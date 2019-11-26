package net.mcorp.network.common.protocols.encryption;

import java.io.IOException;

import net.mcorp.network.common.utils.debug.SmartDebug;

public abstract class EncryptHiddenLayer extends SmartDebug{

	public abstract void write(int b) throws IOException;

	public abstract int read() throws IOException;
	
}
