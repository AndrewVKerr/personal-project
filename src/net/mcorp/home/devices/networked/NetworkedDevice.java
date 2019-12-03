package net.mcorp.home.devices.networked;

import java.net.SocketException;

import net.mcorp.home.devices.Device;
import net.mcorp.home.devices.Devices;

public abstract class NetworkedDevice extends Device implements Runnable{
	
	public final String host;
	public final int port;
	
	private boolean running = false;
	public synchronized boolean isRunning() { return this.running; };
	public synchronized void stop() { this.running = false; };
	
	private static boolean enable_devices = false;
	
	public NetworkedDevice(Devices devices, String host, int port) {
		super(devices);
		this.host = host;
		this.port = port;
	}
	
	public NetworkedDevice(Devices devices, String host) {
		super(devices);
		this.host = host;
		this.port = 80;
	}
	
	public abstract void runCall() throws Exception;
	
	@Override
	public void run() {
		running = true;
		while(isRunning()) {
			try {
				if(enable_devices)
					this.runCall();
				Thread.sleep(1000);
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
