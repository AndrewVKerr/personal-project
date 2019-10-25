package net.mcorp.server.protocols.https.records;

import net.mcorp.server.protocols.https.HttpsRecord;
import net.mcorp.utils.BinaryUtilitys;

public abstract class RecordTypeStub<R extends RecordType<?>> implements BinaryUtilitys{
	
	private final R record;
	public R record() { return this.record; };
	
	private final HttpsRecord https;
	public HttpsRecord httpsRecord() { return this.https; };
	
	public RecordTypeStub(R record, HttpsRecord https) {
		this.record = record;
		this.https = https;
	}

	public String toString() {
		return this.toString("", "\t");
	}
	
	public abstract String toString(String indent, String indentBy);
	
}
