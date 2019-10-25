package net.mcorp.server.protocols.https;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.Packet;
import net.mcorp.server.protocols.Protocol;

public class Https extends Protocol<HttpsPacket>{

	public static final Https protocol = new Https();
	public static final HttpsHandler handler = new HttpsHandler();
	
	/*
	 * This is here mainly to prevent the instantiation of this class outside of the class.
	 */
	private Https() {}
	
	public HttpsPacket generateNewPacketObject(Ticket ticket) {
		HttpsPacket packet = new HttpsPacket();
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
