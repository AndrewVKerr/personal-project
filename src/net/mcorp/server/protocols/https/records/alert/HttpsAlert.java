package net.mcorp.server.protocols.https.records.alert;

import net.mcorp.server.protocols.https.records.RecordStubConstructor;

public class HttpsAlert extends RecordStubConstructor<AlertStub> {

	public static final HttpsAlert instance = new HttpsAlert();
	
	private HttpsAlert() {};
	
	@Override
	public AlertStub createResponseStub() {
		return new AlertStub();
	}

	@Override
	public AlertStub createListenerStub() {
		return null;
	}

}
