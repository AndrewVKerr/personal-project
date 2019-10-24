package net.mcorp.server;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import net.mcorp.server.protocols.Packet;
import net.mcorp.server.protocols.Protocol;

public class Ticket {

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
			if(!p.validateTicket())
				p.ticket(this);
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

	/*@Override
	public void run() {
		
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
		
	}*/
	
}
