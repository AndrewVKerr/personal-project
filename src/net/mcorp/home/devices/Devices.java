package net.mcorp.home.devices;

import java.io.File;
import java.util.ArrayList;

import net.mcorp.common.pseudo.PseudoFinalArrayList;
import net.mcorp.common.utils.debug.SmartDebug;

public class Devices extends SmartDebug{
	
	public final File folder;
	
	private final PseudoFinalArrayList<Device> devices;
	
	public Devices(File folder){		
		devices = new PseudoFinalArrayList<Device>();
		this.folder = folder;
		System.out.println(this.folder.getAbsolutePath());
		if(this.folder.exists() == false)
			if(this.folder.mkdir())
				throw new RuntimeException("[Devices(File):FOLDER_NON_EXIST_CANT_CREATE] Could not create a devices folder...");
	}
	
	public Object[] getDevices(Class<? extends Device> class_) {
		return this.devices.toArray(class_);
	}
	
	public void addDevice(Device device) {
		if(device.devices != this)
			throw new RuntimeException("[Devices.addDevice(Device):DEVICE_CANNOT_GET_PARENT] The device provided does not belong to this devices list.]");
		this.devices.add(device);
	}
	
	public void removeDevice(Device device) {
		this.devices.remove(device);
	}
	
	public void publish() {
		this.devices.publish();
	}

	@Override
	public String toString(String indent, String indentBy) {
		String s = this.getClass().getSimpleName()+"[";
		s += "\n"+indent+indentBy+this.devices.toString(indent+indentBy, indentBy);
		s += "\n"+indent+"]";
		return s;
	}
	
}
