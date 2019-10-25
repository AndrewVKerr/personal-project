package net.mcorp.server.protocols.https;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.HandlerThread;

public class HttpsThread extends HandlerThread<Https, HttpsPacket> {

	protected HttpsThread(Ticket ticket) {
		super(Https.protocol, ticket);
	}

	@Override
	public void execute(HttpsPacket packet) {
		System.out.println("Starting up!!!");
		try {
			packet.readFromTicket();
			System.out.println(packet.toString());
			while(true) {}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
