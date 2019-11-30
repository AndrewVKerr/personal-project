package net.mcorp.home.devices.networked;

import java.net.SocketException;

import net.mcorp.home.devices.Device;

public abstract class NetworkedDevice extends Device implements Runnable{
	
	public final String host;
	public final int port;
	
	private boolean running = false;
	public synchronized boolean isRunning() { return this.running; };
	public synchronized void stop() { this.running = false; };
	
	public NetworkedDevice(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public NetworkedDevice(String host) {
		this.host = host;
		this.port = 80;
	}
	
	public abstract void runCall() throws Exception;
	
	@Override
	public void run() {
		running = true;
		while(isRunning()) {
			try {
				this.runCall();
			}catch(Exception e) {
				e.printStackTrace();
				if(e instanceof SocketException) {
					this.stop();
					break;
				}
			}
		}
	}
	
}
