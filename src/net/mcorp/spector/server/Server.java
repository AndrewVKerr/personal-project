package net.mcorp.spector.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server implements Runnable{

	private ServerSocket socket;
	public ServerSocket socket() { return this.socket; };
	
	private boolean stop = false;
	public synchronized void stop() { this.stop = true; };
	
	private boolean stopped = false;
	public synchronized boolean stopped() { return this.stopped; };
	
	public Server(int port) throws IOException {
		socket = new ServerSocket(port);
		socket.setSoTimeout(1000);
	}

	@Override
	public void run() {
		stop = false;
		while(!stop) {
			try {
				Socket sock = socket.accept();
				sock.getOutputStream().write("Http/1.1 200 OK\n\nHello World!".getBytes());
				sock.close();
			}catch(Exception e) {
				if(e instanceof SocketTimeoutException) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e1) {}
					continue;
				}
				e.printStackTrace();
			}
		}
		stopped = true;
	} 
	
}
