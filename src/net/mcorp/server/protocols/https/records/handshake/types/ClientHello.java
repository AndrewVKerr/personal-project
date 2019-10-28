package net.mcorp.server.protocols.https.records.handshake.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ClientHello extends HandshakeType{
	
	private int sslVersion;
	public int sslVersion() { return this.sslVersion; };
	
	private int sessionIDLength;
	public int sessionIDLength() { return this.sessionIDLength; }
	
	private String sessionID;
	public String sessionID() { return this.sessionID; };
	
	private int cipher = -1;
	public int cipher() {
		return cipher;
	}
	
	public int length() {
		return 3+32+1+this.sessionIDLength();
	}
	
	@Override
	protected void readRoutine(InputStream in) throws IOException {
		this.sslVersion = Integer.parseInt(getNextNBytes(in,2),2);
		
		getNextNBytes(in,32);
		
		this.sessionIDLength = Integer.parseInt(getNextNBytes(in,1), 2);
		
		this.sessionID = "";
		for(int i = 0; i < this.sessionIDLength; i++) {
			int val = in.read();
			this.sessionID += this.toHexString(val, false);
		}
		
		int cipher = Integer.parseInt(getNextNBytes(in,2), 2);
		for(int i = 0; i < cipher; i++) {
			if(this.cipher == -1)
				this.cipher = cipher;
			System.out.println("["+(i+1)+"/"+cipher+"]Cipher: "+toHexString(Integer.parseInt(getNextNBytes(in,2), 2)));
		}
		
		int compression = Integer.parseInt(getNextNBytes(in,1), 2);
		for(int i = 0; i < compression; i++) {
			System.out.println("["+(i+1)+"/"+compression+"]Compression: "+toHexString(Integer.parseInt(getNextNBytes(in,1), 2)));
		}
		
		int extensions = Integer.parseInt(getNextNBytes(in,2), 2);
		for(int i = 0; i < extensions; i++) {
			System.out.println("["+(i+1)+"/"+extensions+"]Extensions: "+toHexString(Integer.parseInt(getNextNBytes(in,2), 2)));
		}
	}
	
	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"length = Integer["+this.length()+"],";
		str += "\n"+indent+indentBy+"sslVersion = Integer["+this.sslVersion+"],";
		str += "\n"+indent+indentBy+"sessionIDLength = Integer["+this.sessionIDLength()+"],";
		str += "\n"+indent+indentBy+"sessionID = String[\"0x"+this.sessionID+"\"]";
		str += "\n"+indent+"]";
		return str;
	}

	@Override
	public int type() {
		return 1;
	};
	
}
