package net.mcorp.network.server.handlers;

import java.net.Socket;

import net.mcorp.network.server.ConnectionHandler;

public class NoOpConnectionHandler extends ConnectionHandler {

	@Override
	public void handleAccept(Socket socket) {}

}
