package net.mcorp.network.client;

import java.net.Socket;

import net.mcorp.network.common.Connection;

public class ServerConnection extends Connection{

	public ServerConnection(Socket socket) {
		super(socket);
	}

}