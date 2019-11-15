package net.mcorp.network.common.protocols.old.http;

import net.mcorp.network.common.exceptions.ProtocolException;
import net.mcorp.network.common.protocols.http.codes.ErrorStatusCodes;

public class HttpProtocolException extends ProtocolException {
	
	private static final long serialVersionUID = -7658843645714705220L;

	private ErrorStatusCodes code;
	
	public HttpProtocolException(ErrorStatusCodes code) {
		this.code = code;
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"message = "+this.getLocalizedMessage()+""
				+ "\n"+indent+"]";
	}

	@Override
	public String getLocalizedMessage() {
		return this.code.text();
	}

}
