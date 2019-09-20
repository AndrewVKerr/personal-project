package net.projectio.server.protocols.http;

import net.projectio.server.Ticket;
import net.projectio.server.protocols.Packet;
import net.projectio.server.protocols.Protocol;

public final class Http extends Protocol<HttpPacket>{

	public static final Http protocol = new Http();
	
	/*
	 * This is here mainly to prevent the instantiation of this class outside of the class.
	 */
	private Http() {}
	
	public HttpPacket generateNewPacketObject(Ticket ticket) {
		HttpPacket packet = new HttpPacket();
		try {
			packet.ticket(ticket);
		}catch(Exception e) {
			RuntimeException re = new RuntimeException("[Http.generateNewPacketObject(Ticket):TICKET_ALREADY_SET] The ticket object has already been set within the generated ticket object.");
			re.addSuppressed(e);
			throw re;
		}
		if(packet instanceof Packet)
			return packet;
		return null;
	}
	
}
