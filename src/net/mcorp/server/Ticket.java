package net.mcorp.server;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import net.mcorp.server.protocols.Packet;
import net.mcorp.server.protocols.Protocol;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.server.protocols.websocket.Websocket;
import net.mcorp.server.protocols.websocket.WebsocketConnection;
import net.mcorp.server.protocols.websocket.WebsocketFrame;
import net.mcorp.server.protocols.websocket.WebsocketFrame.Payload;
import net.mcorp.server.protocols.websocket.WebsocketFrame.Payload.PayloadItem;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.server.resources.transferable.TransferableObject;
import net.mcorp.utils.exceptions.TimedOutException;

public class Ticket implements Runnable {

	public final Socket socket;
	public final Server server;
	
	private Packet lastPacket;
	public synchronized final Packet lastPacket() { return this.lastPacket; };
	
	private Packet currentPacket;
	public synchronized final Packet currentPacket() { return this.currentPacket; };
	
	private Protocol<?> protocol;
	public synchronized final Protocol<?> protocol(){ return this.protocol; };
	public synchronized final void protocol(Protocol<?> protocol) { 
		if(protocol == null) 
			throw new NullPointerException("INVALID_PARAMETER"); 
		this.protocol = protocol; 
	};
	
	private boolean autoClose = true;
	public synchronized boolean autoClose() { return this.autoClose; };
	public synchronized void autoClose(boolean bool) { this.autoClose = bool; };
	
	public synchronized void write(byte[] data) throws IOException{
		if(socket.getOutputStream() == null || socket.isOutputShutdown())
			throw new IOException("Output Closed!");
		socket.getOutputStream().write(data);
	}
	
	public synchronized void write(String str) throws IOException{
		this.write(str.getBytes());
	}
	
	public synchronized void write(File file) throws IOException{
		this.write(Files.readAllBytes(file.toPath()));
	}
	
	public synchronized Packet getPacket(Protocol<?> protocol) throws IOException {
		try {
			Packet p = protocol.generateNewPacketObject(this);
			this.lastPacket = this.currentPacket;
			this.currentPacket = p;
			return p;
		}catch(Exception e) {
			if(e instanceof IOException) {
				throw ((IOException) e);
			}
			IOException io = new IOException("An Exception has occurred while attempting to generate a new Packet! See Suppressed!");
			io.addSuppressed(e);
			throw io;
		}
	}
	
	public synchronized void flush() throws IOException {
		this.socket.getOutputStream().flush();
	}
	
	public synchronized void close() throws IOException{
		if(this.socket.getOutputStream() != null && !this.socket.isOutputShutdown())
			this.socket.getOutputStream().flush();
		this.socket.close();
	}
	
	public Ticket(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		
		try {
			HttpPacket packet = (HttpPacket) this.getPacket(Http.protocol);
			this.protocol(Http.protocol);
			packet.readFromTicket();
			System.out.println(packet.Url());
			this.lastPacket = packet;
			ResourceUrl resourceUrl = this.server.fileTree.getResource(packet.Url(), "/");
			if(resourceUrl != null) {
				TransferableObject transferableObj = resourceUrl.resource();
				if(transferableObj != null) {
					transferableObj.execute(this);
				}else {
					HttpPacket response = Http.protocol.generateNewPacketObject(this);
					response.Version("Http/1.1");
					response.StatusCode(StandardHttpStatusCodes.No_Content);
					response.setHeaderValue("type", "plain/text");
					response.Payload("No Content".getBytes());
					response.lock();
					response.writeToTicket();
				}
			}else {
				HttpPacket response = Http.protocol.generateNewPacketObject(this);
				response.Version("Http/1.1");
				response.StatusCode(StandardHttpStatusCodes.Not_Found);
				response.setHeaderValue("type", "plain/text");
				response.lock();
				response.writeToTicket();
			}
			/*if(packet.Url().equals("/Websocket")) {
				WebsocketConnection web = Websocket.protocol.generateNewPacketObject(this);
				web.switchToWebsocket(Http.class);
				this.lastPacket = web;
				System.out.println("Reading!");
				while(true) {
					try {
						System.out.println("1");
						WebsocketFrame frame = web.getNextFrame();
						if(frame.opcode() == (byte)8)
							break;
						System.out.println("2");
						System.out.println("Frame Len: "+frame.length());
						System.out.println("3");
						Payload payload = frame.payload();
						System.out.println("Payload Len: "+payload.size());
						String pay_load = "";
						for(int i = 0; i < payload.size(); i++) {
							PayloadItem pi = payload.read();
							System.out.println(pi);
							pay_load += (char)pi.getStored();
						}
						System.out.println();
						System.out.println("4");
						WebsocketFrame f = web.createResponse();
						f.opcode((byte)1);
						f.payload(pay_load);
						f.lock();
						f.sendFrame(false);
					}catch(Exception e) {
						e.printStackTrace();
						if(e instanceof NumberFormatException)
							break;
					}
				}
				System.out.println("Closing Websocket Connection!");
				
			}else {
				if(packet.Url().equals("/favicon.ico")) {
					HttpPacket response = Http.protocol.generateNewPacketObject(this);
					response.Version("Http/1.1");
					response.StatusCode(404);
					response.setHeaderValue("type", "image/png");
					response.lock();
					response.writeToTicket();
				}else {
					System.out.println("Running!");
				}
			}*/
		} catch (Exception e) {
			if(e instanceof TimedOutException) {
				try {
					HttpPacket response = Http.protocol.generateNewPacketObject(this);
					response.Version("Http/1.1");
					response.StatusCode(StandardHttpStatusCodes.Request_Timeout);
					response.setHeaderValue("type", "image/png");
					response.lock();
					response.writeToTicket();
				}catch(Exception e1) {}
			}
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*try {
			HttpPacket packet = (HttpPacket) this.readPacket(HttpPacket.class);
			if(packet == null) {
				this.write("Http/1.1 500 ServerException\n\n");
			}else {
				try {
					packet.readInPacket();
				}catch(Exception e) {
					if(e instanceof IOException) {
						if(e instanceof TimedOutException) {
							this.write("Http/1.1 408 Request Timed Out\n\n");
						}else {
							e.printStackTrace();
							this.write("Http/1.1 400 Client Side Exception\n\n"+e.getClass().getSimpleName()+": "+e.getLocalizedMessage());
						}
						this.close();
						return;
					}
				}
				System.out.println(packet.getValue(HttpPacket.URL));
				Resource resource = this.server.fileTree.search(packet.getValue(HttpPacket.URL));
				if(resource != null) {
					resource.writeToTicket(this);
				}else {
					if(this.server.fileTree.notFound != null)
						this.server.fileTree.notFound.writeToTicket(this);
					else {
						this.write("Http/1.1 404 Not Found\n\n");
						this.write("Failed to retrieve the notFound file, if this is happening assume that there was an issue with loading the Resource Structure!");
					}
				}
			}
		}catch(Exception e) {
			if(e instanceof SocketException) {
				if(e.getLocalizedMessage().contains("socket write error"))//Skip if Socket was closed before it could finish writing.
					return;
				int i = JOptionPane.showConfirmDialog(null, "An Exception has Occurred, press OK to close the server or CANCEL to cancel closing the Server.\nException:\n"+e.getClass().getSimpleName()+": "+e.getLocalizedMessage(), "SocketException", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null);
				if(i == JOptionPane.OK_OPTION) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		try { this.close(); }catch(Exception e) { System.err.println("Could not close socket! "+e.getClass().getSimpleName()+": "+e.getLocalizedMessage());}
		*/
	}
	
}
