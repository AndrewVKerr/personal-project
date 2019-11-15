package net.mcorp.server.protocols.https.records;

import net.mcorp.network.common.utils.BinaryUtilitys;
import net.mcorp.server.protocols.https.HttpsRecord;

public abstract class RecordTypeStub<R extends RecordType<?>> implements BinaryUtilitys{
	
	private final R record;
	public R record() { return this.record; };
	
	protected int type = -1;
	public final int type() { return this.type; };
	
	protected int length = -1;
	public final int length() { return this.length; };
	public abstract void calcLength();
	
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
