package net.mcorp.network.common.protocols.http;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;

public class HttpProtocol extends Protocol<HttpProtocol, HttpRPacket<HttpProtocol>, HttpWPacket<HttpProtocol>> {

	public static final HttpProtocol instance = new HttpProtocol();
	
	/**
	 * {@linkplain InputStream} objects will block when you try to read from them when there is no data to read,
	 * because of this a computer could be taking a long time to respond so we will wait until either a timeout
	 * expires, or until the other computer responds. This variable is used to set the default timeout variable
	 * for all <b>NEW</b> {@linkplain HttpRPacket}'s.
	 */
	private long timeout = 10000;
	public synchronized long timeout() { return this.timeout; };
	public synchronized void timeout(long timeout) { this.timeout = timeout; };
	
	private HttpProtocol() {}
	
	@Override
	public HttpRPacket<HttpProtocol> createNewReadPacket(Connection connection) {
		return new HttpRPacket<HttpProtocol>(connection,this,timeout);
	}

	@Override
	public HttpWPacket<HttpProtocol> createNewWritePacket(Connection connection) {
		return new HttpWPacket<HttpProtocol>(connection,this);
	}

}
