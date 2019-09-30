package net.mcorp.utils.lockable;

/**
 * This class extends the {@linkplain LockObject} class and is used to define a permanently locked object.<br>
 * Once the lock is set it cannot be unset.
 * @author Andrew Kerr
 *
 */
public class PermLockObject extends LockObject {

	public final void lock() {
		this.permLock();
		super.lock();
	}
	
}
