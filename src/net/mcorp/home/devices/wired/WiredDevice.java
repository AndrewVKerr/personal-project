package net.mcorp.home.devices.wired;

import java.io.IOException;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;

import net.mcorp.home.HomeController;
import net.mcorp.home.devices.Device;

public abstract class WiredDevice extends Device{

	public static enum Config{
		Input,Output,VirtualInput,VirtualOutput
	}
	
	private boolean virtualState = false;
	public final Config configuration;
	
	private final GpioPinDigitalInput input;
	private final GpioPinDigitalOutput output;
	
	public WiredDevice(Config config, Pin pin, String name) {
		configuration = config;
		if(config == Config.Input) {
			input = HomeController.instance.gpio.provisionDigitalInputPin(pin,name,PinPullResistance.PULL_DOWN);
			output = null;
		}else
		if(config == Config.Output){
			output = HomeController.instance.gpio.provisionDigitalOutputPin(pin,name,PinState.LOW);
			input = null;
		}else {
			output = null;
			input = null;
		}
	}
	
	/**
	 * Interacts with the device virtually, this is only functional if the GPIO is not active.
	 * @param state - {@linkplain Boolean} - The state of the device to set to.
	 * @throws IOException Thrown if the GPIO is active.
	 */
	public void virtualTrigger(boolean state) throws IOException{
		if(this.isVirtual() == false)
			throw new IOException("[WiredDevice.virtualTrigger(Boolean):PHYSICAL_DEVICE] This device is not a virtual device, it is a physical device.");
		virtualState = state;
	}
	
	/**
	 * Retrieves the internal state of the device.
	 * @return {@linkplain Boolean} - The state of the device.
	 * @throws IOException This exception is thrown by the {@linkplain #getStateCall()}.
	 */
	public boolean getState() throws IOException {
		if(this.isVirtual() == false)
			return this.getStateCall();
		else
			return this.virtualState;
	}
	
	/**
	 * Checks to see if this device is virtual or physical.
	 * @return {@linkplain Boolean} - True(Virtual)/False(Physical)
	 */
	public boolean isVirtual() {
		if(HomeController.instance.gpio == null) {
			if(this.input == null || this.output == null) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method retrieves the current state of the device through the GPIO.
	 * @return {@linkplain Boolean} - The state of the device.
	 * @throws IOException 
	 */
	public boolean getStateCall() throws IOException{
		return this.input.isHigh();
	}
	
}
