package net.mcorp.network.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.exceptions.ConnectionException.ConnectionFailState;
import net.mcorp.network.common.protocols.encryption.EncryptedConnection;
import net.mcorp.network.common.protocols.encryption.EncryptedPacketData;
import net.mcorp.network.common.utils.debug.SmartDebug;

/**
 * A abstract class that is used to communicate between two computers, intended to be extended by a object that would designate the connection role
 * such as a {@linkplain ServerConnection} and a {@linkplain ClientConnection} or possibly be used as a middle layer for encrypted communications 
 * (See {@linkplain EncryptedConnection}). These classes will help packets determine the best structure for them
 * to use. This only is necessary for two way communication with asymmetrical packet structures such as HTTP, however it should be implemented in
 * every packet in order to determine if a connection should be used or not IE a server to a server or a client to a client.
 * @apiNote This class (and all extending classes) should have no responsibility to maintain the flow of the program. It should only be
 * responsibly for storing information about the connection and will not be responsible for handling the connection.
 * @author Andrew Kerr
 */
public abstract class Connection extends SmartDebug{
	
	private Socket socket;
	public Connection(Socket socket) {
		this.socket = socket;
	}

	public InputStream getRawInputStream() throws ConnectionException {
		StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
		try {
			Class<?> callerClass = Class.forName(caller.getClassName());
			if(EncryptedPacketData.class.isAssignableFrom(callerClass)) {
				try {
					return this.socket.getInputStream();
				} catch (IOException e) {
					throw new ConnectionException(ConnectionFailState.INPUT_CLOSED);
				}
			}else {
				throw new Exception();
			}
			
		} catch (Exception e1) {
			throw new ConnectionException(ConnectionFailState.RESTRICTED_IO);
		}
	}
	
	public InputStream getInputStream() throws ConnectionException {
		try {
			return this.socket.getInputStream();
		} catch (IOException e) {
			throw new ConnectionException(ConnectionFailState.INPUT_CLOSED);
		}
	}
	
	public OutputStream getOutputStream() throws ConnectionException{
		try {
			return this.socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ConnectionException(ConnectionFailState.OUTPUT_CLOSED);
		}
	}
	
	public boolean isOpen() {
		return !this.socket.isClosed();
	}
	
	public void close() throws IOException {
		this.socket.close();
	}
	
}
