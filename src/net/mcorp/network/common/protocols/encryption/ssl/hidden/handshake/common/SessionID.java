package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.common;

import java.io.IOException;
import java.io.InputStream;

public class SessionID {
	
	public final int length;
	public final int[] sessionID;
	
	public SessionID(int[] sessionID) {
		this.sessionID = sessionID;
		this.length = this.sessionID.length;
	}
	
	public SessionID(InputStream in) throws IOException {
		this.length = in.read();
		if(this.length > 32)
			throw new IOException("[SessionID(InputStream):ID_EXCEEDS_MAX_LENGTH] The length of the id given is too long for a valid sessionID. Its possible that the connection may have been desynched.");
		int[] sid = new int[this.length];
		for(int i = 0; i < this.length; i++) {
			sid[i] = in.read();
		}
		this.sessionID = sid;
	}
	
}
