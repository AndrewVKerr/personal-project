package net.mcorp.server.protocols.https.records.handshake.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import net.mcorp.server.protocols.https.SessionID;
import net.mcorp.server.protocols.https.ciphers.CipherSuites;
import net.mcorp.server.protocols.https.ciphers.CipherSuites.CipherSuite;
import net.mcorp.server.protocols.https.compression.CompressionMethods;
import net.mcorp.server.protocols.https.compression.CompressionMethods.CompressionMethod;
import net.mcorp.server.protocols.https.extensions.Extensions;
import net.mcorp.server.protocols.https.extensions.Extensions.Extension;
import net.mcorp.server.protocols.https.records.handshake.HandshakeStub;
import net.mcorp.server.protocols.https.records.handshake.HandshakeType;

public class ServerHello extends HandshakeType {

	public ServerHello(HandshakeStub stub) {
		super(2, stub);
	}
	
	private int length;
	public final int length() { return (this.stub() != null ? this.stub().length() : this.length); };
	public final void calcLength() {
		if(!this.isReadOnly()) {
			this.extensions.calcLength();
			this.length = 39 + this.sessionLen + 5 + extensions.length();
		}
	}
	
	private int version;
	public final int version() { return this.version; };
	public final void version(int version) {
		if(!this.isReadOnly()) {
			this.version = version;
		}
	};
	
	private int sessionLen;
	public final int sessionLength() { return this.sessionLen; };
	public final void cipher(int sessionLength) {
		if(!this.isReadOnly()) {
			this.sessionLen = sessionLength;
		}
	};
	
	private SessionID sessionId;
	public final SessionID sessionId() { return this.sessionId; };
	public final void cipher(SessionID sessionId) {
		if(!this.isReadOnly()) {
			this.sessionId = sessionId;
		}
	};
	
	private CipherSuite cipher;
	public final CipherSuite cipher() { return this.cipher; };
	public final void cipher(CipherSuite cipher) {
		if(!this.isReadOnly()) {
			this.cipher = cipher;
		}
	};
	
	private CompressionMethod compressionMethod;
	public final CompressionMethod compressionMethod() { return this.compressionMethod; };
	public final void compressionMethod(CompressionMethod compressionMethod) {
		if(!this.isReadOnly()) {
			this.compressionMethod = compressionMethod;
		}
	};
	
	private Extensions extensions;
	public final Extensions extensions() { return this.extensions; };
	public final void extensions(Extensions extensions) {
		if(!this.isReadOnly()) {
			this.extensions = extensions;
		}
	};

	@Override
	protected void readRoutine(InputStream in) throws IOException {
		//type               (1 byte)
		//length             (3 bytes)
		//version            (2 bytes)
		//32 Byte Random     (32 bytes)
		//SessionID Length   (1 byte)
		//SessionID          (0-32 bytes)
		//CipherSuite        (2 bytes)
		//Compression Methods(1 byte)
		//Extensions         (?+2 bytes) [length-running total]
		throw new IOException("Unsupported read, server can only write right now.");
	}
	
	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		
		calcLength();
		
		String packet = "";
		packet += this.toBinStr(type(), 8);
		packet += this.toBinStr(length(), 8*3);
		packet += this.toBinStr(version(), 8*2);
		packet += this.toBinStr(new Random().nextLong(),8*8); // Long: 8 bytes * 8 bits
		packet += this.toBinStr(new Random().nextLong(),8*8);
		packet += this.toBinStr(new Random().nextLong(),8*8);
		packet += this.toBinStr(new Random().nextLong(),8*8);
		packet += this.toBinStr(sessionLength(),8);
		packet += sessionId().binaryString(sessionLength()*8);
		packet += this.toBinStr(cipher().id(),16);
		packet += this.toBinStr(compressionMethod.id(), 8);
		for(Extension extension : this.extensions.getExtensions()) {
			packet += this.toBinStr(extension.id(), 16);
		}
		
		for(int i = 0; i < packet.length()/8; i++) {
			out.write(Integer.parseUnsignedInt(packet.substring(i*8,i*8+8), 2));
		}
		
	}

	@Override
	public String toString(String indent, String indentBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
