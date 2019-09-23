package net.projectio;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import net.projectio.server.ProjectIOServer;
import net.projectio.server.Ticket;
import net.projectio.server.packets.WebSocketPacket.WebSocketFrame;
import net.projectio.utils.resources.Webpage;
import net.projectio.utils.resources.Websocket;
import net.projectio.utils.resources.WebsocketHandler;

public class ProjectIO {
	
	public static void main(String[] args) {
		try {
			ProjectIOServer server = new ProjectIOServer();
			server.fileTree.resources.add(new Webpage("index", new File("C:\\Users\\kerr3\\Desktop\\servers\\test\\index.html")));
			server.fileTree.resources.add(new Webpage("favicon.ico",new File("C:\\Users\\kerr3\\Desktop\\servers\\test\\favicon.png")));
			/*server.fileTree.resources.add(new Websocket("websocket",new WebsocketHandler() {

				@Override
				public void handle(Ticket ticket, WebSocketFrame requestFrame, WebSocketFrame responseFrame) {
					if(requestFrame.opcode() == 0x0001) {
						responseFrame.opcode(Byte.parseByte("1"));
						responseFrame.payload("Testing!");
						responseFrame.submit();
					}
				}
				
			}));*/
			server.run();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An Exception occurred that prevented the server from starting...\nCheck Exception for more details.\nException: "+e.getClass().getSimpleName()+"\nMessage: "+e.getLocalizedMessage(), "Failed to start ProjectIO Server!", JOptionPane.ERROR_MESSAGE, null);
			e.printStackTrace();
		}
	}
	
}
