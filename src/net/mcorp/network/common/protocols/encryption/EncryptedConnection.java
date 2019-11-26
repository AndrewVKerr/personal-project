package net.mcorp.network.common.protocols.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;

public abstract class EncryptedConnection<Out_ extends EncryptWriteStream, In_ extends EncryptReadStream> extends Connection{

	public final Connection connection;
	
	public EncryptedConnection(Connection connection) {
		super(null);
		this.connection = connection;
	}
	
	public abstract InputStream getInputStream() throws ConnectionException;
	
	public abstract OutputStream getOutputStream() throws ConnectionException;
	
	public abstract boolean isOpen();
	
	public abstract void close() throws IOException;
	
}
