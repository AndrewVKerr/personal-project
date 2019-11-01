package net.mcorp.webInterface.server;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.mcorp.webInterface.server.gui.ServerWidget;

public class WidgetManager extends JPanel {

	private static final long serialVersionUID = -8201974004164057219L;
	private ArrayList<ServerWidget> widgets = new ArrayList<ServerWidget>();
	public int currentWidget = 0;
	
	private final Keyboard keyboard;
	
	private class Keyboard implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			System.out.println(arg0.getKeyChar());
			try {
				ServerWidget widget = widgets.get(currentWidget%widgets.size());
				if(widget != null) {
					widget.onKeyPressed(arg0);
				}
			}catch(Exception e) {
				if(e instanceof ArithmeticException)
					return;
				e.printStackTrace();
			}
		}
		
	}
	
	public WidgetManager() {
		keyboard = new Keyboard();
		this.addKeyListener(keyboard);
	}
	
	public void add(ServerWidget widget) {
		widgets.add(widget);
		Timer t = new Timer(1000/60,(ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
			
		});
		t.start();
	}
	
	public void remove(ServerWidget widget) {
		widgets.remove(widget);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(this.hasFocus() == false)
			this.requestFocus();
		g.clearRect(0, 0, getWidth(), getHeight());
		try {
			ServerWidget widget = widgets.get(currentWidget%widgets.size());
			if(widget == null)
				g.drawString("Missing Widget?", 10, g.getFontMetrics().getHeight()+10);
			else {
				Point mousePos = this.getMousePosition();
				if(mousePos == null)
					mousePos = new Point(-1,-1);
				widget.update(g,mousePos);
			}
		}catch(Exception e) {
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
			int h = g.getFontMetrics().getHeight();
			PrintStream ps = new PrintStream(new OutputStream() {
				
				public int x = 10;
				public int i = 0;

				@Override
				public void write(int arg0) throws IOException {
					char c = (char)arg0;
					if(c == '\n') {
						x = 10;
						i ++;
					}else {
						if(c == '\t') {
							x += g.getFontMetrics().charWidth(' ')*8;
						}else {
							g.drawString(c+"", x, h + h*i + 5*i);
							x += g.getFontMetrics().charWidth(c);
						}
					}
				}
				
			});
			e.printStackTrace(ps);
		}
	}
	
}
