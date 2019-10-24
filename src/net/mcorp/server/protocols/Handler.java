package net.mcorp.server.protocols;

import net.mcorp.server.Ticket;

public abstract class Handler<T extends HandlerThread<?,?>> {
	
	public abstract T generateThread(Ticket ticket);
	
}
