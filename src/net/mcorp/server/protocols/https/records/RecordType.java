package net.mcorp.server.protocols.https.records;

import net.mcorp.network.common.utils.BinaryUtilitys;
import net.mcorp.server.protocols.https.HttpsRecord;

public abstract class RecordType<S extends RecordTypeStub<?>> implements BinaryUtilitys{
	
	private final int num;
	
	public RecordType(int i) {
		num = i;
	}
	
	public int getValue() {
		return num;
	}
	
	public String toHexString() {
		String str = Integer.toHexString(num);
		int x = str.length() % 2;
		return "0x"+("0".repeat(x))+str;
	}
	
	public String toString() {
		return toString("","\t");
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"value = Integer["+this.num+"],";
		str += "\n"+indent+indentBy+"hexValue = HexString[\""+this.toHexString()+"\"]";
		str += "\n"+indent+"]";
		return str;
	}
	
	public abstract S createNewStub(HttpsRecord record);
	
	public abstract S createNewStub();
	
	
	public final S castStub(Class<S> castingClass, RecordTypeStub<?> stub) {
		try {
			return castingClass.cast(stub);
		}catch(ClassCastException cce) {
			return null;
		}
	}
}