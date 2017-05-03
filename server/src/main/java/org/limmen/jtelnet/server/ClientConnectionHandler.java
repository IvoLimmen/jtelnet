package org.limmen.jtelnet.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.limmen.jtelnet.server.protocol.ConnectionSettings;
import org.limmen.jtelnet.server.protocol.TelnetInputStreamWrapper;

public class ClientConnectionHandler implements Runnable {

	private final ConnectionSettings connectionSettings = new ConnectionSettings();

	private boolean running = true;

	private final TelnetInputStreamWrapper inputStream;

	private final OutputStream outputStream;

	public ClientConnectionHandler(Socket socket) throws IOException {
		this.connectionSettings.setIpAddress(socket.getInetAddress().getHostAddress());
		this.connectionSettings.setHostName(socket.getInetAddress().getHostName());
		this.outputStream = socket.getOutputStream();
		this.inputStream = new TelnetInputStreamWrapper(socket.getInputStream(), this.outputStream, this.connectionSettings);
	}

	@Override
	public void run() {
		try {
			StringBuilder sb = new StringBuilder();
			while (running) {
				int read = inputStream.read();
				if (Character.isLetterOrDigit(read)) {
					sb.append((char) read);
					write("" + (char) read);
				} else if (read == 13) {
					writeLine("");
					execute(sb.toString().toLowerCase());
					sb.setLength(0);
				}
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally {
			try {
				this.outputStream.close();
			}
			catch (IOException ex) {
			}
			try {
				this.inputStream.close();
			}
			catch (IOException ex) {
			}
		}
	}

	private void execute(String command) throws IOException {
		switch (command) {
			case "help":
				writeLine("Commands:");
				writeLine("info  - Show what the server knows about this connection.");
				writeLine("color - Show a test of colors we can use in telnet.");
				writeLine("test  - Read and output a ANS file found online.");
				writeLine("exit  - Leave.");
				break;
			case "exit":
				writeLine("Bye bye!");
				running = false;
				break;
			case "info":
				write(this.connectionSettings.toString());
				break;
			case "color":
				for (int fcol = 0; fcol < 256; fcol++) {
					write("\u001B[38;5;" + fcol + "mX");
				}
				for (int bcol = 0; bcol < 256; bcol++) {
					write("\u001B[48;5;" + bcol + "mX");
				}
				write("\u001B[48;5;0m\n\rRESET\n\r");
				break;
			case "test":
				showImage("test.ans");
				break;
		}
	}

	private void showImage(String file) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/" + file)))) {
			String line;
			while ((line = reader.readLine()) != null) {
				writeLine(line);
			}
		}
	}

	private void writeLine(String line) throws IOException {
		write(line + "\n\r");
	}

	private void write(String line) throws IOException {
		this.outputStream.write(line.getBytes());
	}
}
