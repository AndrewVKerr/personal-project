package net.mcorp.network.server;

public class Server<ConnectionHandler_ extends ConnectionHandler>{
	
	public Server(ConnectionHandler_ handler, boolean autoStart) {
		this.runnable = new ServerRunnable<ConnectionHandler_>(this, handler);
		if(autoStart) {
			start();
		}
	}

	private Thread thread = null;
	private boolean running = false;
	
	public synchronized void start() {
		this.running = true;
		if(this.thread == null) {
			this.generateThread();
		}else {
			this.thread.start();
		}
	}
	
	public synchronized void stop() {
		this.running = false;
	};
	
	public synchronized boolean isRunning() {
		return this.running;
	};
	
	private ServerRunnable<ConnectionHandler_> runnable;
	
	public void generateThread() {
		thread = new Thread(runnable);
		thread.setName("SERVER_THREAD");
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.setDaemon(true);
		thread.start();
	}

	private int port = 2000;
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
}
