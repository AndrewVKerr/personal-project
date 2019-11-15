package net.mcorp.network.common.protocols.old.http;

import net.mcorp.network.common.protocols.old.Protocol;
import net.mcorp.network.common.protocols.old.TwoPacketConstructor;

public class HttpProtocol extends Protocol<HttpProtocol, TwoPacketConstructor<HttpProtocol>> {

	public static final HttpProtocol instance = new HttpProtocol();
	
	private final TwoPacketConstructor<HttpProtocol> constructor;
	
	/**
	 * {@linkplain InputStream} objects will block when you try to read from them when there is no data to read,
	 * because of this a computer could be taking a long time to respond so we will wait until either a timeout
	 * expires, or until the other computer responds. This variable is used to set the default timeout variable
	 * for all <b>NEW</b> {@linkplain HttpRPacket}'s.
	 */
	private long timeout = 10000;
	public synchronized long timeout() { return this.timeout; };
	public synchronized void timeout(long timeout) { this.timeout = timeout; };
	
	private HttpProtocol() {
		constructor = new TwoPacketConstructor<HttpProtocol>(this, HttpRPacket.class, HttpWPacket.class);
	}
	
	@Override
	public TwoPacketConstructor<HttpProtocol> getConstructor() {
		// TODO Auto-generated method stub
		return null;
	}

}
