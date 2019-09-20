package net.projectio.server.protocols.websocket;

import net.projectio.server.Ticket;
import net.projectio.server.protocols.Packet;
import net.projectio.server.protocols.Protocol;

public final class Websocket extends Protocol<WebsocketConnection>{
	
	public static final Websocket protocol = new Websocket();
	
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
