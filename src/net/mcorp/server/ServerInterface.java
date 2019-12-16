package net.mcorp.server;

import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerInterface{

	public final JFrame frame;
	public final JPanel panel;
	
	public ServerInterface() {
		if(GraphicsEnvironment.isHeadless()) {
			this.frame = null;
			this.panel = null;
		}else {
			this.frame = new JFrame();
			this.frame.setSize(720,480);
			this.frame.setTitle("Server Interface");
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.panel = new JPanel();
			
			
			this.frame.setVisible(true);
		}
	}
	
}
