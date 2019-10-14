package net.mcorp.server.resources;

public enum MIMEType {
	
	MP3("audio/mp3"),
	MP4("video/mp4"),
	PNG("image/png"),
	JPG("image/jpg"),
	NONE("")
	;
	
	private String type;
	private MIMEType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return this.type;
	}
	
	public static MIMEType getType(String string) {
		MIMEType type = MIMEType.valueOf(string.toUpperCase());
		if(type == null)
			return MIMEType.NONE;
		return type;
	}
	
}
