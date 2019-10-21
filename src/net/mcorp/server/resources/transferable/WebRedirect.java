package net.mcorp.server.resources.transferable;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.StandardHttpStatusCodes;
import net.mcorp.utils.exceptions.UnsupportedProtocolException;

public class WebRedirect extends TransferableObject {

	private String redirectTo;
	public synchronized String redirectTo() { return this.redirectTo; };
	public synchronized void redirectTo(String redirectTo) { this.redirectTo = redirectTo; };
	
	public WebRedirect(String redirectTo) {
		this.redirectTo = redirectTo;
	}
	
	@Override
	public void execute(Ticket ticket) throws Exception {
		if(ticket.protocol() == Http.protocol) {
			HttpPacket packet = Http.protocol.generateNewPacketObject(ticket);
			packet.Version("Http/1.1");
			packet.StatusCode(StandardHttpStatusCodes.Temporary_Redirect);
			packet.setHeaderValue("Location", redirectTo);
			packet.lock();
			packet.writeToTicket();
		}else {
			throw new UnsupportedProtocolException("WebOverview.execute(Ticket)",ticket.protocol());
		}
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+"redirectTo = String[\""+this.redirectTo+"\"]";
		str += "\n"+indent.substring(0,(indent.length() < indentBy.length() ? indent.length() : indent.length()-indentBy.length()))+"]";
		return str;
	}

}
