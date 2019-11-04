package net.mcorp.webInterface.threader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h1>ThreadPool</h1>
 * <hr>
 * <p>
 * 		This class is responsible for maintaining a list of all of the {@linkplain ManagedRunnable}'s currently running
 * 		within this object.
 * </p>
 * @author Andrew Kerr
 * @param ExtendingPool - {@linkplain ThreadPool ThreadPool&#60;T,R&#62;} - The extending class. 
 * @param MRun - {@linkplain ManagedRunnable ManagedRunnable&#60;T,?&#62;} - The provided class is used to prevent any non-matching objects from being executed on this pool.
 */
public abstract class ThreadPool<ExtendingPool extends ThreadPool<ExtendingPool,MRun>, MRun extends ManagedRunnable<ExtendingPool,?>>{

	private ThreadManager<ExtendingPool> manager;
	public void manager(ThreadManager<ExtendingPool> manager) throws RuntimeException {
		if(this.manager != null) throw new RuntimeException("[ThreadPool<"+this.getClass().getSimpleName()+",ManagedRunnable<"+this.getClass().getSimpleName()+",?>>:MANAGER_ALREADY_SET] The manager object has already been set for this object.");
		this.manager = manager;
	}
	
	private ExecutorService pool;
	
	/**
	 * Creates a new instance of this class with a new {@linkplain ExecutorService pool}.
	 * @apiNote If you are not quite sure on what to put as the pool then I would recommend you provide null, as that will default it to a cached pool.
	 * @param pool - {@linkplain ExecutorService}/null - A new pool object that has no active threads running and is not shared by any other ThreadPool instance or null for default.
	 */
	protected ThreadPool(ExecutorService pool) {
		if(pool == null)
			this.pool = Executors.newCachedThreadPool();
		else
			this.pool = pool;
	}
	
	/**
	 * Executes the provided Runnable using the {@linkplain ThreadPool#pool}
	 * @param managedRunnable
	 */
	public void execute(MRun managedRunnable) {
		pool.execute(managedRunnable);
	}
	
}
