package net.mcorp.server.protocols.https;

import net.mcorp.network.common.utils.BinaryUtilitys;

public final class SessionID implements BinaryUtilitys{

	private long[] idSections = new long[8];
	public final long[] idSections() { return this.idSections.clone(); };
	
	private String hexString = "0x";
	public final String hexString() { return this.hexString; };
	
	public SessionID(String binaryStr) {
		for(int i = 0; i < 8; i++) {
			idSections[i] = Integer.parseInt(binaryStr.substring(i*8,i*8+8),2);
			hexString += this.toHexString(idSections[i],false);
		}
	}

	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"idSections = LongArray[";
		str += "\n"+indent+indentBy+indentBy+"length = Integer["+this.idSections.length+"],";
		str += "\n"+indent+indentBy+indentBy+"data = Array[...]";
		str += "\n"+indent+indentBy+"],";
		str += "\n"+indent+indentBy+"hexString = HexString["+this.hexString+"]";
		str += "\n"+indent+"]";
		return str;
	}

	public String binaryString(int len) {
		String str = "";
		for(int i = 0; i < idSections.length; i++) {
			str += this.toBinStr(idSections[i], 8);
		}
		return str;
	}
	
}
