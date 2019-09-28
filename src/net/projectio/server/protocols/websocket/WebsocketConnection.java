package net.projectio.server.protocols.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;

import net.projectio.server.packets.WebSocketPacket;
import net.projectio.server.protocols.Packet;
import net.projectio.server.protocols.Protocol;
import net.projectio.server.protocols.http.Http;
import net.projectio.server.protocols.http.HttpPacket;
import net.projectio.server.protocols.websocket.WebsocketFrame.Payload;

public class WebsocketConnection extends Packet {
	
	public WebsocketFrame getNextFrame() throws IOException {
		InputStream in = this.ticket().socket.getInputStream();
		System.out.println("Reading next frame!");
		String f = "";
		WebsocketFrame frame = new WebsocketFrame();
		String bite = this.getNextByte(in);//Frame 1 [Fin,Rsv1,Rsv2,Rsv3,Opcode(4)]
		f+=bite;
		frame.fin((bite.charAt(0) == '1'));
		frame.rsv1((bite.charAt(1) == '1'));
		frame.rsv2((bite.charAt(2) == '1'));
		frame.rsv3((bite.charAt(3) == '1'));
		frame.opcode(Byte.parseByte(bite.substring(4), 2));
		bite = this.getNextByte(in);f+=bite;
		frame.mask((bite.charAt(0) == '1'));
		WebsocketLength len = frame.length();
		len.set7BitValue(Long.parseLong(bite.substring(1),2));
		if(len.get7BitValue() > 125) {
			if(len.get7BitValue() == 126) {
				len.setActualValue(Long.parseUnsignedLong(this.getNextNBytes(in, 2),2));
			}else {
				len.setActualValue(Long.parseUnsignedLong(this.getNextNBytes(in, 8),2));
			}
		}
		if(frame.mask()) {
			for(int i = 0; i < 4; i++) {
				bite = this.getNextByte(in);f+=bite;
				frame.maskKeys(i, Integer.parseInt(bite, 2));
			}
		}
		int j = 0;
		System.out.println("7Bit Value: "+frame.length().get7BitValue());	
		if(frame.length().get7BitValue() < 125) {
			for(byte b = 0; b < frame.length().get7BitValue(); b++) {
				bite = this.getNextByte(in);f+=bite;
				frame.payload().write((Integer.parseUnsignedInt(bite, 2) ^ frame.maskKeys()[j]));
				j = (j+1) % 4;
			}
			frame.payload().reset();
		}else {
			if(frame.length().get7BitValue() == 126) {
				System.out.println("16Bit Value: "+frame.length().get16BitValue());
				for(int i = 0; i < frame.length().get16BitValue(); i++) {
					bite = this.getNextByte(in);f+=bite;
					frame.payload().write((Integer.parseUnsignedInt(bite, 2) ^ frame.maskKeys()[j]));
					j = (j+1) % 4;
				}
				frame.payload().reset();
				System.out.println();
			}else {
				long i = 0;
				while(0<Long.compareUnsigned(frame.length().get64BitValue(), i)) {
					bite = this.getNextByte(in);f+=bite;
					frame.payload().write((Integer.parseUnsignedInt(bite, 2) ^ frame.maskKeys()[j]));
					j = (j+1) % 4;
					i++;
				}
			}
		}
		/*
		long l = 0;
		while(Long.compareUnsigned(l,frame.length())<0) {
			System.out.println(Long.toUnsignedString(l,10)+"<"+Long.toUnsignedString(frame.length(),10)+" = "+(Long.compareUnsigned(l,frame.length())<0));
			bite = this.getNextByte(in);f+=bite;
			int b = (Integer.parseUnsignedInt(bite, 2) ^ frame.maskKeys()[j]);
			try {
				System.out.println("Ping");
				frame.payload().write(b);
				System.out.println("Pong");
			}catch(Exception e) {
				System.err.println(e.getLocalizedMessage()+": "+l+"; "+b);
			}
			l++;
			j = (j+1) % 4;
		}*/
		System.out.println("DONE");
		frame.lock();
		System.out.println("Frame: "+f);
		return frame;
	}
	
	public String getNextNBytes(InputStream in, int n) throws IOException {
		String temp = "";
		for(int i = 0; i < n; i++) {
			temp += getNextByte(in);
		}
		return temp;
	}

	public String getNextByte(InputStream in) throws IOException{
		int i = in.read();
		String b = Integer.toBinaryString(i);
		return b;
	}
	
	public WebsocketFrame createResponse() {
		return new WebsocketFrame(this.ticket());
	}

	public void switchToWebsocket(Class<? extends Protocol<?>> clazz) throws NullPointerException, IOException {
		if(clazz == Http.class) {
			HttpPacket packet = (HttpPacket) this.ticket().lastPacket();
			if(packet.getHeaderValue("Connection").equalsIgnoreCase("Upgrade")) {
				System.out.println(packet.getHeaderValue("Sec-WebSocket-Key"));
				String s;
				try {
					s = new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-1").digest((packet.getHeaderValue("Sec-WebSocket-Key").concat("258EAFA5-E914-47DA-95CA-C5AB0DC85B11")).getBytes())));
				}catch(Exception e) {
					e.printStackTrace();
					return;
				}
				System.out.println(s);
				this.ticket().write("Http/1.1 101 Websocket\nConnection: Upgrade\nUpgrade: Websocket\nSec-WebSocket-Accept: "+s+"\n\n");
				this.ticket().flush();
				//Start communicating in websocket only!
				System.out.println("Websocket protocol established!");
			}
		}
	}
	
}
