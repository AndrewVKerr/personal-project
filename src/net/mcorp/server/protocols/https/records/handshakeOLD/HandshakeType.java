package net.mcorp.server.protocols.https.records.handshakeOLD;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.network.common.utils.BinaryUtilitys;

public abstract class HandshakeType implements BinaryUtilitys{
	
	private final int type;
	public final int type() { return this.type; };
	
	private final HandshakeStub stub;
	public final HandshakeStub stub() { return this.stub; };
	
	protected int length;
	public final int length() { return (this.stub() != null ? this.stub().length() : this.length); };
	public abstract void calcLength();
	
	public HandshakeType(int type, HandshakeStub stub) {
		this.type = type;
		this.stub = stub;
	}
	
	private boolean readFinished = false;
	public final boolean isReadOnly() { return this.readFinished; };
	public final void read(InputStream in) throws IOException {
		if(isWriteOnly())
			throw new IOException("Read Routine cannot run as writeOnly is set.");
		if(readFinished)
			throw new IOException("Read Routine has already been ran once!");
		this.readRoutine(in);
		this.readFinished = true;
	}
	protected abstract void readRoutine(InputStream in) throws IOException;
	
	private boolean writeFinished = false;
	public final boolean isWriteOnly() { return this.writeFinished; };
	public final void write(OutputStream out) throws IOException{
		if(isReadOnly())
			throw new IOException("Write Routine cannot run as readOnly is set.");
		if(writeFinished)
			throw new IOException("Write Routine has already been ran once!");
		this.writeRoutine(out);
		this.writeFinished = true;
	}
	protected abstract void writeRoutine(OutputStream out) throws IOException;
	
	public abstract String toString(String indent, String indentBy);
	
}
