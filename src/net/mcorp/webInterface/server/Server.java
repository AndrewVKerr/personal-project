package net.mcorp.webInterface.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import net.mcorp.webInterface.server.gui.TestingWidget;

public class Server {
	
	public static final Server instance = new Server();
	
	private final ArrayList<Client> clients = new ArrayList<Client>();
	private final ServerSocket socket;
	
	private boolean running = false;
	public boolean isRunning() { return this.running; };
	public void stop() { this.running = false; };
	
	private final ServerUI ui;
	
	private Server() {
		ui = new ServerUI();
		ui.addWidget(new TestingWidget());
		try {
			socket = new ServerSocket(2000);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void start() {
		while(true) {
			synchronized(this) { //Acquire the lock for this server object... This prevents the stopping of this server while this section of code is being executed.
				if(!this.running)
					break;
				if(this.socket.isClosed())
					break;
			}
			try {
				Socket sock = socket.accept();
				synchronized(this){
					Client client = new Client();
					client.socket(sock);
					clients.add(client);
					client.server(this);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
