package net.projectio.utils.resources;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Base64;
import net.projectio.server.Packet;
import net.projectio.server.Ticket;
import net.projectio.server.packets.HttpPacket;
import net.projectio.server.packets.WebSocketPacket;
import net.projectio.server.packets.WebSocketPacket.WebSocketFrame;

public class Websocket extends Resource{
	
	private WebsocketHandler handler = null;
	public WebsocketHandler handler() { return this.handler; };
	public void handler(WebsocketHandler handler) { if(handler != null) { this.handler = handler; } else { throw new NullPointerException("Please provide a non null value when assigning a handler!"); }; };
	
	public Websocket(String name, WebsocketHandler handler) {
		super(name);
		this.handler = handler;
	}

	@Override
	public void writeToTicket(Ticket ticket) throws IOException { //This method will result in a loop.
		Packet p = ticket.lastPacket();
		if(p instanceof HttpPacket) {
			HttpPacket packet = (HttpPacket) p;
			if(packet.getValue("Connection").equalsIgnoreCase("Upgrade")) {
				System.out.println(packet.getValue("Sec-WebSocket-Key"));
				String s;
				try {
					s = new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-1").digest((packet.getValue("Sec-WebSocket-Key").concat("258EAFA5-E914-47DA-95CA-C5AB0DC85B11")).getBytes())));
				}catch(Exception e) {
					e.printStackTrace();
					return;
				}
				System.out.println(s);
				ticket.write("Http/1.1 101 Websocket\nConnection: Upgrade\nUpgrade: Websocket\nSec-WebSocket-Accept: "+s+"\n\n");
				ticket.flush();
				//Start communicating in websocket only!
				System.out.println("Websocket protocol established!");
				boolean closeSocket = false;
				WebSocketPacket wpacket = (WebSocketPacket) ticket.readPacket(WebSocketPacket.class);
				OutputStream out = ticket.socket.getOutputStream();
				while(!closeSocket && ticket.socket.isClosed() == false && ticket.socket.isInputShutdown() == false) {
					try {
						wpacket.readInPacket();
						if(wpacket.finishedReading() == false)
							throw new Exception("Failed to read socket, finished flag not set!");
						WebSocketFrame frame = wpacket.frame;
						if(frame == null)
							throw new Exception("Missing Websocket frame!?");
						if(frame.opcode() == 8) {//Close!
							WebSocketFrame response = wpacket.createNewResponse(this);
							response.opcode(Byte.valueOf("8"));
							response.sendFrame(out, false);
							break;
						}
						WebSocketFrame response = wpacket.createNewResponse(this);
						this.handler.handle(ticket, frame, response);
						if(response.submitted() && response.owner == this) {
							response.sendFrame(out, false); 
						}
					}catch(Exception e) {
						if(e.getLocalizedMessage().startsWith("[CLOSED_SOCKET]")) {
							closeSocket = true;
							break;
						}
						e.printStackTrace();
					}
				}
			}else {
				ticket.write("Http/1.1 426 Websocket\nUpgrade: Websocket\n\n");
				ticket.flush();
			}
			try {
				ticket.close();
			}catch(Exception e) {}//Client probably already closed the socket, ignore this exception!
		}
	}

}
