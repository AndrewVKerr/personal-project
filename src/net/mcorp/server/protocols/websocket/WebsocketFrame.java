package net.mcorp.server.protocols.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.websocket.WebsocketFrame.Payload.PayloadItem;

/**
 * The WebSocketFrame is a object that contains all the information necessary for sending and receiving
 * websocket frames.
 * @author Andrew Kerr
 */
public final class WebsocketFrame{
	
	protected WebsocketFrame() { owner = null; };
	
	public WebsocketFrame(Ticket websocket) { 
		if(websocket == null) 
			throw new NullPointerException("[WebsocketFrame(Websocket):NON_NULL_VALUE_EXPECTED] Expected a non null value for the \"websocket\" parameter.");
		owner = websocket; 
	};

	/**This is the {@linkplain Ticket} that was provided during the creation of the Frame.
	 * If this value is a null pointer value then this object was generated during the reading of a
	 * {@linkplain InputStream} under the function {@linkplain WebSocketPacket}.getNextFrame({@linkplain InputStream});
	 */
	public final Ticket owner;
	
	/**This boolean value is used to inform the {@linkplain Ticket} that generated this object that
	 * this frame is going to be sent to the connected client. Will only work if a {@linkplain Ticket}
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
	
	private WebsocketLength length = new WebsocketLength();
	public WebsocketLength length() { return this.length; };
	
	private int[] maskKeys = new int[4];
	public int[] maskKeys() { return this.maskKeys; };
	public void maskKeys(int i, int value) { if(locked()){return;}; this.maskKeys[i] = value; };
	
	public static class Payload{
		
		public class PayloadItem{
			
			private PayloadItem next;
			private PayloadItem prev;
			private int stored = -1;
			
			private long index = -1;
			
			public PayloadItem() {};
			
			public PayloadItem(PayloadItem last) {
				this.prev = last;
			}

			public PayloadItem getNext() {
				return this.next;
			}
			
			public PayloadItem getPrev() {
				return this.prev;
			}
			
			public int getStored() {
				return stored;
			}

			public boolean hasNext() {
				return (this.next != null);
			}

			public long sizeRoutine() {
				return (this.next == null ? 1 : this.next.sizeRoutine()+1);
			}
			
			public String toString() {
				return this.getClass().getSimpleName()+"[index="+this.index+";stored="+this.stored+"("+(char)this.stored+")]";
			}
			
		}
		
		private WebsocketFrame frame;
		
		public Payload(WebsocketFrame frame) {
			this.frame = frame;
		}
		
		private PayloadItem rootItem;
		private PayloadItem currItem;
		private long index = 0;
		
		public void reset() {
			if(rootItem == null)
				rootItem = new PayloadItem();
			currItem = rootItem;
		}
		
		public void write(int store) {
			index++;
			//System.out.print((char) store);
			if(currItem == null) {
				this.reset();
			}
			if(currItem == rootItem && currItem.stored == -1) {
				currItem.stored = store;
				index = 0;
				currItem.index = index;
			}else {
				if(!currItem.hasNext()) {
					currItem.next = new PayloadItem(currItem);
				}
				currItem = currItem.getNext();
				currItem.stored = store;
				currItem.index = index;
			}
		}
		
		public void recalcSize() {
			this.frame.length.set7BitValue(this.size());
			if(this.frame.length.get7BitValue() > 125) {
				this.frame.length.setActualValue(this.size());
			}
		}

		public void write(String str) {
			for(byte b : str.getBytes()) {
				this.write(b);
			}
		}
		
		public PayloadItem read() {
			PayloadItem pi = this.currItem;
			if(pi.hasNext()) {
				currItem = pi.getNext();
			}
			return pi;
		}
		
		public long size() {
			return (this.rootItem == null ? 0 : this.rootItem.sizeRoutine());
		}
		
	}
	
	private Payload payload = new Payload(this);
	public Payload payload(){ return this.payload; };
	public void payload(String str) {
		if(locked()) {return;};
		payload.write(str);
	}
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
	public void sendFrame(boolean clientMode) throws NumberFormatException, IOException {
		OutputStream out = this.owner.socket.getOutputStream();
		this.payload.recalcSize();
		this.fin = true;
		String bite = "";
		bite = toBinStr(this.fin)+toBinStr(this.rsv1)+toBinStr(this.rsv2)+toBinStr(this.rsv3)+toBinStr(this.opcode,4);
		out.write(Integer.parseUnsignedInt(bite, 2));
		out.flush();
		bite = toBinStr(clientMode)+toBinStr(this.length.get7BitValue(),7);
		out.write(Integer.parseUnsignedInt(bite, 2));
		out.flush();
		if(this.length.get7BitValue() > 125) {//TODO:Add Support for more then 125 values;
			if(this.length.get7BitValue() == 126) {
				bite = toBinStr(this.length.get16BitValue(),16);
				out.write(Integer.parseUnsignedInt(bite,2));
			}else {
				bite = toBinStr(this.length.get64BitValue(),64);
				out.write(Integer.parseUnsignedInt(bite,2));
			}
			//throw new IOException("Payload length exceeds current max, >125!");
		}
		if(clientMode) {//TODO:Add ClientMode Support!
			throw new IOException("Failed to send due to clientMode not being supported yet!");
		}
		PayloadItem pi = this.payload.rootItem;
		if(this.length().get7BitValue() < 125) {
			for(byte b = 0; b < this.length().get7BitValue(); b++) {
				out.write(pi.getStored());
				pi = pi.getNext();
			}
			this.payload().reset();
		}else {
			if(this.length().get7BitValue() == 126) {
				//System.out.println("16Bit Value: "+this.length().get16BitValue());
				for(int i = 0; i < this.length().get16BitValue(); i++) {
					out.write(pi.getStored());
					pi = pi.getNext();
				}
				this.payload().reset();
				//System.out.println();
			}else {
				long i = 0;
				while(0<Long.compareUnsigned(this.length().get64BitValue(), i)) {
					out.write(pi.getStored());
					pi = pi.getNext();
					i++;
				}
			}
		}
	}
	
	protected static String toBinStr(boolean bool) {
		return (bool ? "1" : "0");
	}
	
	protected static String toBinStr(byte bite, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(bite)).replace(' ', '0');
	}
	
	protected static String toBinStr(long bite, int len) {
		return String.format("%"+len+"s",Long.toBinaryString(bite)).replace(' ', '0');
	}
	
}
