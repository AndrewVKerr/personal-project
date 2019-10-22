package net.mcorp.server.resources.transferable;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.http.Http;
import net.mcorp.server.protocols.http.HttpPacket;
import net.mcorp.server.protocols.http.statuscodes.HttpStatusCodes;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.utils.exceptions.UnsupportedProtocolException;

public class WebOverview extends TransferableObject{
	
	public String loop(ResourceUrl cUrl) {
		String page = "";
		for(ResourceUrl url : cUrl.getChildren()) {
			page += "\t\t\t<tr>\n";
			String sUrl = url.url();
			page += "\t\t\t\t<td><a href=\""+sUrl+"\">"+sUrl+"</a></td>\n";
			page += "\t\t\t\t<td>"+(url.resource() == null ? "NULL_VALUE" : url.resource().toString("\t\t\t\t\t","\t"))+"</td>\n";
			page += "\t\t\t</tr>\n";
			page += loop(url);
		}
		return page;
	}
	
	@Override
	public void execute(Ticket ticket) throws Exception {
		if(ticket.protocol() == Http.protocol) {
			ResourceUrl rUrl = this.getUrl().tree().root();
			HttpPacket packet = Http.protocol.generateNewPacketObject(ticket);
			packet.Version("Http/1.1");
			if(rUrl != null) {
				String page = "<html>\n";
				page += "\t<head>\n";
				page += "\t\t<title>Website Overview</title>\n";
				page += "\t\t<style>\n";
				page += "\t\t\ttable,td,th{\n";
				page += "\t\t\t\tborder:1px #000 solid\n";
				page += "\t\t\t}\n";
				page += "\t\t\ttable{\n";
				page += "\t\t\t\twidth:100%\n";
				page += "\t\t\t}\n";
				page += "\t\t</style>\n";
				page += "\t</head>\n";
				page += "\t<body>\n";
				page += "\t\t<h1>Website Overview</h1>\n";
				page += "\t\t<hr>\n";
				page += "\t\t<p>This webpage is used to show an overview of the website, this should not be left in after development is finished!</p>\n";
				page += "\t\t<hr>\n";
				page += "\t\t<table>\n";
				page += "\t\t\t<tr><th>Url</th><th>TransferableObject</th></tr>\n";
				page += loop(rUrl);
				page += "\t\t</table>";
				page += "\t</body>";
				page += "</html>";
				packet.StatusCode(HttpStatusCodes.instance.getCode(200));
				packet.Payload(page.getBytes());
			}else {
				packet.StatusCode(StandardHttpStatusCodes.Ok);
			}
			packet.lock();
			packet.writeToTicket();
		}else {
			throw new UnsupportedProtocolException("WebOverview.execute(Ticket)",ticket.protocol());
		}
	}

	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+"devNote = String[";
		str += "\n"+indent+indentBy+"\"<span style='color:red'>NOTE: This object should be removed in the final build of any application.</span>\"";
		str += "\n"+indent+"]";
		str += "\n"+indent.substring(0,(indent.length() < indentBy.length() ? indent.length() : indent.length()-indentBy.length()))+"]";
		return str;
	}
	
}
