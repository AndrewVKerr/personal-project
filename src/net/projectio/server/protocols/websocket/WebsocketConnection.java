package net.projectio.server.protocols.websocket;

import java.io.IOException;
import java.io.InputStream;
import net.projectio.server.protocols.Packet;

public class WebsocketConnection extends Packet {
	
	public WebsocketFrame getNextFrame(InputStream in) throws IOException {
		WebsocketFrame frame = new WebsocketFrame();
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
		for(long l = 0; l != frame.length(); l+=(frame.length() > 0 ? 1 : -1)) {
			bite = this.getNextByte(in);
			frame.payload(Byte.parseByte(Integer.toBinaryString(Integer.parseInt(bite, 2) ^ frame.maskKeys()[j]),2));
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
	
	/**
	 * Calling this method will force the provided ticket object (if not null) to switch from the previous connection protocol to a websocket protocol.
	 * After calling this method it is assumed that you will begin to communicate using the websocket language, attempting to talk to the client as anything else
	 * can and will confuse the client. 
	 */
	public void shiftConnectionToWebsocket() {
		
	}
	
}
