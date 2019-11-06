package net.mcorp.network.common.protocols.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;
import net.mcorp.network.common.protocols.ReadPacket;

public class HttpRPacket<Protocol_ extends Protocol<Protocol_, HttpRPacket<Protocol_>, ?>> extends ReadPacket<Protocol_> {

	private final long timeout;
	
	public HttpRPacket(Connection connection, Protocol_ protocol, long timeout) {
		super(connection, protocol);
		this.timeout = timeout;
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

	@Override
	protected void readCall(InputStream in) throws TimeoutException, IOException {
		if(doesTimeout(in))
			throw new TimeoutException();
		String line = this.readUntil(in, '\n');
		System.out.println(line);
	}
	
	public boolean doesTimeout(InputStream in) throws IOException {
		long tout = System.currentTimeMillis();
		while(in.available() <= 0) {
			if(System.currentTimeMillis() - tout >= timeout)
				return true;
		}
		return false;
	}

}
