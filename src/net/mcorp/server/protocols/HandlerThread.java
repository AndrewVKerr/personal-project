package net.mcorp.server.protocols;

import java.io.IOException;

import net.mcorp.server.Ticket;

public abstract class HandlerThread<P extends Protocol<?>, T extends Packet> implements Runnable{
	
	private Ticket ticket;
	protected Ticket ticket() { return this.ticket; };
	private Protocol<T> protocol;
	protected Protocol<T> protocol() { return this.protocol; };
	
	public HandlerThread(Protocol<T> protocol, Ticket ticket) {
		this.ticket = ticket;
		this.protocol = protocol;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final void run() {
		ticket.protocol();
		ticket.autoClose(true);
		try {
			execute((T) ticket.getPacket(protocol));
		} catch (IOException e) {
			e.printStackTrace();
			ticket.protocol(null);
			try {
				ticket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public abstract void execute(T packet);
	
}
