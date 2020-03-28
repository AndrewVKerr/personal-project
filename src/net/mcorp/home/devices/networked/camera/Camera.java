package net.mcorp.home.devices.networked.camera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.UUID;

import net.mcorp.home.devices.Devices;
import net.mcorp.home.devices.networked.NetworkedDevice;
import net.mcorp.networked.common.connections.SocketConnection;

public class Camera extends NetworkedDevice{
	
	public final File ipFolder;
	public final File deviceInfoFolder;
	public final File imagesFolder;
	
	public final UUID uuid = UUID.randomUUID();
	
	public static class Image {
		
		public static class ImageFrame{
			
			private int[] data;
			private String type;
			private String date;
			
			public final int day;
			public final int hour;
			public final int minute;
			public final int second;
			
			public final Image img;
			
			private ImageFrame(Image img) {
				this.img = img;
				Calendar cal = Calendar.getInstance();
				this.day = cal.get(Calendar.DAY_OF_MONTH);
				this.hour = cal.get(Calendar.HOUR_OF_DAY);
				this.minute = cal.get(Calendar.MINUTE);
				this.second = cal.get(Calendar.SECOND);
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

			protected synchronized void save() throws IOException {
				File hourF = new File(this.img.camera.imagesFolder.getAbsolutePath()+"/"+this.hour);
				File minuteF = new File(hourF.getAbsolutePath()+"/"+this.minute);
				if(minuteF.exists() == false)
					if(minuteF.mkdirs() == false)
						throw new RuntimeException("[ImageFrame.save():FAILED_FOLDER_MAKE] Failed to make either the hourFolder or the minuteFolder...");
				File file = new File(minuteF.getAbsolutePath()+"/"+this.second+".jpg");
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				for(int i : this.data) {
					fos.write(i);
				}
				fos.close();
			}
			
		}
		
		public final Camera camera;
		
		public Image(Camera camera) {
			this.camera = camera;
		}
		
		private long lastSave = System.currentTimeMillis();
		
		private ImageFrame lastFrame;
		public synchronized ImageFrame lastFrame() { return this.lastFrame; };
		public synchronized void nextFrame(InputStream in) throws Exception{
			ImageFrame frame = new ImageFrame(this);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			long tout = System.currentTimeMillis();
			while(!br.ready()) {
				if(System.currentTimeMillis()-tout > 1000)
					throw new SocketException("[Camera.Image.nextFrame():NO_RESPONSE] Socket never responded to our request...");
			}
			String line = br.readLine();
			if(line.contains("200")) {
				synchronized(frame) {
					while(true) {
						line = br.readLine();
						if(line.equals(""))
							break;
						if(line.startsWith("Content-Length: ")) {
							String length = line.substring("Content-Length: ".length());
							int len = Integer.parseInt(length);
							frame.data = new int[len];
						}
						if(line.startsWith("Content-Type: ")) {
							frame.type = line.substring("Content-Type: ".length());
						}
						if(line.startsWith("Date:")) {
							frame.date = line.substring("Date: ".length());
						}
					}
					for(int i = 0; i < frame.data.length; i++) {
						frame.data[i] = in.read();
					}
					if(frame.data[0] != 0xff || frame.data[1] != 0xD8 || frame.data[2] != 0xff) {
						throw new IOException("[Image.nextFrame(InputStream):JPG_IMG_CORRUPTED] This frame was corrupted, skip it...");
					}
				}
			}else {
				String packet = "";
				while(true) {
					packet += "\t"+line+"\n";
					line = br.readLine();
					if(line.equals(""))
						break;
				}
				throw new IOException("[Camera.runCall():HTTP_ERROR] An unexpected http status code was given...\n{"+packet+"}");
			}
			if(System.currentTimeMillis() - lastSave >= 5000) {
				frame.save();
				lastSave = System.currentTimeMillis();
			}
			this.lastFrame = frame;
		}
		
	}
	
	private Image img = new Image(this);
	
	public Camera(Devices devices, String host, int port){
		super(devices,host,port);
		this.ipFolder = new File(devices.folder.getAbsolutePath()+"/"+host+"/");
		if(this.ipFolder.exists() == false)
			if(this.ipFolder.mkdir() == false)
				throw new RuntimeException("[Camera(Devices,String,int):IPFOLDER_NOT_CREATEABLE] Could not create an ipFolder...");
		this.deviceInfoFolder = new File(ipFolder.getAbsolutePath()+"/info/");
		if(this.deviceInfoFolder.exists() == false)
			if(this.deviceInfoFolder.mkdir() == false)
				throw new RuntimeException("[Camera(Devices,String,int):INFO_NOT_CREATEABLE] Could not create an deviceInfoFolder...");
		this.imagesFolder = new File(ipFolder.getAbsolutePath()+"/images/");
		if(this.imagesFolder.exists() == false)
			if(this.imagesFolder.mkdir() == false)
				throw new RuntimeException("[Camera(Devices,String,int):IMAGES_NOT_CREATEABLE] Could not create an imagesFolder...");
	}
	
	public final Image img() {
		synchronized(img) {
			return img;
		}
	}
	
	private String key = "";
	private long lq = 0;
	
	public void setupCall() throws Exception {
		File authFile = new File(this.deviceInfoFolder.getAbsolutePath()+"/auth.txt");
		if(authFile.exists()) {
			SocketConnection socket = null;
			@SuppressWarnings("resource")
			Socket sock = new Socket(this.host, this.port);
			long tout = System.currentTimeMillis();
			while(sock.isConnected() == false) { //Await connection
				if(System.currentTimeMillis() - tout > 1000)
					throw new SocketException("[Camera.runCall():COULD_NOT_CONNECT] The camera object could not connect to the pysical camera in time.");
			}
			socket = new SocketConnection(sock);
			OutputStream out = socket.getOutputStream();
			out.write("GET / Http/1.1\n".getBytes());
			String auth = Files.readString(authFile.toPath());
			out.write(("Authorization: Basic "+auth+"\n\n").getBytes());
			
			out.flush();
			InputStream in = socket.getInputStream();
			tout = System.currentTimeMillis();
			while(in.available() <= 0) {
				if(System.currentTimeMillis() - tout > 1000)
					throw new SocketException("[Camera.runCall():AUTH_TIMEOUT] The camera object could not connect to the pysical camera in time.");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String head = br.readLine();
			String[] segs = head.split(" ",3);
			if(segs.length < 3)
				throw new SocketException("[Camera.runCall():MALFORMED_RESPONSE] The camera object could not connect to the pysical camera in time.");
			key = "key="+head.substring(head.lastIndexOf("id="));
		}else {
			throw new SocketException("[Camera.setupCall():NO_AUTH_FILE] There is no auth file so this camera cannot be enabled.");
		}
	}
	
	@Override
	public void runCall() throws Exception {
		SocketConnection socket = null;
		@SuppressWarnings("resource")
		Socket sock = new Socket(this.host, this.port);
		long tout = System.currentTimeMillis();
		while(sock.isConnected() == false) { //Await connection
			if(System.currentTimeMillis() - tout > 1000)
				throw new SocketException("[Camera.runCall():COULD_NOT_CONNECT] The camera object could not connect to the pysical camera in time.");
		}
		socket = new SocketConnection(sock);
		OutputStream out = socket.getOutputStream();
		out.write(("GET /jpgmulreq/1/image.jpg?key="+key+"&lq="+lq+" Http/1.1\n").getBytes());
		lq++;
		if(lq >= 9999L)
			lq = 0;
		out.write("Cookie: dev=159; BSDdev=0; ICEtype=0; SU=admin; jsLivePresetNo=0; jsLiveAutopanNo=0; jslivePatternNum=0; jsliveTourNum=0; playMode=NaN; player=hkvisionMjpeg\n\n".getBytes());
		try{
			img.nextFrame(socket.getInputStream());
		}catch(Exception e) {
			try {
				socket.close();
			}catch(Exception e1) {}
			throw e;
		}
		try {
			socket.close();
		}catch(Exception e1) {}
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"host = String["+this.host+"],"
				+ "\n"+indent+indentBy+"port = Integer["+this.port+"]"
				+ "\n"+indent+"]";
	}

}
