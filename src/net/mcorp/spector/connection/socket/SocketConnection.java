package net.mcorp.spector.connection.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.mcorp.spector.connection.Connection;

public class SocketConnection extends Connection<InputStream,OutputStream>{

	public final Socket socket;
	
	public SocketConnection(Socket socket) {
		if(socket == null)
			throw new NullPointerException();
		this.socket = socket;
	}
	
	@Override
	protected InputStream reader() throws IOException {
		return socket.getInputStream();
	}

	@Override
	protected OutputStream writer() throws IOException {
		return socket.getOutputStream();
	}

}
