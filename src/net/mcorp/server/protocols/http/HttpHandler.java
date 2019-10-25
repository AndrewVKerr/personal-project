package net.mcorp.server.protocols.http;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.Handler;

public final class HttpHandler extends Handler<HttpThread>{

	@Override
	public HttpThread generateThread(Ticket ticket) {
		return new HttpThread(ticket);
	}

}
