package net.mcorp.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.mcorp.utils.Debugger;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.lockable.LockableObject;
import net.mcorp.server.resources.ResourceTree;
import net.mcorp.server.resources.transferable.Webpage;

public class Server implements Runnable{

	public final ServerSocket server;
	
	public final ResourceTree fileTree;
	
	public final ExecutorService pool = Executors.newCachedThreadPool();
	
	public Server() throws IOException {
		this.server = new ServerSocket(2000);
		this.fileTree = new ResourceTree();
		
		try {
			LockableObject.unlock(this.fileTree);
			Webpage test;
			this.fileTree.root().createChild("index",test=new Webpage()).createChild("test", null).createChild("helloWorld", new Webpage());
			test.file(new File("/home/andrew/Desktop/GIT/personal-project/server_files/index.html"));
			if(!test.getFile().exists()) {
				test.file(new File("/home/akerr/GIT/personal-project/server_files/index.html"));
			}
		} catch (LockedValueException e) {
			e.printStackTrace();
		}finally {
			this.fileTree.lock();
		}
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
