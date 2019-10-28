package net.mcorp.server.protocols.https.records.handshakeOLD;

import net.mcorp.server.protocols.https.HttpsRecord;
import net.mcorp.server.protocols.https.records.RecordType;

public class Handshake extends RecordType<HandshakeStub> {

	public static final Handshake record = new Handshake();
	
	private Handshake() {
		super(22);
	}

	@Override
	public HandshakeStub createNewStub(HttpsRecord record) {
		return new HandshakeStub(this,record);
	}

	@Override
	public HandshakeStub createNewStub() {
		return new HandshakeStub(this,new HttpsRecord(this, 0, 0));
	}
	
}
