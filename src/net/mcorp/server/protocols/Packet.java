package net.mcorp.server.protocols;

import net.mcorp.server.Ticket;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.exceptions.ValueAlreadySetException;
import net.mcorp.utils.lockable.LockableObject;

/**
 * The Packet (sometimes referred to as a frame) is the most basic form of communication between two devices.
 * This abstract class serves as a means to standardize what some of the packets will have access too.
 * For example a {@linkplain Ticket} object will be attached as a means to reference back to the object
 * that generated this Packet object.
 * @author Andrew Kerr
 */
public abstract class Packet extends LockableObject{

	/**
	 * The original ticket object that was used to generate this Packet object.
	 */
	private Ticket ticket = null;
	
	/**
	 * Gets the internal {@linkplain Ticket} object labeled ticket, can throw a {@linkplain NullPointerException}.<br>
	 * NOTE: This exception will be thrown if the internal ticket object hasn't already been set to a non-null value.
	 * @return {@linkplain Ticket} - The original ticket object that was used to generate this Packet object.
	 * @throws NullPointerException Thrown if the internal ticket object hasn't already been set.
	 */
	public final synchronized Ticket ticket() throws NullPointerException {
		if(this.ticket != null) return this.ticket;
		throw new NullPointerException("The object \"Packet.ticket\" has not been set to a non null value yet.");
	};
	
	/**
	 * Checks the internal {@linkplain Ticket} object labeled ticket.<br>
	 * NOTE: This method should be used before calling the Packet.ticket() method, as this method will yield a boolean
	 * value representing if the ticket object is a non-null value instead of a {@linkplain NullPointerException}.
	 * @return {@linkplain Boolean} - If the internal ticket object is not null.
	 */
	public final synchronized boolean validateTicket() {
		return (this.ticket != null);
	}
	
	/**
	 * Sets the internal {@linkplain Ticket} object labeled ticket, can throw a {@linkplain ValueAlreadySetException}.<br>
	 * NOTE: This exception will be thrown if the internal ticket object is already set to a non-null value.
	 * @param ticket - {@linkplain Ticket} - A ticket object that you wish to associate with this packet.
	 * @throws ValueAlreadySetException Thrown if the internal ticket object is already set.
	 */
	public final synchronized void ticket(Ticket ticket) throws ValueAlreadySetException { 
		if(this.ticket != null) { throw new ValueAlreadySetException("Packet.ticket"); };
		this.ticket = ticket; 
	};
	
	/**
	 * Once called this value will set an internal flag ({@linkplain Boolean} value) that will prevent any
	 * setters that incorporate this lock from changing their values and will throw a {@linkplain LockedValueException}
	 * if that method is called.
	 */
	public final synchronized void lock() { super.lock(); };
	
	/**
	 * This method will return the state of the internal {@linkplain Boolean} value labeled lockValues,
	 * if this method returns true then any methods that incorporates this specific lock will yield a 
	 * {@linkplain LockedValueException} preventing the changing of that value. This method serves as 
	 * a means to test if the lock is active.
	 * @param location - {@linkplain String} - The location of the caller or null. See {@linkplain LockableObject}.isLocked(String);
	 * @return {@linkplain Boolean} - Used to check if a locked value can be changed.
	 * @throws LockedValueException 
	 */
	public final synchronized boolean isLocked(String location) throws LockedValueException { return super.isLocked(location); };
	
}
