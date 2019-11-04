package net.mcorp.network.common.protocols;

import java.io.InputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.utils.NetworkingUtils;

public abstract class ReadPacket<Protocol_ extends Protocol<Protocol_, ?, ?>> extends NetworkingUtils{
	
	private Connection connection;
	private Protocol_ protocol;
	
	public ReadPacket(Connection connection, Protocol_ protocol) {
		this.connection = connection;
		this.protocol = protocol;
	}
	
	public abstract void readCall(InputStream in);
	
	public final void read() throws ConnectionException {
		this.readCall(connection.getInputStream());
	}
	
	public Protocol_ protocol() {
		return this.protocol;
	}
	
}
