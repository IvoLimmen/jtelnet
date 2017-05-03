package org.limmen.jtelnet.server.protocol.type;

public enum Command implements HasValue<Command> {

	IAC(255),
	SE(240),
	SB(250),
	DONT(254),
	WONT(252),
	WILL(251),
	DO(253),
	
	NAWS(31),
	NEW_ENVIRONMENT(39),
	TERMINAL_SPEED(32),
	BINARY(0),
	EXTEND_ASCII(17),
	TERMINAL_TYPE(24),
	RCTE(7),
	ECHO(1),
	SUPPRESS_GO_AHEAD(3),
	FLOW_CONTROL(33),
	LINEMODE(34);

	private final int value;

	private Command(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public Command parseValue(int value) {
		for (Command command : Command.values()) {
			if (command.value == value) {
				return command;
			}
		}
		return null;
	}
}
