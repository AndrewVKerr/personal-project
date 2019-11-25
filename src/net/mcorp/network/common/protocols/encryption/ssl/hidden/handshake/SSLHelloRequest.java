package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake;

import java.io.IOException;
import java.io.InputStream;

public class SSLHelloRequest extends SSLHandshakeData {

	public SSLHelloRequest() {
		super(0);
	}
	
	protected SSLHelloRequest(InputStream in) throws IOException {
		super(0,in);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

	@Override
	protected void calcLength() {
		this.length_of_packet.set(0L);
	}
	
	public String getData() {
		return this.toBinStr(this.data_type.get(), 8)+this.toBinStr(this.length_of_packet.get(), 24);
	}

}
