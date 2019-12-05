package net.mcorp.networked.interfaces.server;

import java.awt.HeadlessException;

import javax.swing.*;

public class ServerController<ServerHandler_ extends ServerHandler> {

	@SuppressWarnings("unused")
	private final JFrame frame;
	
	@SuppressWarnings("unused")
	private final JPanel root;
	
	private final boolean headless;
	public final boolean isHeadless() { return this.headless; };
	
	public final Server<ServerHandler_> server;
	
	protected ServerController(Server<ServerHandler_> server) {
		this.server = server;
		JFrame frame = null;
		JPanel root = null;
		boolean headless;
		try {
			frame = new JFrame();
			frame.setTitle("Server Controller");
			frame.setSize(720, 480);
			
			root = new JPanel(null);
			
			frame.add(root);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			headless = false;
		}catch(HeadlessException e) { headless = true; }
		this.headless = headless;
		this.frame = frame;
		this.root = root;
	}

}
