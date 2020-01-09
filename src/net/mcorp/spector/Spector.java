package net.mcorp.spector;

import java.io.IOException;

import net.mcorp.spector.server.Server;

public class Spector {

	public final Server server;
	
	public Spector() throws IOException {
		server = new Server(2020);
	}
	
	public static void main(String[] args) {
		try {
			new Spector().server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
