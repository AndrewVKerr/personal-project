package net.mcorp.spector.connection.plaintext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.spector.connection.Connection;

public final class EndpointConnection extends Connection<InputStream, OutputStream>{

	private Connection<?,?> connection;
	
	public EndpointConnection(Connection<?,?> connection) {
		this.connection = connection;
		if(connection == null)
			throw new NullPointerException("A valid non null connection object must be passed to this constructor.");
	}
	
	public void write(int b) throws IOException {
		this.connection.getWriter().write(b);
	}
	
	public int read() throws IOException{
		return this.connection.getReader().read();
	}
	
	@Override
	protected InputStream reader() throws IOException {
		throw new RuntimeException("A PlaintextConnection object is the last link in the connection chain, for this reason there is no reader object to pass back.");
	}

	@Override
	protected OutputStream writer() throws IOException {
		throw new RuntimeException("A PlaintextConnection object is the last link in the connection chain, for this reason there is no writer object to pass back.");
	}

}
