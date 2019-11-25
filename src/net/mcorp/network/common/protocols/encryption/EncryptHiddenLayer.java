package net.mcorp.network.common.protocols.encryption;

import java.io.IOException;

public abstract class EncryptHiddenLayer{

	public abstract void write(int b) throws IOException;

	public abstract int read() throws IOException;
	
}
