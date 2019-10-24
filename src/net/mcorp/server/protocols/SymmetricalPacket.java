package net.mcorp.server.protocols;

public abstract class SymmetricalPacket extends Packet{
	
	protected SymmetricalPacket(Packet dummy) {
		super(dummy);
	}

	/**
	 * This method will check to make sure that there is a valid ticket assigned to this object,
	 * and that the Packet.isLocked() method returns false before calling another method
	 * Packet.readInPacket() to read in all of the necessary data.
	 * @throws Exception An Exception can be thrown from the Packet.readInPacket() method.
	 */
	public final synchronized void readFromTicket() throws Exception {
		if(!this.validateTicket())
			throw new RuntimeException("[SymmetricalPacket.readFromTicket():INVALID_TICKET] Could not read in the next packet from ticket, missing ticket object within the packet object.");
		if(this.isLocked(null))
			throw new RuntimeException("[SymmetricalPacket.readFromTicket():ALREADY_LOCKED] Packet is already in a locked state, cannot interact with the packet due to this lock.");
		this.readInPacket();
	}
	
	/**
	 * This method will check to make sure that there is a valid ticket assigned to this object,
	 * and that the Packet.isLocked() method returns true before calling another method
	 * Packet.writeToPacket() to write all of the necessary data to the ticket socket.
	 * @throws Exception An Exception can be thrown from the Packet.writeToPacket() method.
	 */
	public final synchronized void writeToTicket() throws Exception {
		if(!this.validateTicket())
			throw new RuntimeException("[SymmetricalPacket.writeToTicket():INVALID_TICKET] Could not write the packet to the ticket, missing ticket object within the packet object.");
		if(!this.isLocked(null))
			throw new RuntimeException("[SymmetricalPacket.writeToTicket():MUST_LOCK_PACKET] Could not write the packet to the ticket, Packet must be in a locked state before sending.");
		this.writeToPacket();
	}
	
	/**
	 * This abstract method is responsible for reading in the information from the packet.
	 * @throws Exception
	 */
	protected abstract void readInPacket() throws Exception;
	
	/**
	 * This abstract method is responsible for writing out the information from the packet to the socket.
	 * @throws Exception
	 */
	protected abstract void writeToPacket() throws Exception;
	
}
