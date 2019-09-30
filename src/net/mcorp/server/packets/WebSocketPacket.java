package net.mcorp.server.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import net.mcorp.server.Packet;
import net.mcorp.server.Ticket;
import net.mcorp.utils.resources.Websocket;

@Deprecated
public class WebSocketPacket extends Packet{

	public WebSocketPacket(Ticket ticket) {
		super(ticket);
	}
	
	/**
	 * The WebSocketFrame is a object that contains all the information necessary for sending and receiving
	 * websocket frames.
	 * @author ThePuppet
	 */
	public static final class WebSocketFrame{
		
		protected WebSocketFrame() { owner = null; };
		
		protected WebSocketFrame(Websocket websocket) { owner = websocket; };

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
		
		private ArrayList<Integer> payload = new ArrayList<Integer>();
		public int[] payload() { int[] b = new int[this.payload.size()]; for(int i = 0; i < this.payload.size(); i++) {b[i] = this.payload.get(i); }; return b; };
		public byte[] payloadBytes() { byte[] b = new byte[this.payload.size()]; for(int i = 0; i < this.payload.size(); i++) {b[i] = this.payload.get(i).byteValue(); }; return b; };
		public void payload(byte value) { if(locked()){return;}; this.payload.add((int)value); };
		public void payload(int value) { if(locked()){return;}; this.payload.add((int)value); };
		public void payload(String value) { if(locked()){return;}; for(byte c : value.getBytes()) {this.payload.add((int)c); };}
		
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
				throw new IOException("Payload length exceeds current max, >125!");
			}
			if(clientMode) {//TODO:Add ClientMode Support!
				throw new IOException("Failed to send due to clientMode not being supported yet!");
			}
			for(int i : this.payload) {
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
	}

	public WebSocketFrame getNextFrame(InputStream in) throws IOException {
		WebSocketFrame frame = new WebSocketFrame();
		String bite = this.getNextByte(in);//Frame 1 [Fin,Rsv1,Rsv2,Rsv3,Opcode(4)]
		frame.fin((bite.charAt(0) == '1'));
		frame.rsv1((bite.charAt(1) == '1'));
		frame.rsv2((bite.charAt(2) == '1'));
		frame.rsv3((bite.charAt(3) == '1'));
		frame.opcode(Byte.parseByte(bite.substring(4), 2));
		bite = this.getNextByte(in);
		frame.mask((bite.charAt(0) == '1'));
		frame.length(Long.parseLong(bite.substring(1),2));
		if(frame.length() > 125) {
			if(frame.length() == 126) { //Next 32 bits are length!
				
			}else { //Next 64 bits are length!
				
			}
			throw new IOException("Cannot decode frames bigger then 7 bits! >7 [FIXME]"); //FIXME: Add 32 and 64 bit support!
		}
		if(frame.mask()) {
			for(int i = 0; i < 4; i++) {
				bite = this.getNextByte(in);
				frame.maskKeys(i, Integer.parseInt(bite, 2));
			}
		}
		int j = 0;
		for(long l = 0; l != frame.length; l+=(frame.length > 0 ? 1 : -1)) {
			bite = this.getNextByte(in);
			frame.payload(Byte.parseByte(Integer.toBinaryString(Integer.parseInt(bite, 2) ^ frame.maskKeys[j]),2));
			j = (j+1) % 4;
		}
		frame.lock();
		return frame;
	}
	
	public String getNextByte(InputStream in) throws IOException{
		int i = in.read();
		String b = Integer.toBinaryString(i);
		return b;
	}
	
	public WebSocketFrame frame;
	
	@Override
	public void readInPacket() throws IOException {
		this.startReading();
		InputStream in = this.ticket().socket.getInputStream();
		try {
			frame = this.getNextFrame(in);
		}catch(NumberFormatException nfe) {
			throw new IOException("[CLOSED_SOCKET]");
		}
		this.finishReading();
	}

	public WebSocketFrame createNewResponse(Websocket websocket) {
		
		return new WebSocketFrame(websocket);
		
	}

}
