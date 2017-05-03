package org.limmen.jtelnet.server.protocol.type;

public enum LineModeOption implements HasValue<LineModeOption> {

	MODE(1),
	FORWARDMASK(2),
	SLC(3),
	EOF(236),
	SUSP(237),
	ABORT(238);

	private final int value;

	private LineModeOption(int value) {
		this.value = value;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public LineModeOption parseValue(int value) {
		for (LineModeOption lmo : LineModeOption.values()) {
			if (lmo.value == value) {
				return lmo;
			}
		}
		return null;
	}
}
