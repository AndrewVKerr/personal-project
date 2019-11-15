package net.mcorp.network.server;

import net.mcorp.network.common.protocols.http.HttpData;

public class TestServer {

	public static final void main(String[] args) {
		HttpData data = new HttpData();
		System.out.println(data.toString("","\t"));
		//Server<HttpConnectionHandler> server = new Server<HttpConnectionHandler>(new HttpConnectionHandler(), true);
		//while(server.isRunning()) {}
	}
	
}
