package net.mcorp.networked.interfaces.server.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.util.List;

import net.mcorp.home.devices.Devices;
import net.mcorp.home.devices.networked.Camera;
import net.mcorp.home.devices.networked.Camera.Image.ImageFrame;
import net.mcorp.networked.common.connections.SocketConnection;
import net.mcorp.networked.interfaces.server.ServerHandler;

public class TestServerHandler extends ServerHandler {

	public final Devices devices = new Devices(new File("./devices"));
	public final Camera camera = new Camera(devices,"192.168.1.18",80);
	
	public static int timeout = 10000;
	
	public TestServerHandler() {
		Thread t = new Thread(camera);
		t.setName("Camera Thread");
		t.setDaemon(true);
		t.start();
	}
	
	@Override
	public void handleSocket(Socket socket) {
		SocketConnection client = new SocketConnection(socket);
		OutputStream out = null;
		InputStream in = null;
		BufferedReader br = null;
		try {
			out = client.getOutputStream();
			in = client.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			String url = null;
			try {
				long start_time = System.currentTimeMillis();
				while(!br.ready()) {
					if(System.currentTimeMillis() - start_time > timeout)
						throw new SocketTimeoutException("[TestServerHandler.handleSocket(Socket):TIMEOUT] Client timed out...");
				}
				String line = br.readLine();
				String[] temp = line.split(" ",3);
				if(temp.length < 3)
					throw new IOException("[TestServerHandler.handleSocket(Socket):HEAD_NOT_VALID] The first line of data from the client was invalid...");
				url = temp[1];
			}catch(Exception e) {
				if(e instanceof SocketTimeoutException) {
					//Let Client know of its failure...
				}
				throw e;
			}
			System.out.println(url);
			if(url != null) {
				if(url.equals("/cameras/1")) {
					try {
						ImageFrame frame = camera.img().lastFrame();
						if(frame == null) {
							out.write("Http/1.1 503 Service Unavailable\n\n<h1>Camera 1</h1><hr><p>Camera 1 is currently unavailable to the network.</p>".getBytes());
						}else {
							synchronized(frame) {
								int[] data = frame.data();
								out.write(("Http/1.1 200 OK\n"
										+ "Content-Type: "+frame.type()+"\n"
										+ "Content-Length: "+data.length+"\n"
										+ "Cache-Control: no-cache\n"
										+ "Date: "+frame.date()+"\n"
										+ "\n").getBytes());
								for(int b : data) {
									out.write(b);
								}
							}
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else
				if(url.equals("/")) {
					try {
						out.write("Http/1.1 200 OK\n\n".getBytes());
						List<String> lines = Files.readAllLines(new File("./server_files/index.html").toPath());
						for(String line : lines) {
							out.write((line).getBytes());
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
				}else {
					try {
						out.write("Http/1.1 404 Not Found\n\n".getBytes());
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
