package net.mcorp.network.common.protocols;

import java.io.InputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.utils.NetworkingUtils;

public abstract class WritePacket<Protocol_ extends Protocol<Protocol_, ?, ?>> extends NetworkingUtils{

	private Connection connection;
	private Protocol_ protocol;
	
	public WritePacket(Connection connection, Protocol_ protocol) {
		this.connection = connection;
		this.protocol = protocol;
	}
	
	public abstract void writeCall(InputStream in);
	
	public final void write() throws ConnectionException {
		this.writeCall(connection.getInputStream());
	}
	
	public Protocol_ protocol() {
		return this.protocol;
	}
	
}
