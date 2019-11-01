package net.mcorp.webInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.mcorp.webInterface.exceptions.RunnableException;

public class ThreadManager {
	
	/**
	 * <h1>ThreadPool</h1>
	 * <hr>
	 * <p>
	 * 		This class is responsible for maintaining a list of all of the {@linkplain ManagedRunnable}'s currently running
	 * 		within this object.
	 * </p>
	 * @author Andrew Kerr
	 * @param T - {@linkplain ThreadPool ThreadPool&#60;T,R&#62;} - The extending class. 
	 * @param R - {@linkplain ManagedRunnable ManagedRunnable&#60;T,?&#62;} - The provided class is used to prevent any non-matching objects from being executed on this pool.
	 */
	public abstract class ThreadPool<T extends ThreadPool<T,R>,R extends ManagedRunnable<T,?>>{

		private ExecutorService pool;
		
		/**
		 * Creates a new instance of this class with a new {@linkplain ExecutorService pool}.
		 * @apiNote If you are not quite sure on what to put as the pool then I would recommend you provide null, as that will default it to a cached pool.
		 * @param pool - {@linkplain ExecutorService}/null - A new pool object that has no active threads running and is not shared by any other ThreadPool instance or null for default.
		 */
		public ThreadPool(ExecutorService pool) {
			if(pool == null)
				this.pool = Executors.newCachedThreadPool();
			else
				this.pool = pool;
		}
		
		/**
		 * Executes the provided Runnable using the {@linkplain ThreadPool#pool}
		 * @param managedRunnable
		 */
		public void execute(R managedRunnable) {
			pool.execute(managedRunnable);
		}
		
	}
	
	public abstract class ManagedRunnable<T,P> implements Runnable{
		
		private P passedObject;
		private T pool;
		private RunnableException exception;
		public RunnableException exception() { return this.exception; };
		
		public ManagedRunnable(T pool, P passedObject) {
			this.passedObject = passedObject;
			this.pool = pool;
		}
		
		public abstract void execute(T pool, P passedObject) throws Exception;
		
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
	
}
