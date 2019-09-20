package net.projectio.server.protocols;

import net.projectio.server.Ticket;

/**
 * A Protocol is a language used between two or more devices as a means to hold a conversation between themselves.
 * This class serves as a means to standardizes what each protocol should do. As of Version 0.0.1 this class
 * will only serve as a means to standardizes the method generateNewPacketObject, as this is really the only method
 * that is shared between protocols as of right now.
 * @author Andrew Kerr
 *
 */
public abstract class Protocol<P extends Packet>{
	
	/**
	 * This method will generate a {@linkplain Packet} that matches the protocol's name.
	 * @param ticket - {@linkplain Ticket} - The ticket object that is being used to generate the packet.
	 */
	public abstract P generateNewPacketObject(Ticket ticket);
	
}
