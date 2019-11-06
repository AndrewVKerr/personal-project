package net.mcorp.network.common.protocols.http.codes;

public enum SuccessStatusCodes implements StatusCode{
	
	Ok(200),
	Created(201),
	Accepted(202),
	Non_Authoritative_Information(203),
	No_Content(204),
	Reset_Content(205),
	Partial_Content(206),
	IM_Used(226)
	;
	private final int code;
	
	private SuccessStatusCodes(int i) {
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
