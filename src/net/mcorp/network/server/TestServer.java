package net.mcorp.network.server;

import net.mcorp.network.server.handlers.HttpConnectionHandler;

public class TestServer {

	public static final void main(String[] args) {
		Server<HttpConnectionHandler> server = new Server<HttpConnectionHandler>(new HttpConnectionHandler(), true);
		while(server.isRunning()) {}
	}
	
}
