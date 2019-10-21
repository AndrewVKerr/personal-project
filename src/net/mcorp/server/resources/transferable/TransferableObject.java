package net.mcorp.server.resources.transferable;

import net.mcorp.server.Ticket;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.lockable.LockableObject;

public abstract class TransferableObject extends LockableObject{
	
	private ResourceUrl url = null;
	public synchronized ResourceUrl getUrl() { return this.url; };
	public synchronized void url(ResourceUrl url) throws LockedValueException { if(this.url == url) { this.url = null; return; }; this.isLocked("WebObject.url(ResourceUrl)"); this.url = url; };
	
	/**
	 * This function will perform some kind of action on the provided {@linkplain Ticket} object.
	 * @param ticket - {@linkplain Ticket} - The ticket to use for this function.
	 * @throws Exception It is possible that an exception may be raised during the execution of this function.
	 */
	public abstract void execute(Ticket ticket) throws Exception;
	
	public synchronized String toString() {
		return this.toString("", "   ");
	}
	
	public synchronized String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[...],";
	}
}
