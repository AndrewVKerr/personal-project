package net.mcorp.server.protocols.http;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.HandlerThread;

public class HttpThread extends HandlerThread<Http,HttpPacket>{

	public HttpThread(Ticket ticket) {
		super(Http.protocol, ticket);
	}

	@Override
	public void execute(HttpPacket packet) {
		// TODO Auto-generated method stub
		
	}
	
}
