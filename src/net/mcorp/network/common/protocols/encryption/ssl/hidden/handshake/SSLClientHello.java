package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.common.CipherSuites;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.common.SessionID;

public class SSLClientHello extends SSLHandshakeData {

	public final PsudoFinalVariable<Long> ssl_tls_version = new PsudoFinalVariable<Long>();
	public final PsudoFinalVariable<SessionID> sessionID = new PsudoFinalVariable<SessionID>();
	public final PsudoFinalVariable<CipherSuites> ciphers = new PsudoFinalVariable<CipherSuites>();
	
	public SSLClientHello() {
		super(1);
		
		//ssl_tls_version.set();
	}
	
	public SSLClientHello(InputStream in) throws IOException {
		super(1,in);
		byte[] buffer = in.readNBytes(2);
		this.ssl_tls_version.set(Long.parseLong(this.toBinStr(new byte[] {buffer[0],buffer[1]}),2));
		in.readNBytes(32);
		this.sessionID.set(new SessionID(in));
		//this.ciphers.set(new CipherSuites(in));
	}

	@Override
	public String toString(String indent, String indentBy) {
		return null;
	}

	@Override
	protected void calcLength() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
