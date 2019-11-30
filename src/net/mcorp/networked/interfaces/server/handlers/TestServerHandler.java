package net.mcorp.networked.interfaces.server.handlers;

import java.io.OutputStream;
import java.net.Socket;

import net.mcorp.home.devices.networked.Camera;
import net.mcorp.networked.common.connections.SocketConnection;
import net.mcorp.networked.interfaces.server.ServerHandler;

public class TestServerHandler extends ServerHandler {

	public final Camera camera = new Camera("192.168.1.18",80);
	
	public TestServerHandler() {
		Thread t = new Thread(camera);
		t.setName("Camera Thread");
		t.setDaemon(true);
		t.start();
	}
	
	@Override
	public void handleSocket(Socket socket) {
		SocketConnection client = new SocketConnection(socket);
		try {
			OutputStream out = client.getOutputStream();
			int[] data = camera.data();
			out.write(("Http/1.1 200 OK\n"
					+ "Content-Type: "+camera.type()+"\n"
					+ "Content-Length: "+data.length+"\n"
					+ "Cache-Control: no-cache\n"
					+ "Date: "+camera.date()+"\n"
					+ "\n").getBytes());
			for(int b : data) {
				out.write(b);
			}
			client.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
