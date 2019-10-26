package net.mcorp.server.protocols.https;

import java.io.IOException;

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
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof IOException) {
				if(e.getLocalizedMessage().equals("CLOSE_ME")) {
					try {
						packet.ticket().close();
					} catch(Exception e1) {}
					return;
				}
			}
		}
		while(true) {}
		
	}

}
