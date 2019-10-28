package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

/**
 * Basically an Integer object however it has been integrated with {@linkplain LockeableVariable}.
 * @author Andrew Kerr
 */
public class LockableInteger extends LockableVariable<Integer>{

	private Integer integer;
	
	public LockableInteger(PsuedoLockable lock, int value) {
		super(lock);
		integer = value;
	}

	@Override
	protected Integer getCall() {
		return integer;
	}

	@Override
	protected void setCall(Integer object) {
		integer = object;
	}

}
