package net.mcorp.lockable;

import net.mcorp.lockable.PsuedoLockable.LockableVariable;

public class LockableChar extends LockableVariable<Character> {

	private char chr = 0x0;
	
	public LockableChar(PsuedoLockable lock) {
		super(lock);
	}

	@Override
	protected Character getCall() {
		return chr;
	}

	@Override
	protected void setCall(Character object) {
		this.chr = object;
	}

}
