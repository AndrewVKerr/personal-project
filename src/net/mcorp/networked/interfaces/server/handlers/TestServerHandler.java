package net.mcorp.networked.interfaces.server.handlers;

import java.net.Socket;

import net.mcorp.networked.common.connections.SocketConnection;
import net.mcorp.networked.interfaces.server.ServerHandler;

public class TestServerHandler extends ServerHandler {

	@Override
	public void handleSocket(Socket socket) {
		SocketConnection client = new SocketConnection(socket);
		try {
			client.getOutputStream().write("Http/1.1 200 OK\n\nHello World!".getBytes());
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
