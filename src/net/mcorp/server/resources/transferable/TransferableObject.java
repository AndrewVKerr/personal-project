package net.mcorp.server.resources.transferable;

import net.mcorp.server.Ticket;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.lockable.LockableObject;

public abstract class TransferableObject extends LockableObject{
	
	private String url = "";
	public String getUrl() { return this.url; };
	public void url(String url) throws LockedValueException { this.isLocked("WebObject.url(String)"); this.url = url; };
	
	/**
	 * This function will perform some kind of action on the provided {@linkplain Ticket} object.
	 * @param ticket - {@linkplain Ticket} - The ticket to use for this function.
	 * @throws Exception It is possible that an exception may be raised during the execution of this function.
	 */
	public abstract void execute(Ticket ticket) throws Exception;
	
}
