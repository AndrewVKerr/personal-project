package net.mcorp.server.resources.transferable;

import java.io.File;
import java.nio.file.Files;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.exceptions.UnsupportedProtocolException;

public class Webpage extends TransferableObject {

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
				packet.StatusCode(StandardHttpStatusCodes.Not_Found);
				packet.Payload("404 Not Found".getBytes());
			}else {
				try {
					packet.StatusCode(StandardHttpStatusCodes.Ok);
					packet.Payload(Files.readAllBytes(file.toPath()));
				}catch(Exception e) {
					packet.StatusCode(StandardHttpStatusCodes.Internal_Server_Error);
					packet.Payload(e.getLocalizedMessage().getBytes());
				}
			}
			packet.lock();
			packet.writeToTicket();
		}else {
			throw new UnsupportedProtocolException("Webpage.execute(Ticket)",ticket.protocol());
		}
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+"file = File[";
		str += "\n"+indent+indentBy+"name = String[\""+this.file.getName()+"\"],";
		str += "\n"+indent+indentBy+"path = String[\""+this.file.getPath()+"\"],";
		str += "\n"+indent+indentBy+"exists = Boolean["+this.file.exists()+"]";
		str += "\n"+indent+"]";
		str += "\n"+indent.substring(0,(indent.length() < indentBy.length() ? indent.length() : indent.length()-indentBy.length()))+"]";
		return str;
	}

}
