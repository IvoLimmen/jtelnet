package org.limmen.jtelnet.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TelnetServer {

	public static void main(String[] args) throws IOException {
		new TelnetServer().start();
	}

	private final ExecutorService executorService = Executors.newFixedThreadPool(100);

	public TelnetServer() {
	}

	public void start() throws IOException {
		ServerSocket server = new ServerSocket(2300, 1000, InetAddress.getLocalHost());
		while (true) {
			executorService.submit(new ClientConnectionHandler(server.accept()));
		}
	}
}
