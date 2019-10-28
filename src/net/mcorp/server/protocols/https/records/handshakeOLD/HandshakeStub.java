package net.mcorp.server.protocols.https.records.handshakeOLD;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.server.protocols.https.HttpsRecord;
import net.mcorp.server.protocols.https.records.RecordTypeStub;
import net.mcorp.utils.BinaryUtilitys;

public class HandshakeStub extends RecordTypeStub<Handshake> {
	
	public HandshakeStub(Handshake record, HttpsRecord https) {
		super(record, https);
	}
	
	private HandshakeType typeObject;
	public final HandshakeType typeObject() { return this.typeObject; };
	
	@Override
	public void calcLength() {
		if(typeObject == null)
			this.length = 0;
		else {
			this.typeObject.calcLength();
			this.length = 4+this.typeObject.length();
		}
	}
	
	private boolean readDone = false;
	
	public void read(InputStream in) throws IOException {
		if(readDone)
			throw new IOException("This stub has already read in its information!");
		
		//Read here
		this.type = Integer.parseInt(getNextByte(in),2);
		this.length = Integer.parseInt(getNextNBytes(in,3),2);
		
		this.typeObject = HandshakeTypes.createHandshakeType(this.type, this);
		if(this.typeObject != null)
			this.typeObject.read(in);
		else {
			this.getNextNBytes(in,this.length);
			System.err.println("Failed to interperate connection... Close me!!!");
			throw new IOException("CLOSE_ME");
		}
		
		readDone = true;
	}
	
	public String toString() {
		return this.toString("","\t");
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"type = Integer["+toHexString(type)+"],";
		str += "\n"+indent+indentBy+"length = Integer["+toHexString(length)+"],";
		str += "\n"+indent+indentBy+"typeObject = "+(this.typeObject != null ? this.typeObject.toString(indent+indentBy, indentBy) : null)+"";
		str += "\n"+indent+"]";
		return str;
	}

}
