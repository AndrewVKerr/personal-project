package net.mcorp.server.protocols.https.records.handshake;

import net.mcorp.server.protocols.https.records.RecordStubConstructor;

public class Handshake extends RecordStubConstructor<HandshakeStub> {

	public static final Handshake instance = new Handshake();
	
	private Handshake() {};
	
	@Override
	public HandshakeStub createResponseStub() {
		HandshakeStub stub = new HandshakeStub();
		stub.writeOnly();
		return stub;
	}

	@Override
	public HandshakeStub createListenerStub() {
		HandshakeStub stub = new HandshakeStub();
		return stub;
	}

}
