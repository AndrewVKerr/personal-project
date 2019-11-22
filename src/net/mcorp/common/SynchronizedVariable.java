package net.mcorp.common;

public class SynchronizedVariable<Obj extends Object>{
	
	private Obj object;
	
	public SynchronizedVariable(Obj init) {
		this.object = init;
	}
	
	public SynchronizedVariable() {}

	public synchronized Obj get() {
		return this.object;
	}
	
	public synchronized void set(Obj object) {
		this.object = object;
	}
	
}
