package net.mcorp.server.protocols.http;

import net.mcorp.server.protocols.http.statuscodes.HttpStatusCode;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;

public class HttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2209225661399538898L;
	
	private Exception originalException = null;
	private String reason = null;
	private HttpStatusCode code = StandardHttpStatusCodes.Internal_Server_Error;
	
	public HttpException(String reason) {
		this.reason = reason;
	}
	
	public HttpException(String reason, HttpStatusCode code) {
		this.reason = reason;
		this.code = code;
	}
	
	public HttpException(Exception origin) {
		this.originalException = origin;
	}
	
	public HttpException(Exception origin, HttpStatusCode code) {
		this.originalException = origin;
		this.code = code;
	}
	
	public HttpException(HttpStatusCode code) {
		this.code = code;
	}
	
	public String getMessage() {
		if(this.originalException != null) {
			 return this.originalException.getLocalizedMessage();
		}else {
			if(this.reason != null) {
				return this.reason;
			}else {
				return this.code.getClass().getSimpleName()+"( "+this.code.getCode()+", "+this.code.getText()+" )";
			}
		}
	}
	
}
