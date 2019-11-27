package net.mcorp.home.devices.networked;

import net.mcorp.home.devices.Device;
import net.mcorp.network.server.ClientConnection;

public abstract class NetworkedDevice extends Device{

	public final ClientConnection connection;
	
	public NetworkedDevice(ClientConnection connection) {
		this.connection = connection;
	}
	
}
