package net.mcorp.home.devices.networked;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;

import javax.swing.JOptionPane;

import net.mcorp.home.devices.Device;
import net.mcorp.home.devices.Devices;
import net.mcorp.networked.interfaces.server.Server;

public abstract class NetworkedDevice extends Device implements Runnable{
	
	public final String host;
	public final int port;
	
	private boolean running = false;
	public synchronized boolean isRunning() { return this.running; };
	public synchronized void stop() { this.running = false; };
	
	private static boolean enable_devices = false;
	public static final boolean isEnabled() { return enable_devices; }
	public static final boolean promptEnable() {
		if(enable_devices)
			return true;
		int res = JOptionPane.showConfirmDialog(null, "Would you like to enable networked devices?", "Enable Network Devices?", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION) {
			enable_devices = true;
			return true;
		}
		return false;
	}
	public static final void checkForEnableFile() {
		if(Server.workingDirectory() != null && new File(Server.workingDirectory().getAbsolutePath()+"/enableNetworkedDevices").exists()) {
			enable_devices = true;
		}else {
			System.err.println("[NetworkedDevice.checkForEnableFile():FILE_NOT_FOUND] Could not find the file, assuming the devices should be disabled.");
		}
	}
	
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
					synchronized(this) {
						this.runCall();
					}
				else {
					System.err.println("[NetworkedDevice.run():DEVICE_DISABLED] All networked devices are currently disabled. Halting ["+this.getClass().getSimpleName()+"] object thread...");
					this.stop();
					break;
				}
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
