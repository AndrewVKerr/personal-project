package net.mcorp.server.resources.transferable;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.HttpStatusCode;
import net.mcorp.server.protocols.http.statuscodes.HttpStatusCodes;

public class HttpsResource extends TransferableObject{

	@Override
	public void execute(Ticket ticket) throws Exception {
		HttpPacket packet = (HttpPacket) ticket.currentPacket();
		if(packet.StatusCode() != HttpStatusCodes.instance.getCode(101)) {
			HttpPacket response = (HttpPacket) ticket.getPacket(Http.protocol);
			response.setHeaderValue("Upgrade", "HTTPS");
			response.setHeaderValue("Connection", "Upgrade");
			response.StatusCode(new HttpStatusCode() {
				public int getCode() {
					return 426;
				}
				public String getText() {
					return "Upgrade Required";
				}
			});
			response.lock();
			response.writeToTicket();
		}
	}
	
}
