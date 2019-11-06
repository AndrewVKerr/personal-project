package net.mcorp.network.server.handlers;

import java.io.IOException;
import java.net.Socket;

import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.http.HttpProtocol;
import net.mcorp.network.common.protocols.http.HttpRPacket;
import net.mcorp.network.server.ClientConnection;
import net.mcorp.network.server.ConnectionHandler;

public class HttpConnectionHandler extends ConnectionHandler{

	@Override
	public void handleAccept(Socket socket) {
		ClientConnection client = new ClientConnection(socket);
		HttpRPacket<HttpProtocol> packet = HttpProtocol.instance.createNewReadPacket(client);
		try {
			packet.read();
			client.getOutputStream().write("Http/1.1 200 OK\n\nHello World!".getBytes());
			client.getOutputStream().flush();
			client.close();
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
