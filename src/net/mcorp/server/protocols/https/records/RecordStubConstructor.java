package net.mcorp.server.protocols.https.records;

public abstract class RecordStubConstructor<R extends RecordStub> {
	
	public abstract R createResponseStub();
	
	public abstract R createListenerStub();
	
}
