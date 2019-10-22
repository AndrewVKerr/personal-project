package net.mcorp.server.protocols.http.statuscodes;

import java.util.HashMap;

public final class HttpStatusCodes {
	
	public static HttpStatusCodes instance = new HttpStatusCodes();
	
	private HttpStatusCode clientError;
	
	/**
	 * Creates a new HttpStatusCodes object and adds the Standard Http Status Codes immediately.
	 */
	public HttpStatusCodes() {
		for(HttpStatusCode code : StandardHttpStatusCodes.values()) {
			try {
				addCode(code);
			} catch (Exception e) {
				e.printStackTrace(); //THIS SHOULD NEVER HAPPEN!!!
			}
		}
	}
	
	private final HashMap<Integer,HttpStatusCode> codes = new HashMap<Integer,HttpStatusCode>();
	public HttpStatusCode getCode(int code) {
		return codes.getOrDefault(code, clientError);
	}
	
	public void addCode(HttpStatusCode code) throws Exception {
		if(codes.get(code.getCode()) != null)
			throw new Exception("The provided code is using a numeric code that has already been reserved by an existing code.");
		codes.put(code.getCode(), code);
		if(code.getCode() == 400) {
			clientError = code;
		}
	}
	
}
