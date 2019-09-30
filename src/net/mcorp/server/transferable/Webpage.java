package net.mcorp.server.transferable;

import java.io.File;
import java.nio.file.Files;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.HttpPacket.Methods;
import net.mcorp.utils.exceptions.LockedValueException;

public class Webpage extends WebObject {

	private File file;
	public File getFile() { return this.file; };
	public void file(File file) throws LockedValueException { this.isLocked("Webpage.file(File)"); this.file = file; };
	
	public Webpage() {
		this.file = null; //TODO: Add 404 Not Found webpage handler!
	}
	
	public Webpage(File file) {
		this.file = file;
	}
	
	@Override
	public void execute(Ticket ticket) throws Exception {
		if(ticket.protocol() == Http.protocol) {
			HttpPacket packet = Http.protocol.generateNewPacketObject(ticket);
			packet.Version("Http/1.1");
			if(this.file == null) {
				packet.StatusCode(404);
				packet.StatusText("Not Found");
				packet.Payload("404 Not Found".getBytes());
			}else {
				try {
					packet.StatusCode(200);
					packet.StatusText("OK");
					packet.Payload(Files.readAllBytes(file.toPath()));
				}catch(Exception e) {
					packet.StatusCode(500);
					packet.StatusText("Internal Server Exception!");
					packet.Payload(e.getLocalizedMessage().getBytes());
				}
			}
			packet.lock();
			packet.writeToTicket();
		}else {
			System.out.println("PROTOCOL NOT SUPPORTED");
		}
	}

}
