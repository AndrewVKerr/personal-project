package net.mcorp.server;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JTextField;

import net.mcorp.utils.lockable.LockableObject;
import net.mcorp.network.common.exceptions.LockedValueException;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.https.Https;
import net.mcorp.server.protocols.websocket.WebsocketFrame;
import net.mcorp.server.protocols.websocket.WebsocketFrame.Payload;
import net.mcorp.server.protocols.websocket.WebsocketFrame.Payload.PayloadItem;
import net.mcorp.server.resources.ResourceTree;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.server.resources.transferable.HttpsResource;
import net.mcorp.server.resources.transferable.WebMedia;
import net.mcorp.server.resources.transferable.WebOverview;
import net.mcorp.server.resources.transferable.WebRedirect;
import net.mcorp.server.resources.transferable.Webpage;
import net.mcorp.server.resources.transferable.WebsocketPool;

public class Server implements Runnable{

	public final ServerSocket server;
	public final ServerInterface serverInterface = new ServerInterface();
	
	public final ResourceTree fileTree;
	
	public final ExecutorService pool = Executors.newCachedThreadPool();
	
	public final ServerUI ui = new ServerUI(this);
	
	public Server() throws IOException {
		this.server = new ServerSocket(2000);
		this.fileTree = new ResourceTree();
		
		File filesDir = new File("/home/andrew/Desktop/GIT/personal-project/server_files/");
		if(filesDir.exists() == false) {
			filesDir = new File("/home/akerr/GIT/personal-project/server_files/");
			if(filesDir.exists() == false) {
				filesDir = new File("C:/Users/kerr3/Desktop/network/personal-project/server_files/");
			}
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
			directory = index.createChild("directory", new WebOverview());
			directory.createChild("image", image = new WebMedia());
			directory.createChild("video", video = new WebMedia());
			directory.createChild("faded", audio = new WebMedia());
			index.createChild("github", new WebRedirect("https://github.com/AndrewVKerr/personal-project"));
			index.createChild("Websocket", new WebsocketPool() {

				@Override
				public void onDataRecieved(WebsocketFrame nextFrame) {
					Payload payload = nextFrame.payload();
					PayloadItem pi = payload.read();
					while(pi.hasNext() != false) {
						System.out.print((char)pi.getStored());
						pi = pi.getNext();
					}
					System.out.println((char)pi.getStored());
				}
				
			});
			index.createChild("https", new HttpsResource());
			image.update(new File(filesDir.getAbsolutePath()+"/image.jpg")); // https://www.pexels.com/photo/mountain-with-fog-2539409/
			video.update(new File(filesDir.getAbsolutePath()+"/video.mp4"));
			audio.update(new File(filesDir.getAbsolutePath()+"/faded.mp3"));
			test.file(new File(filesDir.getAbsolutePath()+"/index.html"));
			this.fileTree.landing(index);
		} catch (LockedValueException e) {
			e.printStackTrace();
		}finally {
			this.fileTree.lock();
		}
		
		JTextField tbox = new JTextField();
		tbox.setSize(100, 30);
		tbox.setLocation(10, 10);
		tbox.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						ResourceUrl url = fileTree.getResource("/index/Websocket", "/");
						if(url != null && url.resource() != null) {
							if(url.resource() instanceof WebsocketPool) {
								WebsocketPool pool = (WebsocketPool) url.resource();
								pool.sendMassData(tbox.getText());
								tbox.setText("");
							}else {
								System.err.println("Resource not of right type!");
							}
						}else {
							System.err.println("Failed to find resource!");
						}
					} catch (LockedValueException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		ui.addComponent(tbox);
		tbox.setVisible(true);
	}

	@Override
	public void run() {
		while(this.server != null && this.server.isClosed() == false) {
			try {
				Socket socket = server.accept();
				pool.execute(Http.handler.generateThread(new Ticket(socket,this)));
			}catch(Exception e) {
			}
		}
	}
	
	public void close() throws IOException {
		this.server.close();
	}
	
}
