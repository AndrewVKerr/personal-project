package net.mcorp.server.protocols.https.records.handshake;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.server.protocols.https.HttpsPacket;
import net.mcorp.server.protocols.https.records.RecordStub;
import net.mcorp.server.protocols.https.records.handshake.types.ClientHello;
import net.mcorp.server.protocols.https.records.handshake.types.HandshakeType;
import net.mcorp.server.protocols.https.records.handshake.types.ServerHello;

public class HandshakeStub extends RecordStub {

	protected int length() { return this.handshakeLength(); }
	public int recordType() { return 22; }
	
	private int handshakeLength;
	public int handshakeLength() { return this.handshakeLength; };
	
	private HandshakeType handshakeType;
	public HandshakeType handshakeType() { return this.handshakeType; };
	public void handshakeType(HandshakeType handshakeType) {
		if(!this.isWriteOnly())
			return;
		this.handshakeType = handshakeType;
	}
	
	@Override
	protected void readRoutine(InputStream in) throws IOException {
		
		int type = Integer.parseInt(getNextByte(in),2);
		this.handshakeLength = Integer.parseInt(getNextNBytes(in,3),2);
		
		if(type == 0x00) { //HelloRequest
			/*
			 * The client wishes to restart the hello process as a means to generate new keys.
			 */
		}else
		if(type == 0x01) { //ClientHello
			this.handshakeType = new ClientHello();
			this.handshakeType.read(in);
		}else
			throw new IOException("[HandshakeStub.readRoutine(InputStream):UNKNOWN_TYPE] Client provided an unknow type?");
		
	}

	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		
		out.write(handshakeType.type());
		out.write(this.length());
		
		this.handshakeType.write(out);
		
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"length = Integer["+this.length()+"],";
		str += "\n"+indent+indentBy+"recordType = Integer["+this.toHexString(this.recordType())+"],";
		str += "\n"+indent+indentBy+"handshakeType = "+(this.handshakeType == null ? null : this.handshakeType.toString(indent+indentBy, indentBy))+"";
		str += "\n"+indent+"]";
		return str;
	}

}
