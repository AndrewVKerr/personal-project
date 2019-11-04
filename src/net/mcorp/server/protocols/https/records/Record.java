package net.mcorp.server.protocols.https.records;

import java.io.IOException;
import java.io.OutputStream;

import net.mcorp.network.common.utils.BinaryUtils;
import net.mcorp.network.common.utils.NetworkingUtils;

public final class Record extends NetworkingUtils{
	
	private RecordStub stub;
	public RecordStub stub() { return this.stub; };
	public void stub(RecordStub stub) { if(this.stub == null) { this.stub = stub; }; };
	
	private int recordType = -1;
	public int recordType() { return (this.stub == null ? this.recordType : this.stub.recordType()); };
	
	private int version = -1;
	public int version() { return this.version; };
	
	private int length = -1;
	public int length() { return (this.stub == null ? this.length : 5+this.stub.length()); };
	
	private Record() {}
	
	public static Record generateListeningRecord(int recordType, int version, int length) {
		Record record = new Record();
		record.length = length;
		record.recordType = recordType;
		record.version = version;
		return record;
	}
	
	public static Record generateWritingRecord(int version, RecordStub stub) {
		Record record = new Record();
		record.stub = stub;
		record.version = version;
		return record;
	}
	
	public void write(OutputStream out) throws IOException {
		
		out.write(this.recordType());
		out.write(this.version());
		out.write(this.length());
		
		this.stub.write(out);
		
	}

	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"recordType = Integer["+this.toHexString(this.recordType())+"],";
		str += "\n"+indent+indentBy+"version = Integer["+this.toHexString(this.version())+"],";
		str += "\n"+indent+indentBy+"length = Integer["+this.length()+"],";
		str += "\n"+indent+indentBy+"recordStub = "+(this.stub == null ? null : this.stub.toString(indent+indentBy, indentBy));
		str += "\n"+indent+"]";
		return str;
	}
	
}
