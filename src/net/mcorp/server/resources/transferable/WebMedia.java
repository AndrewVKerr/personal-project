package net.mcorp.server.resources.transferable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import net.mcorp.network.common.exceptions.LockedValueException;
import net.mcorp.network.common.exceptions.UnsupportedProtocolException;
import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.server.resources.MIMEType;

public class WebMedia extends TransferableObject {

	private byte[] internal_data;
	
	private MIMEType mimeType = MIMEType.NONE;
	
	public void update(BufferedImage bimg) throws LockedValueException {
		this.isLocked("Webimage.update(BufferedImage)");
		try{
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(bimg, "jpg", baos );
			internal_data=baos.toByteArray();
			mimeType = MIMEType.JPG;
		}catch(IOException ie){
			internal_data=null;
			this.mimeType = MIMEType.NONE;
		}
	}
	
	public void update(File file) throws LockedValueException {
		this.isLocked("Webimage.update(File)");
		try{
			String name = new String(file.getName());
			if(name.indexOf('.') == -1) {
				throw new Exception("Unable to determine MIMEType from filename. Please make sure that the filename has a valid file extension. ["+name+"]");
			}
			String extension = name.substring(name.indexOf('.')+1);
			this.internal_data = Files.readAllBytes(file.toPath());
			this.mimeType = MIMEType.getType(extension);
		}catch(Exception ie){
			ie.printStackTrace();
			internal_data=null;
			this.mimeType = MIMEType.NONE;
		}
	}
	
	@Override
	public void execute(Ticket ticket) throws Exception {
		if(ticket.protocol() == Http.protocol) {
			HttpPacket packet = Http.protocol.generateNewPacketObject(ticket);
			packet.Version("Http/1.1");
			if(this.internal_data == null) {
				packet.StatusCode(StandardHttpStatusCodes.Not_Found);
				packet.Payload("404 Not Found".getBytes());
			}else {
				try {
					if(this.mimeType == MIMEType.NONE) {
						packet.StatusCode(StandardHttpStatusCodes.No_Content);
					}else {
						packet.StatusCode(StandardHttpStatusCodes.Ok);
						packet.setHeaderValue("Content-Type", this.mimeType.toString());
						packet.Payload(internal_data);
					}
				}catch(Exception e) {
					packet.StatusCode(StandardHttpStatusCodes.Internal_Server_Error);
					packet.Payload(e.getLocalizedMessage().getBytes());
				}
			}
			packet.lock();
			packet.writeToTicket();
		}else {
			throw new UnsupportedProtocolException("Webimage.execute(Ticket)",ticket.protocol());
		}
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+"mimeType = MimeType[\""+this.mimeType+"\"],";
		str += "\n"+indent+"internalData = byte["+internal_data.length+"]{...}";
		str += "\n"+indent.substring(0,(indent.length() < indentBy.length() ? indent.length() : indent.length()-indentBy.length()))+"]";
		return str;
	}

}
