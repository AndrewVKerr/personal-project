package net.mcorp.server;

import javax.swing.*;

public class ServerUI {

	private JFrame frame;
	private JPanel panel;
	
	private final Server server;
	public final Server server() { return this.server; };
	
	public final void addComponent(JComponent component) {
		panel.add(component);
	}
	
	public final void removeComponent(JComponent component) {
		panel.remove(component);
	}
	
	public ServerUI(Server server) {
		this.server = server;
		frame = new JFrame();
		panel = new JPanel(null);
		frame.add(panel);
		panel.setSize(720, 480);
		frame.setSize(720, 480);	
		frame.setTitle("Server - Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
}
