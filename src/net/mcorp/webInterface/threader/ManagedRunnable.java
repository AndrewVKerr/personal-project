package net.mcorp.webInterface.threader;

import net.mcorp.webInterface.exceptions.RunnableException;

public abstract class ManagedRunnable<Pool,Obj> implements Runnable{
	
	private Obj passedObject;
	private Pool pool;
	private RunnableException exception;
	public RunnableException exception() { return this.exception; };
	
	protected ManagedRunnable(Pool pool, Obj passedObject) {
		this.passedObject = passedObject;
		this.pool = pool;
	}
	
	public abstract void execute(Pool pool, Obj passedObject) throws Exception;
	
	public final void run() {
		try {
			this.execute(pool, passedObject);
		}catch(Exception e) {
			//this.exception = e;
			return;
		}
		//this.exception = 
	}
	
}
