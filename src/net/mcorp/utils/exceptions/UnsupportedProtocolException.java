package net.mcorp.utils.exceptions;

import net.mcorp.server.protocols.Protocol;

public class UnsupportedProtocolException extends Exception {

	private static final long serialVersionUID = 210915771270862936L;

	public UnsupportedProtocolException(String location, @SuppressWarnings("rawtypes") Protocol protocol) {
		super(
				"["+location+":UNSUPPORTED_PROTOCOL]A ticket object is attempting to perform an action with an unsupported protocol.\n"
				+ "UnSupported Protocol: "+(protocol == null ? null : protocol.getClass().getSimpleName())
		);
	}
	
}
