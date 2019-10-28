package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

/**
 * Basically a Byte object however it has been integrated with {@linkplain LockeableVariable}.
 * @author Andrew Kerr
 */
public class LockableByte extends LockableVariable<Byte> {

	private byte bite;
	
	public LockableByte(PsuedoLockable lock) {
		super(lock);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Byte getCall() {
		return bite;
	}

	@Override
	protected void setCall(Byte object) {
		bite = object;
	}

}
