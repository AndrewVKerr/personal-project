package net.mcorp.server.protocols.https.ciphers;

import net.mcorp.server.protocols.https.ciphers.CipherSuites.CipherSuite;

public class TLS_RSA_WITH_NULL_NULL extends CipherSuite {

	public static final TLS_RSA_WITH_NULL_NULL instance = new TLS_RSA_WITH_NULL_NULL();

	private TLS_RSA_WITH_NULL_NULL() {
		super(0x00);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

}
