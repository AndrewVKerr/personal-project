package net.mcorp.server.protocols.https;

import net.mcorp.server.protocols.https.records.RecordType;
import net.mcorp.server.protocols.https.records.RecordTypeStub;
import net.mcorp.utils.BinaryUtilitys;

public class HttpsRecord implements BinaryUtilitys{
	
	private RecordType<?> type = null;
	public RecordType<?> type() { return this.type; };
	
	private RecordTypeStub<?> stub = null;
	public RecordTypeStub<?> stub(){ return this.stub; };
	
	private int version = -1;
	public int version() { return this.version; };
	public void version(int i) { if(this.version == -1) this.version = i; }
	
	private int length = -1;
	public int length() { return this.length; };
	public void calcLength() { this.length = 5+(stub == null ? 0 : stub.length()); };
	
	public HttpsRecord(RecordType<?> type, int version, int length) {
		this.type = type;
		this.version = version;
		this.length = length;
	}
	
	public HttpsRecord(RecordTypeStub<?> stub) {
		this.type = stub.record();
		this.stub = stub;
	}
	
	public String toString() {
		return this.toString("","\t");
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"type = "+(type != null ? type.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"version = Integer["+toHexString(version)+"],";
		str += "\n"+indent+indentBy+"length = Integer["+toHexString(length)+"]";
		str += "\n"+indent+"]";
		return str;
	}
	
}
