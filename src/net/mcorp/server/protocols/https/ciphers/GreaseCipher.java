package net.mcorp.server.protocols.https.ciphers;

import net.mcorp.server.protocols.https.ciphers.CipherSuites.CipherSuite;

public class GreaseCipher extends CipherSuite{

	public static final GreaseCipher instance = new GreaseCipher();
	
	private GreaseCipher() {//TODO: Check to make sure that these codes have been finalised
		//FIXME:                     /\
		super(new int[] {/*0x0a0a,0x1a1a,0x2a2a,0x3a3a,*/0x4a4a,0x5a5a,0x6a6a,0x7a7a,0x8a8a,0x9a9a,0xaaaa,0xbaba,0xcaca,0xdada,0xeaea,0xfafa});
	}

	@Override
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		if(id() != -1)
			str += "\n"+indent+indentBy+"id = ["+id()+"]";
		else {
			str += "\n"+indent+indentBy+"ids = Array<Integer>[";
			int[] a = ids();
			for(int i = 0; i < a.length; i++) {
				int id = a[i];
				str += "\n"+indent+indentBy+indentBy+toHexString(id)+(i < a.length-1 ? "," : "");
			}
			str += "\n"+indent+indentBy+"]";
		}
		str += "\n"+indent+"]";
		return str;
	}
	
}
