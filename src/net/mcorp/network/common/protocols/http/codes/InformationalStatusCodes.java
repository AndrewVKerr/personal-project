package net.mcorp.network.common.protocols.http.codes;

public enum InformationalStatusCodes implements StatusCode{

	Continue(100),
	Switching_Protocol(101)
	;
	
	private final int code;
	
	private InformationalStatusCodes(int i) {
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
