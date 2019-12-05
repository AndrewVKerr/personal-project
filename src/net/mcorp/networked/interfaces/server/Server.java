package net.mcorp.networked.interfaces.server;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;

import net.mcorp.home.devices.networked.NetworkedDevice;

/**
 * <h1>net.mcorp.networked.interfaces.server.Server&lt;ServerHandler_ extends {@linkplain ServerHandler}&gt;</h1>
 * <hr>
 * <p>
 * This class is used to create and maintain the main server thread.
 * </p>
 * <hr>
 * @author Andrew Kerr
 * @param <ServerHandler_> - {@linkplain ServerHandler} - The class of the handler for this server.
 */
public class Server<ServerHandler_ extends ServerHandler> implements Runnable{
	
	private final ServerHandler_ handler;
	private final ServerSocket serverSocket;
	
	public final ServerController<ServerHandler_> controller = new ServerController<ServerHandler_>(this);
	
	private static File workingDirectory = null;
	public static final File workingDirectory() { findWorkingDirectory(); return workingDirectory; };
	public static final String workingDirectoryPath() { findWorkingDirectory(); return (workingDirectory == null ? "." : workingDirectory.getAbsolutePath()); };
	public static final void findWorkingDirectory() {
		if(workingDirectory != null)
			return;
		String path = NetworkedDevice.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			String decodedPath = URLDecoder.decode(path, "UTF-8");
			String[] pathSegs = decodedPath.split("/");
			String finalPath = "/";
			for(String seg : pathSegs) {
				if(!seg.endsWith(".jar") && seg.length() > 0)
					finalPath += seg+"/";
			}
			File file = new File(finalPath);
			if(file.exists())
				workingDirectory = file;
			else
				System.err.println("[Server.findWorkingDirectory():FILE_DOESNT_EXIST] "+finalPath+"/enableNetworkedDevices file does not exist!");
		} catch (UnsupportedEncodingException e) { System.err.println("[Server.findWorkingDirectory():CANT_FIND_DIRECTORY] Could not attempt to enable through file."); e.printStackTrace();}
	}
	
	private boolean shutdown = false;
	private boolean running = false;
	public synchronized boolean isRunning() { return this.running; };
	public synchronized void shutdown() { this.shutdown = true; };
	public synchronized boolean willShutdown() { return this.shutdown; };
	public void awaitShutdown() {
		while(isRunning()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Server(ServerHandler_ handler, int port) throws IOException {
		findWorkingDirectory();
		this.handler = handler;
		this.serverSocket = new ServerSocket(port);
		this.serverSocket.setSoTimeout(1000);
	}

	@Override
	public void run() {
		synchronized(this) { running = true; }
		while(!willShutdown()) {
			try {
				synchronized(this) {
					Socket socket = this.serverSocket.accept();
					handler.handleSocket(socket);
				}
			}catch(Exception e) {
				if( !(e instanceof SocketTimeoutException) ) {
					e.printStackTrace();
				}
			}
		}
		synchronized(this) { running = false; }
	}
	
	public void close() throws IOException {
		if(this.isRunning()) {
			throw new RuntimeException("[Server.run():SERVER_STILL_RUNNING] The server is still running right now...");
		}
		this.serverSocket.close();
	}
	
}
