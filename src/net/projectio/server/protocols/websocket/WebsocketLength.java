package net.projectio.server.protocols.websocket;

public class WebsocketLength {
	
	private boolean lock = false;
	public boolean isLocked() { return this.lock; };
	public void lock() { this.lock = true; };
	
	private byte value7Bit = 0;
	public byte get7BitValue() { return this.value7Bit; };
	
	private int value16Bit = 0;
	public int get16BitValue() { return this.value16Bit; };
	
	private long value64Bit = 0;
	public long get64BitValue() { return this.value64Bit; };
	
	public void setValue(long value) {
		if(0>Long.compareUnsigned(value, 125)) {
			value7Bit = (byte) value;
		}else {
			if(0>Long.compareUnsigned(value, (long)Math.pow(2, Short.SIZE))) {
				value7Bit = (byte) 126;
				value16Bit = (int) value;
			}else {
				value7Bit = (byte) 127;
				value64Bit = value;
			}
		}
	}
	
}
