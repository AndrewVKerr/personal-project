package net.mcorp.webInterface.server.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TestingWidget extends ServerWidget {

	private static final long serialVersionUID = 6926553571005277734L;

	private String str = "";
	
	public TestingWidget() {
		this.setVisible(true);
	}
	
	@Override
	public void draw(Graphics g, int w, int y) {
		g.drawRect(this.mouseX()-10, this.mouseY()-10, 20, 20);
		g.drawString(str, 10, 20);
	}

	@Override
	public void onMouseClick(MouseEvent event) {
		
	}

	@Override
	public void onKeyPressed(KeyEvent event) {
		if(event.getKeyChar() == (char)KeyEvent.VK_BACK_SPACE) {
			if(str.length() <= 0)
				return;
			str = new String(str.substring(0, str.length()-1));
		}else {
			str += event.getKeyChar();
		}
	}

}
