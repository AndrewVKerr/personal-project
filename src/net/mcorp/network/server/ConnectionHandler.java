package net.mcorp.network.server;

import java.net.Socket;

public abstract class ConnectionHandler {
	
	public abstract void handleAccept(Socket socket);
	
}
