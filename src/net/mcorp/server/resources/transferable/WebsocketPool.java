package net.mcorp.server.resources.transferable;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.HttpException;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.server.protocols.websocket.Websocket;
import net.mcorp.server.protocols.websocket.WebsocketFrame;
import net.mcorp.server.protocols.websocket.WebsocketThread;

public abstract class WebsocketPool extends TransferableObject {
	
	private ArrayList<WebsocketThread> threads = new ArrayList<WebsocketThread>();
	
	private class ShutdownHook implements Runnable{

		public WebsocketPool pool;
		
		public ShutdownHook(WebsocketPool websocketPool) {
			pool = websocketPool;
		}

		@Override
		public void run() {
			for(WebsocketThread thread : pool.threads) {
				if(thread != null) { 
					if(thread.state() == WebsocketThread.WebsocketState.Open) {
						try {
							WebsocketFrame frame = thread.connection().createResponse();
							frame.opcode((byte)0x8);
							frame.lock();
							frame.sendFrame(false);
						} catch (Exception e) {}
					}
					try {
						thread.connection().ticket().close();
					}catch(Exception e) {}
				}
			}
		}
		
	}
	
	public WebsocketPool() {
		Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook(this)));
	}
	
	@Override
	public synchronized void execute(Ticket ticket) throws Exception {
		try {
			HttpPacket packet = (HttpPacket) ticket.currentPacket();
			if(packet.getHeaderValue("Connection").equalsIgnoreCase("Upgrade")) {
				//System.out.println(packet.getHeaderValue("Sec-WebSocket-Key"));
				String s;
				try {
					s = new String(Base64.getEncoder().encode(MessageDigest.getInstance("SHA-1").digest((packet.getHeaderValue("Sec-WebSocket-Key").concat("258EAFA5-E914-47DA-95CA-C5AB0DC85B11")).getBytes())));
				}catch(Exception e) {
					e.printStackTrace();
					return;
				}
				//System.out.println(s);
				ticket.write("Http/1.1 101 Websocket\nConnection: Upgrade\nUpgrade: Websocket\nSec-WebSocket-Accept: "+s+"\n\n");
				ticket.flush();
				//Start communicating in websocket only!
				//System.out.println("Websocket protocol established!");
				ticket.protocol(Websocket.protocol);
				WebsocketThread t = Websocket.handler.generateThread(ticket);
				t.resource(this);
				ticket.server.pool.execute(t);
				ticket.autoClose(false);
				threads.add(t);
			}else {
				throw new HttpException("Failed to establish connection, missing Connection header!",StandardHttpStatusCodes.Bad_Request);
			}
		}catch(Exception e) {
			if(e instanceof HttpException)
				throw e;
			e.printStackTrace();
		}
	}

	public abstract void onDataRecieved(WebsocketFrame nextFrame);
	
	public synchronized void sendMassData(String data) {
		for(WebsocketThread thread : threads.toArray(new WebsocketThread[] {})) {
			System.out.println(thread);
			if(thread != null) { 
				if(thread.state() == WebsocketThread.WebsocketState.Open) {
					try {
						WebsocketFrame frame = thread.connection().createResponse();
						frame.opcode((byte)0x1);
						frame.payload(data);
						frame.lock();
						frame.sendFrame(false);
					}catch(Exception e) {
						thread.error(e);
					}
				}else {
					if(thread.state() != WebsocketThread.WebsocketState.Opening) {
						threads.remove(thread); //Thread is no longer needed as it is closing or closed.
					}
				}
			}else {
				threads.remove(thread);
			}
		}
	}

}
