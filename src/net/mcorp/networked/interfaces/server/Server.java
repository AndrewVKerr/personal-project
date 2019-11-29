package net.mcorp.networked.interfaces.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * <h1>net.mcorp.networked.interfaces.server.Server&lt;ServerHandler_ extends {@linkplain ServerHandler}&gt;</h1>
 * <hr>
 * <p>
 * This class is used to create and maintain the main server thread.
 * </p>
 * <hr>
 * @author Andrew Kerr
 * @param <ServerHandler_> - {@linkplain ServerHandler} - The class of the handler for this server.
 */
public class Server<ServerHandler_ extends ServerHandler> implements Runnable{
	
	private final ServerHandler_ handler;
	private final ServerSocket serverSocket;
	
	private boolean shutdown = false;
	public void shutdown() { this.shutdown = true; };
	public boolean willShutdown() { return this.shutdown; };
	
	public Server(ServerHandler_ handler, int port) throws IOException {
		this.handler = handler;
		this.serverSocket = new ServerSocket(port);
		this.serverSocket.setSoTimeout(1000);
	}

	@Override
	public void run() {
		while(!willShutdown()) {
			try {
				Socket socket = this.serverSocket.accept();
				handler.handleSocket(socket);
			}catch(Exception e) {
				if( !(e instanceof SocketTimeoutException) ) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
