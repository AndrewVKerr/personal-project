package net.mcorp.webInterface.threader;

public class ThreadManager<Pool extends ThreadPool<Pool,?>> {
	
	protected ThreadManager() {}
	
	private Pool pool;
	public synchronized Pool getPool() { return this.pool; };
	
	public void generateNewManager(Pool pool) {
		ThreadManager<Pool> manager = new ThreadManager<Pool>();
		manager.pool = pool;
		pool.manager(this);
	}
	
}
