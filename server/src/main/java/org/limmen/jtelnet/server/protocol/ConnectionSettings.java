package org.limmen.jtelnet.server.protocol;

public class ConnectionSettings {

	private int height;

	private String hostName;

	private String ipAddress;

	private boolean supports256Colors;

	private String terminal;

	private int width;

	public ConnectionSettings() {
		this.width = 80;
		this.height = 25;
	}

	public int getHeight() {
		return height;
	}

	public String getHostName() {
		return hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public String getTerminal() {
		return terminal;
	}

	public int getWidth() {
		return width;
	}

	public boolean isSupports256Colors() {
		return supports256Colors;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setSupports256Colors(boolean supports256Colors) {
		this.supports256Colors = supports256Colors;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;

		if (terminal.toLowerCase().contains("256") || terminal.toLowerCase().contains("xterm")) {
			this.supports256Colors = true;
		}
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Width: ").append(getWidth()).append("\n\r");
		sb.append("Height: ").append(getHeight()).append("\n\r");
		sb.append("Terminal: ").append(getTerminal()).append("\n\r");
		sb.append("256 color: ").append(isSupports256Colors()).append("\n\r");
		sb.append("IP address: ").append(getIpAddress()).append("\n\r");
		sb.append("Hostname: ").append(getHostName()).append("\n\r");

		return sb.toString();
	}

}
