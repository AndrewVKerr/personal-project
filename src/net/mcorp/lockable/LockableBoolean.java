package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

public class LockableBoolean extends LockableVariable<Boolean>{

	private boolean bool;
	
	public LockableBoolean(PsuedoLockable lock, Boolean bool) {
		super(lock);
		this.bool = bool;
	}

	@Override
	protected Boolean getCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setCall(Boolean object) {
		// TODO Auto-generated method stub
		
	}

}
