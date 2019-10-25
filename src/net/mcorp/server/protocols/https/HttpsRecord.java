package net.mcorp.server.protocols.https;

import net.mcorp.server.protocols.https.records.RecordType;
import net.mcorp.utils.BinaryUtilitys;

public class HttpsRecord implements BinaryUtilitys{
	
	private RecordType<?> type = null;
	public RecordType<?> type() { return this.type; };
	protected void type(RecordType<?> i) { this.type = i; }
	
	private int version = 0;
	public int version() { return this.version; };
	protected void version(int i) { this.version = i; }
	
	private int length = 0;
	public int length() { return this.length; };
	protected void length(int i) { this.length = i; }
	
	public HttpsRecord(RecordType<?> type, int version, int length) {
		this.type = type;
		this.version = version;
		this.length = length;
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
