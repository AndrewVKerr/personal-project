package net.mcorp.server.protocols.https.records.handshake.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.server.protocols.https.records.handshake.*;

public class HelloRequest extends HandshakeType {

	public HelloRequest(HandshakeStub stub) {
		super(0,stub);
	}

	@Override
	protected void readRoutine(InputStream in) throws IOException {
		throw new IOException("Hello Request not setup yet...");
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		throw new IOException("Hello Request not setup yet...");
	}
	
}
