package net.mcorp.home.devices;

import java.util.ArrayList;

import net.mcorp.common.utils.debug.SmartDebug;

public abstract class Device extends SmartDebug{
	
	public final Devices devices;
	
	public Device(Devices devices) {
		this.devices = devices;
	}
	
}
