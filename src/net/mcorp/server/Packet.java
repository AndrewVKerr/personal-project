package net.mcorp.server;

import java.io.IOException;

@Deprecated
public abstract class Packet {

	private Ticket ticket;
	public Ticket ticket() { return this.ticket; };
	public void ticket(Ticket ticket) throws Exception { if(this.ticket == null) this.ticket = ticket; else throw new Exception("Failed to set Ticket; Ticket was already set!"); };

	private boolean done = false;
	public boolean finishedReading() { return this.done; };
	protected void startReading() { this.done = false; };
	protected void finishReading() { this.done = true; };
	
	public Packet(Ticket ticket) {
		this.ticket = ticket;
	}
	
	public abstract void readInPacket() throws IOException;
	
}
