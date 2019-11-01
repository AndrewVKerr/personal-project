package net.mcorp.webInterface.server;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import net.mcorp.webInterface.server.gui.ServerWidget;

public class ServerUI {

	private final JFrame frame;
	private final WidgetManager panel;
	
	private final Timer timer;
	
	public ServerUI() {
		this.frame = new JFrame();
		this.frame.setSize(720,480);
		this.frame.setTitle("Server Controller");
		
		this.panel = new WidgetManager();
		this.panel.setSize(720,480);
		
		this.frame.add(panel);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		
		this.timer = new Timer(1000/60, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ServerUI.this.refresh();
			}
			
		});
		this.timer.start();
	}
	
	protected void refresh() {

	}

	public void addWidget(ServerWidget widget) {
		this.panel.add(widget);
		widget.setVisible(true);
	}
	
	public void removeWidget(ServerWidget widget) {
		this.panel.remove(widget);
	}
	
}
