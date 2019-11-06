package net.mcorp.network.common.protocols.http.codes;

public enum ClientErrorStatusCodes implements ErrorStatusCodes{

	Bad_Request(400),
	Unauthorized(401),
	Forbidden(403),
	Not_Found(404),
	Method_Not_Allowed(405),
	Not_Acceptable(406),
	Proxy_Authentication_Required(407),
	Request_Timeout(408),
	Conflict(409),
	Gone(410),
	Length_Required(411),
	Precondition_Failed(412),
	Request_Entity_Too_Large(413),
	Request_URI_Too_Long(414),
	Unsupported_Media_Type(415),
	Requested_Range_Not_Satisfiable(416),
	Expectation_Failed(417),
	Upgrade_Required(426),
	Precondition_Required(428),
	Too_Many_Requests(429),
	Request_Header_Fields_Too_Large(431),
	Unavailable_For_Legal_Reasons(451)
	
	;
	private final int code;
	
	private ClientErrorStatusCodes(int i) {
		code = i;
	}
	
	@Override
	public int code() {
		return code;
	}

	@Override
	public String text() {
		return this.name().replaceAll("_", " ");
	}
	
}
