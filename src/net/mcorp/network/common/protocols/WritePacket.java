package net.mcorp.network.common.protocols;

import java.io.OutputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;

/**
 * A abstract class used to define a write only {@linkplain Packet}.
 * @author Andrew Kerr
 * @param <Protocol_> - {@linkplain Protocol} - The protocol class that was used to generate this packet.
 */
public abstract class WritePacket<Protocol_ extends Protocol<Protocol_, ?, ?>> extends Packet<Protocol_>{
	
	public WritePacket(Connection connection, Protocol_ protocol) {
		super(connection,protocol);
	}
	
	protected abstract void writeCall(OutputStream in);
	
	public final void write() throws ConnectionException {
		this.writeCall(connection.getOutputStream());
	}
	
}
