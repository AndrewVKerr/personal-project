package net.mcorp.network.server;

import java.net.Socket;

import net.mcorp.network.common.Connection;

public final class ClientConnection extends Connection{

	public ClientConnection(Socket socket) {
		super(socket);
	}

	@Override
	public String toString(String indent, String indentBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
