package net.mcorp.network.server.handlers;

import java.net.Socket;

import net.mcorp.network.server.ConnectionHandler;

public class HttpConnectionHandler extends ConnectionHandler{

	@Override
	public void handleAccept(Socket socket) {
		System.out.println(socket);
	}
	
}
