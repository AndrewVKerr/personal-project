package net.mcorp.server.protocols.https;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.Handler;

public class HttpsHandler extends Handler<HttpsThread>{

	@Override
	public HttpsThread generateThread(Ticket ticket) {
		return new HttpsThread(ticket);
	}

}
