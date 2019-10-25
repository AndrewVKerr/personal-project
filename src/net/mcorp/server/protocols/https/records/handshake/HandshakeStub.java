package net.mcorp.server.protocols.https.records.handshake;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.server.protocols.https.HttpsRecord;
import net.mcorp.server.protocols.https.records.RecordTypeStub;
import net.mcorp.utils.BinaryUtilitys;

public class HandshakeStub extends RecordTypeStub<Handshake> {

	public HandshakeStub(Handshake record, HttpsRecord https) {
		super(record, https);
	}

	private int type = -1;
	public final int type() { return this.type; };
	
	private int length = -1;
	public final int length() { return this.length; };
	
	public boolean readDone = false;
	
	public void read(InputStream in) throws IOException {
		if(readDone)
			throw new IOException("This stub has already read in its information!");
		
		//Read here
		this.type = Integer.parseInt(getNextByte(in),2);
		this.length = Integer.parseInt(getNextNBytes(in,3),2);
		
		readDone = true;
	}
	
	public String toString() {
		return this.toString("","\t");
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"type = Integer["+toHexString(type)+"],";
		str += "\n"+indent+indentBy+"length = Integer["+toHexString(length)+"]";
		str += "\n"+indent+"]";
		return str;
	}

}
