package net.mcorp.network.server;

import net.mcorp.network.server.handlers.HttpConnectionHandler;
import net.mcorp.network.server.handlers.NoOpConnectionHandler;

public class TestServer {

	public static final void main(String[] args) {
		Server<NoOpConnectionHandler> server = new Server<NoOpConnectionHandler>(new NoOpConnectionHandler(), true);
		while(server.isRunning()) {}
	}
	
}
