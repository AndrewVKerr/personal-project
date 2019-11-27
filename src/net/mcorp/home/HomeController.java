package net.mcorp.home;

import java.io.File;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

import net.mcorp.home.devices.Devices;
import net.mcorp.home.devices.wired.DoorSensor;
import net.mcorp.network.common.utils.debug.SmartDebug;
import net.mcorp.network.server.Server;
import net.mcorp.network.server.handlers.HttpConnectionHandler;

public class HomeController extends SmartDebug implements Runnable{
	
	public static final HomeController instance = new HomeController();
	
	public final GpioController gpio;
	
	public final Devices devices = new Devices();
	
	//public final Server<HttpConnectionHandler> server;
	private final File enableGPIO = new File("./enable_gpio");
	
	public HomeController() {
		if(isRaspberry())
			gpio = GpioFactory.getInstance();
		else
			gpio = null;
		devices.addDevice(new DoorSensor(null,"BasementDoor"));
		//server = new Server<HttpConnectionHandler>(new HttpConnectionHandler(),false);
	}
	
	public boolean isRaspberry() {
		if(System.getProperty("os.name").startsWith("Linux")) {
			if(enableGPIO.exists())
				return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		while(true) {
			
		}
	}
	
	public static void main(String[] args) {
		if(instance != null) {
			//instance.server.start();
			System.out.println(instance.toString());
			instance.run();
		}
	}

	@Override
	public String toString(String indent, String indentBy) {
		String s = this.getClass().getSimpleName()+"[";
		s += "\n"+indent+indentBy+"devices = "+SmartDebug.readSmartDebug(indent+indentBy, indentBy, this.devices);
		s += "\n"+indent+"]";
		return s;
	}

}
