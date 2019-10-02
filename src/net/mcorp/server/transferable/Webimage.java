package net.mcorp.server.transferable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.exceptions.UnsupportedProtocolException;

public class Webimage extends TransferableObject {

	private byte[] internal_data;
	
	private BufferedImage bimg;
	public BufferedImage getImage() {
		return bimg;
	}
	
	public void setImage(BufferedImage bimg) throws LockedValueException {
		this.isLocked("Webimage.setImage(BufferedImage)");
		this.bimg = bimg;
		update();
	}
	
	public void update() {
		try{
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(bimg, "jpg", baos );
			internal_data=baos.toByteArray();
		}catch(IOException ie){
			internal_data=null;
		}
	}
	
	@Override
	public void execute(Ticket ticket) throws Exception {
		if(ticket.protocol() == Http.protocol) {
			HttpPacket packet = Http.protocol.generateNewPacketObject(ticket);
			packet.Version("Http/1.1");
			if(this.internal_data == null) {
				packet.StatusCode(404);
				packet.StatusText("Not Found");
				packet.Payload("404 Not Found".getBytes());
			}else {
				try {
					packet.StatusCode(200);
					packet.StatusText("OK");
					packet.Payload(internal_data);
				}catch(Exception e) {
					packet.StatusCode(500);
					packet.StatusText("Internal Server Exception!");
					packet.Payload(e.getLocalizedMessage().getBytes());
				}
			}
			packet.lock();
			packet.writeToTicket();
		}else {
			throw new UnsupportedProtocolException("Webimage.execute(Ticket)",ticket.protocol());
		}
	}

}
