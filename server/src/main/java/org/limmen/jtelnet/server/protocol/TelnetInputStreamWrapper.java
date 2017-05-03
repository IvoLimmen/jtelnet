package org.limmen.jtelnet.server.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.limmen.jtelnet.server.protocol.type.Command;

public class TelnetInputStreamWrapper extends InputStream {

	public final InputStream inputStream;

	public final OutputStream outputStream;

	final ConnectionSettings connectionSettings;

	public TelnetInputStreamWrapper(final InputStream inputStream, final OutputStream outputStream, final ConnectionSettings connectionSettings) throws IOException {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.connectionSettings = connectionSettings;

		send(Command.IAC, Command.WONT, Command.LINEMODE);
		send(Command.IAC, Command.WILL, Command.ECHO);
		send(Command.IAC, Command.WILL, Command.SUPPRESS_GO_AHEAD);
		send(Command.IAC, Command.DO, Command.EXTEND_ASCII);
		send(Command.IAC, Command.DO, Command.NAWS);
		send(Command.IAC, Command.DO, Command.TERMINAL_TYPE);
	}

	@Override
	public int read() throws IOException {
		int read = inputStream.read();

		if (read == Command.IAC.getValue()) {
			parseCommand();
			return read();
		}

		return read;
	}

	private void parseCommand() throws IOException {

		int handle = inputStream.read();

		// IAC SE -> done parsing
		if (handle == Command.SE.getValue()) {
			// finished handling attributes
			return;
		}

		// other command will be IAC [WILL/WON'T/...] OPTION
		int option = inputStream.read();

		if (handle == Command.WILL.getValue()) {
			if (option == Command.TERMINAL_TYPE.getValue()) {
				// IAC SB TERMINAL-TYPE SEND IAC SE
				outputStream.write(Command.IAC.getValue());
				outputStream.write(Command.SB.getValue());
				outputStream.write(Command.TERMINAL_TYPE.getValue());
				outputStream.write(1);
				outputStream.write(Command.IAC.getValue());
				outputStream.write(Command.SE.getValue());
			}
		}

		// handling parameters is slightly different
		if (handle == Command.SB.getValue()) {
			if (option == Command.NAWS.getValue()) {
				inputStream.read(); // 0 (left)
				this.connectionSettings.setWidth(inputStream.read());
				inputStream.read(); // 0 (top)
				this.connectionSettings.setHeight(inputStream.read());
				inputStream.read(); // IAC
				inputStream.read(); // SE
			}
			if (option == Command.TERMINAL_TYPE.getValue()) {
				int r;
				StringBuilder term = new StringBuilder();
				while ((r = inputStream.read()) != Command.IAC.getValue()) {
					term.append((char) r);
				}
				this.connectionSettings.setTerminal(term.toString());
				inputStream.read(); // SE
			}
		}
	}

	private void send(Command... cmds) throws IOException {
		for (Command cmd : cmds) {
			outputStream.write(cmd.getValue());
		}
	}

}
