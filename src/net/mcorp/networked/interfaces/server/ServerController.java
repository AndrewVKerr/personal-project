package net.mcorp.networked.interfaces.server;

import javax.swing.*;

public class ServerController<ServerHandler_ extends ServerHandler> {

	private final JFrame frame;
	private final JPanel root;
	
	private Server<ServerHandler_> server;
	
	protected ServerController(Server<ServerHandler_> server) {
		this.server = server;
		this.frame = new JFrame();
		this.frame.setTitle("Server Controller");
		this.frame.setSize(720, 480);
		
		this.root = new JPanel(null);
		
		this.frame.add(this.root);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
	}

}
