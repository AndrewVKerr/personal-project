package net.mcorp.server.protocols.websocket;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.Packet;
import net.mcorp.server.protocols.Protocol;

public final class Websocket extends Protocol<WebsocketConnection>{
	
	public static final Websocket protocol = new Websocket();
	public static final WebsocketHandler handler = new WebsocketHandler();
	
	/*
	 * This constructor is used to prevent this class from being created outside of its class.
	 */
	private Websocket() {}
	
	@Override
	public WebsocketConnection generateNewPacketObject(Ticket ticket) {
		WebsocketConnection packet = new WebsocketConnection();
		try {
			packet.ticket(ticket);
		}catch(Exception e) {
			RuntimeException re = new RuntimeException("[Websocket.generateNewPacketObject(Ticket):TICKET_ALREADY_SET] The ticket object has already been set within the generated ticket object.");
			re.addSuppressed(e);
			throw re;
		}
		if(packet instanceof Packet)
			return packet;
		return null;
	}

}
