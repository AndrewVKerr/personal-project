package net.mcorp.webInterface.server.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public abstract class ServerWidget extends JComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3765448159991519288L;

	private int mouseX = 0;
	public int mouseX() { return this.mouseX; };
	
	private int mouseY = 0;
	public int mouseY() { return this.mouseY; };
	
	public abstract void draw(Graphics g, int w, int y);
	
	public abstract void onMouseClick(MouseEvent event);
	public abstract void onKeyPressed(KeyEvent event);
	
	public void update(Graphics g, Point mousePos) {
		this.mouseX = mousePos.x;
		this.mouseY = mousePos.y;
		paint(g);
	}
	
	public void paint(Graphics g) {
		this.draw(g, this.getWidth(), this.getHeight());
	}
	
}
