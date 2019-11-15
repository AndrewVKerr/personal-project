package net.mcorp.network.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerRunnable<ConnectionHandler_ extends ConnectionHandler> implements Runnable{

	private ServerSocket socket;
	private Server<ConnectionHandler_> server;
	private ConnectionHandler_ handler;
	
	public ServerRunnable(Server<ConnectionHandler_> server, ConnectionHandler_ handler) {
		this.server = server;
		this.handler = handler;
	}

	@Override
	public void run() {
		try {
			if(socket == null) {
					socket = new ServerSocket(server.getPort());
					this.socket.setSoTimeout(1000);
			}
			while(server.isRunning()) {
				try {
					Socket sock = this.socket.accept();
					if(handler != null)
						handler.handleAccept(sock);
					else
						System.out.println("+---=[ Warning ]=---+\nThis server has no handler to handle sockets.");
				}catch(SocketTimeoutException ste) {}
			}
		}catch(Exception e) {
			System.err.println("+--------------------------------=[Server CRASH]=-----------------------------------+\n"
							 + "\t- Report:\tAn unexpected server crash has just occurred...\n"
							 + "\t- Exception:\t"+e.getClass().getSimpleName()+"\n"
							 + "\t- Reason:\t\""+e.getLocalizedMessage()+"\"\n"
							 + "+--------------------------------=[Server CRASH]=-----------------------------------+\n");
			e.printStackTrace();
			server.stop();
		}
	}

}
