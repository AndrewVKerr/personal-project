package net.mcorp;

import java.io.IOException;

import javax.swing.JOptionPane;

import net.mcorp.server.Server;

public class ServerTester {
	
	public static void main(String[] args) {
		try {
			Server server = new Server();
			System.out.println(server.fileTree.toString());
			System.out.println(server.fileTree.getResource("/index/test", "/").toString());
			//server.fileTree.resources.add(new Webpage("index", new File("C:\\Users\\kerr3\\Desktop\\servers\\test\\index.html")));
			//server.fileTree.resources.add(new Webpage("favicon.ico",new File("C:\\Users\\kerr3\\Desktop\\servers\\test\\favicon.png")));
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
			System.out.flush();
			e.printStackTrace();
		} catch (Exception e) {
			System.out.flush();
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
}