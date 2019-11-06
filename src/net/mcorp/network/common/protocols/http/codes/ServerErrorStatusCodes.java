package net.mcorp.network.common.protocols.http.codes;

public enum ServerErrorStatusCodes implements ErrorStatusCodes{
	
	Internal_Server_Error(500),
	Not_Implemented(501),
	Bad_Gateway(502),
	Service_Unavailable(503),
	Gateway_Timeout(504),
	Http_Version_Not_Supported(505),
	Not_Extended(510),
	Network_Authentication_Required(511),
	Network_Read_Timeout_Error(598),
	Network_Connect_Timeout_Error(599)
	
	;
	private final int code;
	
	private ServerErrorStatusCodes(int i) {
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
