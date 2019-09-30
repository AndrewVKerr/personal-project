package net.mcorp.utils.resources;

import net.mcorp.server.Ticket;
import net.mcorp.server.packets.WebSocketPacket.WebSocketFrame;

public abstract class WebsocketHandler {

	public abstract void handle(Ticket ticket, WebSocketFrame requestFrame, WebSocketFrame responseFrame);
	
}
