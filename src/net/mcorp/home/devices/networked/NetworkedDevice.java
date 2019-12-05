package net.mcorp.home.devices.networked;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.URLDecoder;

import javax.swing.JOptionPane;

import net.mcorp.home.devices.Device;
import net.mcorp.home.devices.Devices;

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
		String path = NetworkedDevice.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			String[] pathSegs = decodedPath.split("/");
			String finalPath = "/";
			for(String seg : pathSegs) {
				if(!seg.endsWith(".jar") && seg.length() > 0)
					finalPath += seg+"/";
			}
			File file = new File(finalPath+"/enableNetworkedDevices");
			if(file.exists())
				enable_devices = true;
			else
				System.err.println("[NetworkedDevice.checkForEnableFile():FILE_DOESNT_EXIST] "+finalPath+"/enableNetworkedDevices file does not exist!");
		} catch (UnsupportedEncodingException e) { System.err.println("[NetworkedDevice.checkForEnableFile():CANT_FIND_DIRECTORY] Could not attempt to enable through file."); e.printStackTrace();}
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
