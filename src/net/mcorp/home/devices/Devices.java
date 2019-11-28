package net.mcorp.home.devices;

import net.mcorp.common.pseudo.PseudoFinalArrayList;
import net.mcorp.common.utils.debug.SmartDebug;

public class Devices extends SmartDebug{
	
	private final PseudoFinalArrayList<Device> devices;
	
	public Devices(){
		devices = new PseudoFinalArrayList<Device>();
	}
	
	public Object[] getDevices(Class<? extends Device> class_) {
		return this.devices.toArray(class_);
	}
	
	public void addDevice(Device device) {
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
