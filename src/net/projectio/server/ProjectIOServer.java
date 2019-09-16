package net.projectio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import net.projectio.server.packets.HttpPacket;
import net.projectio.utils.Debugger;
import net.projectio.utils.exceptions.TimedOutException;
import net.projectio.utils.resources.Resource;
import net.projectio.utils.resources.ResourceTree;

public class ProjectIOServer implements Runnable{

	public final ServerSocket server;
	
	public final ResourceTree fileTree;
	
	public final ExecutorService pool = Executors.newCachedThreadPool();
	
	public ProjectIOServer() throws IOException {
		this.server = new ServerSocket(80);
		this.fileTree = new ResourceTree();
	}

	@Override
	public void run() {
		while(this.server != null && this.server.isClosed() == false) {
			try {
				Socket socket = server.accept();
				pool.execute(new Ticket(socket,this));
			}catch(Exception e) {
				Debugger.printException(Debugger.DebugLevel.Warn,e);
			}
		}
	}
	
	public void close() throws IOException {
		this.server.close();
	}
	
}
