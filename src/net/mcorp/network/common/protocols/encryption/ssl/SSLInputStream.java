package net.mcorp.network.common.protocols.encryption.ssl;

import java.io.IOException;
import net.mcorp.network.common.protocols.encryption.EncryptReadStream;

public final class SSLInputStream extends EncryptReadStream {
	
	private final SSLConnection connection;
	
	protected SSLInputStream(SSLConnection connection){
		this.connection = connection;
	}
	
	@Override
	public int read() throws IOException {
		return this.connection.hidden.read();
	}

}
