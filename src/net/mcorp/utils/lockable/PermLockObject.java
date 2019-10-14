package net.mcorp.utils.lockable;

/**
 * This class extends the {@linkplain LockObject} class and is used to define a permanently locked object.<br>
 * Once the lock is set it cannot be unset.
 * @author Andrew Kerr
 *
 */
public class PermLockObject extends LockObject {

	public final void lock() {
		this.lockProtectedLock();
		super.lock();
	}
	
	/**
	 * This method has been deprecated and will throw a {@linkplain RuntimeException} upon execution as this object cannot have its protected lock reset.
	 * @deprecated Removed as this object sets its lock permanently.
	 */
	protected final void unlockProtectedLock() {
		throw new RuntimeException("[PermLockObject.unlockProtectedLock();CANNOT_UNLOCK_PROTECTED] This object cannot unlock its protected lock as it is accessing a PermLockObject.");
	}
	
}
