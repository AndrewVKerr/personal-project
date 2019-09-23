package net.projectio.server.protocols.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import net.projectio.server.packets.WebSocketPacket;

/**
 * The WebSocketFrame is a object that contains all the information necessary for sending and receiving
 * websocket frames.
 * @author Andrew Kerr
 */
public final class WebsocketFrame{
	
	protected WebsocketFrame() { owner = null; };
	
	public WebsocketFrame(Websocket websocket) { 
		if(websocket == null) 
			throw new NullPointerException("[WebsocketFrame(Websocket):NON_NULL_VALUE_EXPECTED] Expected a non null value for the \"websocket\" parameter.");
		owner = websocket; 
	};

	/**This is the {@linkplain Websocket} that was provided during the creation of the Frame.
	 * If this value is a null pointer value then this object was generated during the reading of a
	 * {@linkplain InputStream} under the function {@linkplain WebSocketPacket}.getNextFrame({@linkplain InputStream});
	 */
	public final Websocket owner;
	
	/**This boolean value is used to inform the {@linkplain Websocket} that generated this object that
	 * this frame is going to be sent to the connected client. Will only work if a {@linkplain Websocket}
	 * generated this object, will not work if you just create a new packet.
	 */
	private boolean submitFrame = false;
	/**
	 * Calling this function will inform the owner object that this frame is ready to be sent to
	 * the other device connected.
	 * @return {@linkplain Boolean} - True/False if this function has already been submitted.
	 */
	public boolean submit() { if(locked()) { return false; }; submitFrame = true; lock(); return true; };
	/**
	 * If this function returns true then the frame will be submitted to the device on the other end of
	 * the connection.
	 * @return {@linkplain Boolean} - True/False if this function will be submitted.
	 */
	public boolean submitted() { return this.submitFrame; };
	
	/**
	 * This boolean value is used to determine if this frame can have any of its values edited,
	 * if set to true all setter functions will immediately return upon being called.
	 */
	private boolean editLock = false;
	/**
	 * This function will lock all values preventing any edits to the frame.
	 */
	public void lock() { this.editLock = true; };
	/**
	 * This function will return the status of the lock on the values.
	 * @return {@linkplain Boolean} - True/False if the values of this frame cannot be altered.
	 */
	public boolean locked() { return this.editLock; };
	
	private boolean fin = false;
	public boolean fin() { return this.fin; };
	public void fin(boolean value) { if(locked()){return;}; this.fin = value;};
	
	private boolean rsv1 = false;
	public boolean rsv1() { return this.rsv1; };
	public void rsv1(boolean value) { if(locked()){return;}; this.rsv1 = value;};
	
	private boolean rsv2 = false;
	public boolean rsv2() { return this.rsv2; };
	public void rsv2(boolean value) { if(locked()){return;}; this.rsv2 = value;};
	
	private boolean rsv3 = false;
	public boolean rsv3() { return this.rsv3; };
	public void rsv3(boolean value) { if(locked()){return;}; this.rsv3 = value;};
	
	private byte opcode = 0x0001;
	public byte opcode() { return this.opcode; };
	public void opcode(byte value) { if(locked()){return;}; this.opcode = value;};
	
	private boolean mask = false;
	public boolean mask() { return this.mask; };
	public void mask(boolean value) { if(locked()){return;}; this.mask = value;};
	
	private long length = 0;
	public long length() { return this.length; };
	public void length(long value) { if(locked()){return;}; this.length = value;};
	
	private int[] maskKeys = new int[4];
	public int[] maskKeys() { return this.maskKeys; };
	public void maskKeys(int i, int value) { if(locked()){return;}; this.maskKeys[i] = value; };
	
	public static class Payload{
		
		/*private long length = 0;
		private int[][] bays = new int[4][256]; //TODO: Reminder, this extra one entry may not be needed!!!*/
		
		private String data = "";
		
		public int getInteger(long index) {
			return (data.chars().toArray()[(int) index]);
			/*System.out.println("getInteger: "+Long.compareUnsigned(index, length));
			if(0>=Long.compareUnsigned(index, length))
				throw new IndexOutOfBoundsException("There is no value at the provided index! "+Long.toUnsignedString(index));
			
			if(0<Long.compareUnsigned(index, (int) (Math.pow(2, 8)))) {//Bay 2
				int i = (int)(index-(int) (Math.pow(2, 8)));
				return bays[1][i];
			}else {//Bay 1
				return bays[0][(int)index];
			}*/
		}
		
		public void setInteger(long index, int value) {
			data += (char)value;
			/*boolean bay1 = 0>=Long.compareUnsigned(index, 256);
			boolean bay2 = 0<=Long.compareUnsigned(index, 257) && 0>=Long.compareUnsigned(index, 514);
			boolean bay3 = 0<=Long.compareUnsigned(index, 514) && 0>=Long.compareUnsigned(index, 771);
			System.out.println("Bay available: "+(bay1 ? "1" : "")+(bay2 ? " 2" : "")+(bay3 ? " 3" : "")+" 4");
			if(bay1) {
				System.out.println("Bay 1! "+index+"; "+index);
				bays[0][(int)index-1] = value;
				if(0>Long.compareUnsigned(index, length)) {
					length = index;
				}
			}else
			if(bay2) {
				try {System.in.read();}catch(Exception e){};
				int i = (int)(index-257);
				System.out.println("Bay 2! "+index+"; "+i);
				bays[1][i] = value;
				if(0>Long.compareUnsigned(i, length)) {
					length = i;
				}
			}else
			if(bay3) {
				System.out.println("Bay 3!");
				int i = (int)(index-514);
				bays[2][i] = value;
				if(0>Long.compareUnsigned(i, length)) {
					length = i;
				}
			}else {
				System.out.println("Bay 4!");
				int i = (int)(index-771);
				bays[3][i] = value;
				if(0>Long.compareUnsigned(i, length)) {
					length = i;
				}
			}*/
		}
		
		public long size() {
			return data.length();//return length;
		}
		
		public char[] values(){
			return data.toCharArray();
			//return this.bays.clone();
		}
	}
	
	private Payload payload = new Payload();
	public Payload payload(){ return this.payload; };
	//public void payload(long key, byte value) { if(locked()){return;}; this.payload.put(key, (int)value); };
	//public void payload(long key, int value) { if(locked()){return;}; this.payload.put(key,  (int)value); };
	
	/**
	 * Sends this frame out through the given OutputStream, if the clientMode parameter is set to true
	 * then the payload value will be encrypted using the maskKeys.<br>
	 * Note: Assumes that the payloads size is the length.
	 * @param out
	 * @param clientMode
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public void sendFrame(OutputStream out, boolean clientMode) throws NumberFormatException, IOException {
		this.length = payload.size();
		this.fin = true;
		String bite = "";
		bite = toBinStr(this.fin)+toBinStr(this.rsv1)+toBinStr(this.rsv2)+toBinStr(this.rsv3)+toBinStr(this.opcode,4);
		out.write(Integer.parseUnsignedInt(bite, 2));
		bite = toBinStr(clientMode)+(this.length < 125 ? toBinStr(this.length, 7) : "1111110");
		out.write(Integer.parseUnsignedInt(bite, 2));
		if(this.length > 125) {//TODO:Add Support for more then 125 values;
			if(this.length == 126) {
				bite = toBinStr(this.length,32);
				out.write(Integer.parseUnsignedInt(bite,2));
			}else {
				bite = toBinStr(this.length,64);
				out.write(Integer.parseUnsignedInt(bite,2));
			}
			//throw new IOException("Payload length exceeds current max, >125!");
		}
		if(clientMode) {//TODO:Add ClientMode Support!
			throw new IOException("Failed to send due to clientMode not being supported yet!");
		}
		for(char i : this.payload.values()) {
			out.write(i);
		}
		out.flush();
	}
	
	private String toBinStr(boolean bool) {
		return (bool ? "1" : "0");
	}
	
	private String toBinStr(byte bite, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(bite)).replace(' ', '0');
	}
	
	private String toBinStr(long bite, int len) {
		return String.format("%"+len+"s",Long.toBinaryString(bite)).replace(' ', '0');
	}
	
	public boolean isNegative(long value) {
	    return (0 != (value & 0x8000000000000000L));
	}
}
