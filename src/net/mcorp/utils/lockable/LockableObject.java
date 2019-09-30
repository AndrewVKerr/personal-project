package net.mcorp.utils.lockable;

import net.mcorp.utils.exceptions.LockedValueException;

/**
 * This abstract class is used to define other classes as a lockable object.<hr>
 * <h3>What is a lockable object?</h3>
 * <p>A lockable object is an object that has properties that are pseudo-final, or they can be changed until the lock function is called.
 * Once you call the lock function there is no external way to unlock that lock. However within the class you can call the unlock function
 * to unlock the values.</p>
 * @author Andrew Kerr
 */
public abstract class LockableObject {
	
	private boolean locked = false;
	private boolean permLock = false;
	
	/**
	 * This function will set an internal value that will prevent this lock from being unlocked. <br>
	 * NOTE: THIS ACTION CANNOT BE UNDONE!!!
	 */
	protected synchronized void permLock() { this.permLock = true; };
	
	/**
	 * This function will lock the internal lock and will prevent any values that use this lock to be changed.
	 */
	public synchronized void lock() { 
		this.locked = true; 
	};
	
	protected synchronized static void unlock(LockableObject obj) {
		if(obj.permLock)
			throw new RuntimeException("[LockableObject.unlock(LockableObject):CANNOT_UNLOCK]This object cannot be unlocked, it has been designated as a permanent lock!");
		obj.locked = false;
	}
	
	/**
	 * This function will do one of 3 things:<br>
	 * 1) If the location parameter is set to null then the value of the internal lock will be returned.<br>
	 * 2) If the location parameter is not set to null then the internal lock will be tested, if it is locked then a {@linkplain LockedValueException} will be thrown.<br>
	 * 3) If the location parameter is not set to null and the internal lock is tested to be false then this function will return false.
	 * @param location - {@linkplain String} - The function that is calling this function or null if you wish to throw exception.
	 * @return {@linkplain Boolean} - The state of the lock.
	 * @throws LockedValueException Thrown when the internal lock is tested as true and the location parameter is set to a non-null value.
	 */
	public synchronized boolean isLocked(String location) throws LockedValueException {
		if(location == null) {
			return this.locked;
		}
		if(this.locked == true) {
			throw new LockedValueException(location);
		}
		return false;
	};
	
}
