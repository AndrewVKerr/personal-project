package net.mcorp.server.protocols.websocket;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.Handler;

public final class WebsocketHandler extends Handler<WebsocketThread>{

	@Override
	public WebsocketThread generateThread(Ticket ticket) {
		return new WebsocketThread(ticket);
	}
	
}
