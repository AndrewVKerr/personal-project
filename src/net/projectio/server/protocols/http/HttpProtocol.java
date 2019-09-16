package net.projectio.server.protocols.http;

import net.projectio.server.Ticket;
import net.projectio.server.protocols.Packet;
import net.projectio.server.protocols.Protocol;

public class HttpProtocol extends Protocol{

	@Override
	public Packet generateNewPacketObject(Ticket ticket) {
		HttpPacket packet = new HttpPacket();
		try {
			packet.ticket(ticket);
		}catch(Exception e) {
			RuntimeException re = new RuntimeException("[HttpProtocol.generateNewPacketObject(Ticket):TICKET_ALREADY_SET] The ticket object has already been set within the generated ticket object.");
			re.addSuppressed(e);
			throw re;
		}
		return packet;
	}
	
}
