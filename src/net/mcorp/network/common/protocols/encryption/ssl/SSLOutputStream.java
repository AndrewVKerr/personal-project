package net.mcorp.network.common.protocols.encryption.ssl;

import java.io.IOException;
import net.mcorp.network.common.protocols.encryption.EncryptWriteStream;

public class SSLOutputStream extends EncryptWriteStream {

	private final SSLConnection connection;
	
	protected SSLOutputStream(SSLConnection connection){
		this.connection = connection;
	}
	
	@Override
	public void write(int b) throws IOException {
		// TODO Auto-generated method stub

	}

}
