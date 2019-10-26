package net.mcorp.server.protocols.https.records.handshake.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.server.protocols.https.SessionID;
import net.mcorp.server.protocols.https.ciphers.CipherSuites;
import net.mcorp.server.protocols.https.compression.CompressionMethods;
import net.mcorp.server.protocols.https.extensions.Extensions;
import net.mcorp.server.protocols.https.records.handshake.*;

public class ClientHello extends HandshakeType {

	public ClientHello(HandshakeStub stub) {
		super(1, stub);
	}

	private int version;
	public final int version() { return this.version; };
	
	private int sessionLen;
	public final int sessionLength() { return this.sessionLen; };
	
	private SessionID sessionId;
	public final SessionID sessionId() { return this.sessionId; };
	
	private CipherSuites ciphers;
	public final CipherSuites ciphers() { return this.ciphers; };
	
	private CompressionMethods compressionMethods;
	public final CompressionMethods compressionMethods() { return this.compressionMethods; };
	
	private Extensions extensions;
	public final Extensions extensions() { return this.extensions; };
	
	@Override
	protected void readRoutine(InputStream in) throws IOException {
		
		this.version = Integer.parseInt(this.getNextNBytes(in,2),2);
		
		//Read in the next 32 bits (4 bytes) and ignore them!
		this.getNextNBytes(in, 32);
		
		this.sessionLen = Integer.parseInt(this.getNextByte(in),2);
		System.out.println("Length: "+this.sessionLen);
		this.sessionId = new SessionID(this.getNextNBytes(in,32));
		
		this.ciphers = new CipherSuites();
		this.ciphers.read(in);
		
		this.compressionMethods = new CompressionMethods();
		this.compressionMethods.read(in);
		
		this.extensions = new Extensions();
		this.extensions.read(in);
		
		//FIXME: Add more data
		
	}
	
	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"version = Integer["+toHexString(this.version)+"],";
		str += "\n"+indent+indentBy+"sessionLength = Integer["+this.sessionLen+"],";
		str += "\n"+indent+indentBy+"sessionId = "+(this.sessionId != null ? this.sessionId.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"ciphers = "+(this.ciphers != null ? this.ciphers.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"compressionMethods = "+(this.compressionMethods != null ? this.compressionMethods.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"extensions = "+(this.extensions != null ? this.extensions.toString(indent+indentBy, indentBy) : null)+"";
		str += "\n"+indent+"]";
		return str;
	}

}
