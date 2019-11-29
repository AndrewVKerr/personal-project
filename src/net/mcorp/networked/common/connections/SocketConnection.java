package net.mcorp.networked.common.connections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.mcorp.common.utils.debug.SmartDebugInterface;

public class SocketConnection extends Connection {

	protected final Socket socket;
	
	public SocketConnection(Socket socket) {
		if(socket == null)
			throw new NullPointerException("[SocketConnection(Socket):PARAMETER_IS_NULL] The provided parameter for the object (socket) of class (Socket) was set to null.");
		this.socket = socket;
	}
	
	@Override
	public final OutputStream getOutputStream(boolean raw) throws IOException {
		return this.socket.getOutputStream();
	}

	@Override
	public final InputStream getInputStream(boolean raw) throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	public final void close() throws IOException {
		this.socket.close();
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"socket = "+SmartDebugInterface.readSmartDebug(indent+indentBy, indentBy, this.socket)
				+ "\n"+indent+"]";
	}

}
