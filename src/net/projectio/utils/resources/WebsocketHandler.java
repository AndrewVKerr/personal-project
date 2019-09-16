package net.projectio.utils.resources;

import net.projectio.server.Ticket;
import net.projectio.server.packets.WebSocketPacket.WebSocketFrame;

public abstract class WebsocketHandler {

	public abstract void handle(Ticket ticket, WebSocketFrame requestFrame, WebSocketFrame responseFrame);
	
}
