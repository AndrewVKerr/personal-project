package net.projectio.server;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.nio.file.Files;

import net.projectio.server.protocols.Protocol;
import net.projectio.server.protocols.http.Http;
import net.projectio.server.protocols.http.HttpPacket;

public class Ticket implements Runnable {

	public final Socket socket;
	public final ProjectIOServer server;
	
	private Packet lastPacket;
	public final Packet lastPacket() { return this.lastPacket; };
	
	private Protocol<?> protocol;
	public final Protocol<?> protocol(){ return this.protocol; };
	
	
	public void write(byte[] data) throws IOException{
		if(socket.getOutputStream() == null || socket.isOutputShutdown())
			throw new IOException("Output Closed!");
		socket.getOutputStream().write(data);
	}
	
	public void write(String str) throws IOException{
		this.write(str.getBytes());
	}
	
	public void write(File file) throws IOException{
		this.write(Files.readAllBytes(file.toPath()));
	}
	
	public Packet readPacket(Class<? extends Packet> clazz) throws IOException {
		try {
			Constructor<?> constructor;
			try {
				constructor = clazz.getConstructor(new Class<?>[] {Ticket.class});
			}catch(NoSuchMethodException e1) {constructor = null;};
			if(constructor != null) {
				Packet p = (Packet) constructor.newInstance(this);
				this.lastPacket = p;
				return p;
			}
		}catch(Exception e) {
			if(e instanceof IOException) {
				throw ((IOException) e);
			}
			IOException io = new IOException("An Exception has occurred while attempting to generate a new Packet! See Suppressed!");
			io.addSuppressed(e);
			throw io;
		}
		return null;
	}
	
	public void close() throws IOException{
		if(this.socket.getOutputStream() != null && !this.socket.isOutputShutdown())
			this.socket.getOutputStream().flush();
		this.socket.close();
	}
	
	public Ticket(Socket socket, ProjectIOServer server) {
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		
		HttpPacket packet = Http.protocol.generateNewPacketObject(this);
		try {
			packet.readFromTicket();
			System.out.println(packet.Url());
			
			if(packet.Url().equals("/Websocket")) {
				
			}else {
				HttpPacket response = Http.protocol.generateNewPacketObject(this);
				response.Version("Http/1.1");
				response.StatusCode(200);
				response.setHeaderValue("Test", "Hello");
				response.Payload("Hello World!".getBytes());
				response.lock();
				response.writeToTicket();
			}
		} catch (Exception e) {
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

	public void flush() throws IOException {
		this.socket.getOutputStream().flush();
	}
	
}
