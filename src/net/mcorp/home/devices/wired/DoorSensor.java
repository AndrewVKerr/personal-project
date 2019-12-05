package net.mcorp.home.devices.wired;

import com.pi4j.io.gpio.Pin;

import net.mcorp.home.devices.Devices;

public class DoorSensor extends WiredDevice {

	public DoorSensor(Devices devices, Pin pin, String name) {
		super(devices,(pin == null ? Config.VirtualInput : Config.Input), pin, name);
	}

	@Override
	public String toString(String indent, String indentBy) {
		String s = this.getClass().getSimpleName()+"[";
		s += "\n"+indent+indentBy+"isVirtual() -> "+this.isVirtual()+",";
		s += "\n"+indent+indentBy+"getState() -> ";
		try { s += this.getState()+","; }catch(Exception e) { s += "thrown "+e.getClass().getSimpleName()+","; }
		s += "\n"+indent+indentBy+"configuration = "+this.configuration+"";
		s += "\n"+indent+"]";
		return s;
	}

}