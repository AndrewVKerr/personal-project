package net.mcorp.network.common.protocols;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.network.common.Connection;

/**
 * <h1>Protocol</h1>
 * <hr>
 * <p>An abstract class used for generic argument purposes.</p>
 * @author Andrew Kerr
 */
public abstract class Protocol<ReadData_ extends PacketData, WriteData_ extends PacketData>{
	
	protected Protocol() {};
	
	protected abstract void writeCall(OutputStream out, WriteData_ data) throws Exception;
	public void write(Connection connection, WriteData_ data) throws IOException {
		try {
			this.writeCall(connection.getOutputStream(),data);
			connection.getOutputStream().flush();
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	protected abstract ReadData_ readCall(InputStream in) throws Exception;
	public ReadData_ read(Connection connection) throws IOException {
		try {
			return this.readCall(connection.getInputStream());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
}
