package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

/**
 * Basically a Long object however it has been integrated with {@linkplain LockeableVariable}.
 * @author Andrew Kerr
 */
public class LockableLong extends LockableVariable<Long> {

	private Long l;
	
	public LockableLong(PsuedoLockable lock, Long value) {
		super(lock);
		this.l = value;
	}

	@Override
	protected Long getCall() {
		return l;
	}

	@Override
	protected void setCall(Long object) {
		this.l = object;
	}

}
