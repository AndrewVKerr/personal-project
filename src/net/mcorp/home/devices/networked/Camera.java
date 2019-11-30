package net.mcorp.home.devices.networked;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import net.mcorp.networked.common.connections.SocketConnection;

public class Camera extends NetworkedDevice{
	
	private int[] data;
	private String type;
	private String date;
	
	public Camera(String host, int port){
		super(host,port);
	}

	public synchronized int[] data(){
		return this.data;
	}
	
	public synchronized String type() {
		return this.type;
	}
	
	public synchronized String date() {
		return this.date;
	}
	
	@Override
	public void runCall() throws Exception {
		SocketConnection socket = null;
		if(socket == null) {
			@SuppressWarnings("resource")
			Socket sock = new Socket(this.host, this.port);
			long tout = System.currentTimeMillis();
			while(sock.isConnected() == false) { //Await connection
				if(System.currentTimeMillis() - tout > 1000)
					throw new SocketException("[Camera.runCall():COULD_NOT_CONNECT] The camera object could not connect to the pysical camera in time.");
			}
			socket = new SocketConnection(sock);
		}
		OutputStream out = socket.getOutputStream();
		out.write("GET /jpgmulreq/1/image.jpg?key=1575070468339&lq=13 Http/1.1\n".getBytes());
		out.write("Cookie: dev=159; BSDdev=0; ICEtype=0; SU=admin; jsLivePresetNo=0; jsLiveAutopanNo=0; jslivePatternNum=0; jsliveTourNum=0; playMode=NaN; player=hkvisionMjpeg\n\n".getBytes());
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = br.readLine();
		if(line.contains("200")) {
			synchronized(this) {
				while(true) {
					line = br.readLine();
					if(line.equals(""))
						break;
					if(line.startsWith("Content-Length: ")) {
						String length = line.substring("Content-Length: ".length());
						int len = Integer.parseInt(length);
						this.data = new int[len];
					}
					if(line.startsWith("Content-Type: ")) {
						this.type = line.substring("Content-Type: ".length());
					}
					if(line.startsWith("Date:")) {
						this.date = line.substring("Date: ".length());
					}
				}
				for(int i = 0; i < this.data.length; i++) {
					this.data[i] = in.read();
				}
			}
			try {
				socket.close();
			}catch(Exception e) {}
		}else {
			String packet = "";
			while(true) {
				packet += "\t"+line+"\n";
				line = br.readLine();
				if(line.equals(""))
					break;
			}
			try {
				socket.close();
			}catch(Exception e) {}
			throw new IOException("[Camera.runCall():HTTP_ERROR] An unexpected http status code was given...\n{"+packet+"}");
		}
		Thread.sleep(1000);
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"host = String["+this.host+"],"
				+ "\n"+indent+indentBy+"port = Integer["+this.port+"]"
				+ "\n"+indent+"]";
	}

}
