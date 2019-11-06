package net.mcorp.network.common.protocols.http.codes;

public enum RedirectionStatusCodes implements StatusCode{
	
	Multiple_Choices(300),
	Moved_Permanently(301),
	Found(302),
	See_Other(303),
	Not_Modified(304),
	Use_Proxy(305),
	Temporary_Redirect(307),
	Permanent_Redirect(308)
	
	;
	private final int code;
	
	private RedirectionStatusCodes(int i) {
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
