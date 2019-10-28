package net.mcorp.server.protocols.https.records.handshakeOLD;

import java.util.ArrayList;
import java.util.HashMap;

import net.mcorp.server.protocols.https.records.handshakeOLD.types.ClientHello;
import net.mcorp.server.protocols.https.records.handshakeOLD.types.HelloRequest;

public class HandshakeTypes {
	
	public static final HandshakeTypes instance = new HandshakeTypes();
	
	private HandshakeTypes() { //Prevent any other instances of this class from being generated.
		boolean b = this.addHandshakeType(1,ClientHello.class);
		System.out.println("Adding ClientHello.class "+b);
		b = this.addHandshakeType(0,HelloRequest.class);
		System.out.println("Adding HelloRequest.class "+b);
	}
	
	private HashMap<Integer,Class<? extends HandshakeType>> records = new HashMap<Integer,Class<? extends HandshakeType>>();
	
	public boolean addHandshakeType(Integer type, Class<? extends HandshakeType> typeObject) {
		if(this.getHandshakeType(type) == null) {
			this.records.put(type, typeObject);
			return true;
		}
		return false;
	}
	
	public Class<? extends HandshakeType> getHandshakeType(int type) {
		return this.records.get(type);
	}
	
	public static Class<? extends HandshakeType> getStaticHandshakeType(int type) {
		return instance.getHandshakeType(type);
	}
	
	public static HandshakeType createHandshakeType(int type, HandshakeStub stub) {
		try {
			return getStaticHandshakeType(type).getConstructor(HandshakeStub.class).newInstance(stub);
		}catch(Exception e) {}
		return null;
	}
	
}
