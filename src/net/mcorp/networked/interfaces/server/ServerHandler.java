package net.mcorp.networked.interfaces.server;

import java.net.Socket;

public abstract class ServerHandler {
	
	public abstract void handleSocket(Socket socket);
	
}
