package net.mcorp.network.common.protocols.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.Protocol;
import net.mcorp.network.common.protocols.ReadPacket;
import net.mcorp.network.common.protocols.http.codes.ClientErrorStatusCodes;

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
	protected void readCall(InputStream in) throws TimeoutException, IOException, ConnectionException {
		if(doesTimeout(in))
			throw new TimeoutException();
		
		int state = 0; //0: Start, 1: Headers, 2: Data
		while(state < 3) {
			String line = this.readUntil(in, '\n');
			if(state == 0) {
				String[] data = line.split(" ",3);
				if(data.length != 3)
					throw new ConnectionException(new HttpProtocolException(ClientErrorStatusCodes.Bad_Request));
				
			}
			System.out.println(line);
		}
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
