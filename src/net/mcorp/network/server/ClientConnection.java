package net.mcorp.network.server;

import java.net.Socket;

import net.mcorp.network.common.Connection;

public final class ClientConnection extends Connection{

	public ClientConnection(Socket socket) {
		super(socket);
	}
	
}
