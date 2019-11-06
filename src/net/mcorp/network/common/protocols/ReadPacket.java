package net.mcorp.network.common.protocols;

import java.io.InputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.exceptions.ConnectionException.ConnectionFailState;
import net.mcorp.network.common.exceptions.ProtocolException;
import net.mcorp.network.common.utils.NetworkingUtils;

/**
 * A abstract class used to define a read only {@linkplain Packet}.
 * @author Andrew Kerr
 * @param <Protocol_> - {@linkplain Protocol} - The protocol class that was used to generate this packet.
 */
public abstract class ReadPacket<Protocol_ extends Protocol<Protocol_,?,?>> extends Packet<Protocol_>{
	
	public ReadPacket(Connection connection, Protocol_ protocol) {
		super(connection, protocol);
	}

	protected abstract void readCall(InputStream in) throws Exception;
	
	public final void read() throws ConnectionException {
		try {
			this.readCall(connection.getInputStream());
		}catch(Exception e) {
			if(e instanceof ConnectionException)
				throw (ConnectionException)e;
			if(e instanceof ProtocolException)
				throw new ConnectionException((ProtocolException) e);
			throw new ConnectionException(ConnectionFailState.PROTOCOL_ERROR, e.getLocalizedMessage());
		}
	}
	
}
