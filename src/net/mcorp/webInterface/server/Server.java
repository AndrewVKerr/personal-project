package net.mcorp.webInterface.server;

import java.util.ArrayList;

import net.mcorp.webInterface.server.gui.TestingWidget;

public class Server {
	
	public static final Server instance = new Server();

	private final ArrayList<Clients> clients = new ArrayList<Clients>();
	
	private final ServerUI ui;
	
	private Server() {
		ui = new ServerUI();
		ui.addWidget(new TestingWidget());
	}

	public synchronized void start() {
		while(true) {}
	}
	
}
