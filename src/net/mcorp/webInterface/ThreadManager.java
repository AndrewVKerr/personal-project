package net.mcorp.webInterface;

public class ThreadManager {
	
	/**
	 * <h1>ThreadPool</h1>
	 * <hr>
	 * <p>
	 * 		This class is responsible for maintaining a list of all of the {@linkplain ManagedRunnable}'s currently running
	 * 		within this object. This object contains 
	 * </p>
	 * @author Andrew Kerr
	 */
	public abstract class ThreadPool{
		
	}
	
	public abstract class ManagedRunnable<P> implements Runnable{
		
		public abstract void execute(ThreadPool pool, P passedObject);
		
		public final void run() {
			
		}
		
	}
	
}
