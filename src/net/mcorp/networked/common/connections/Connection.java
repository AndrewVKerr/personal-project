package net.mcorp.networked.common.connections;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.common.utils.debug.SmartDebug;

public abstract class Connection extends SmartDebug{
	
	public abstract OutputStream getOutputStream(boolean raw) throws IOException;
	
	public OutputStream getOutputStream() throws IOException{ return this.getOutputStream(false); }
	
	public abstract InputStream getInputStream(boolean raw) throws IOException;
	
	public InputStream getInputStream() throws IOException{ return this.getInputStream(false); }
	
	public abstract void close() throws IOException;
	
}
