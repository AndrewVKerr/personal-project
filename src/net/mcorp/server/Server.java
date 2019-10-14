package net.mcorp.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import net.mcorp.utils.Debugger;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.lockable.LockableObject;
import net.mcorp.server.resources.ResourceTree;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.server.resources.transferable.WebMedia;
import net.mcorp.server.resources.transferable.Webpage;

public class Server implements Runnable{

	public final ServerSocket server;
	
	public final ResourceTree fileTree;
	
	public final ExecutorService pool = Executors.newCachedThreadPool();
	
	public Server() throws IOException {
		this.server = new ServerSocket(2000);
		this.fileTree = new ResourceTree();
		
		File filesDir = new File("/home/andrew/Desktop/GIT/personal-project/server_files/");
		if(filesDir.exists() == false) {
			filesDir = new File("/home/akerr/GIT/personal-project/server_files/");
		}
		
		try {
			LockableObject.unlock(this.fileTree);
			Webpage test;
			ResourceUrl index;
			ResourceUrl directory;
			WebMedia video;
			WebMedia image;
			WebMedia audio;
			index = this.fileTree.root().createChild("index",test=new Webpage());
			directory = index.createChild("directory", null);
			directory.createChild("image", image = new WebMedia());
			directory.createChild("video", video = new WebMedia());
			directory.createChild("faded", audio = new WebMedia());
			image.update(new File(filesDir.getAbsolutePath()+"/image.jpg")); // https://www.pexels.com/photo/mountain-with-fog-2539409/
			video.update(new File(filesDir.getAbsolutePath()+"/video.mp4"));
			audio.update(new File(filesDir.getAbsolutePath()+"/faded.mp3"));
			test.file(new File(filesDir.getAbsolutePath()+"/index.html"));
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
