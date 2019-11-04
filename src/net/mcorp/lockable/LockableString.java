package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

public class LockableString extends LockableVariable<String> {

	private String string;
	
	public LockableString(PsuedoLockable lock) {
		super(lock);
	}

	@Override
	protected String getCall() {
		return string;
	}

	@Override
	protected void setCall(String object) {
		this.string = object;
	}

}
