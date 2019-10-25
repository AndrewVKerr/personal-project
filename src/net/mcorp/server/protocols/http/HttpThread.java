package net.mcorp.server.protocols.http;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.HandlerThread;
import net.mcorp.server.protocols.http.statuscodes.StandardHttpStatusCodes;
import net.mcorp.server.resources.ResourceTree.ResourceUrl;
import net.mcorp.server.resources.transferable.TransferableObject;
import net.mcorp.utils.exceptions.TimedOutException;

public class HttpThread extends HandlerThread<Http,HttpPacket>{

	protected HttpThread(Ticket ticket) {
		super(Http.protocol, ticket);
	}

	@Override
	public void execute(HttpPacket packet) {
		try {
			ticket().autoClose(true);
			packet.readFromTicket();
			System.out.println(packet.Url());
			ResourceUrl resourceUrl = ticket().server.fileTree.getResource(packet.Url(), "/");
			if(resourceUrl != null) {
				TransferableObject transferableObj = resourceUrl.resource();
				if(transferableObj != null) {
					transferableObj.execute(ticket());
				}else {
					HttpPacket response = (HttpPacket) ticket().getPacket(Http.protocol);
					response.Version("Http/1.1");
					response.StatusCode(StandardHttpStatusCodes.No_Content);
					response.setHeaderValue("type", "plain/text");
					response.Payload("No Content".getBytes());
					response.lock();
					response.writeToTicket();
				}
			}else {
				HttpPacket response = (HttpPacket) ticket().getPacket(Http.protocol);
				response.Version("Http/1.1");
				response.StatusCode(StandardHttpStatusCodes.Not_Found);
				response.setHeaderValue("type", "plain/text");
				response.lock();
				response.writeToTicket();
			}
		} catch (Exception e) {
			if(e instanceof TimedOutException) {
				try {
					HttpPacket response = (HttpPacket) ticket().getPacket(Http.protocol);
					response.Version("Http/1.1");
					response.StatusCode(StandardHttpStatusCodes.Request_Timeout);
					response.setHeaderValue("type", "image/png");
					response.lock();
					response.writeToTicket();
				}catch(Exception e1) {}
			}else {
				if(e instanceof HttpException) {
					try {
						HttpException he = (HttpException) e;
						HttpPacket response = (HttpPacket) ticket().getPacket(Http.protocol);
						response.Version("Http/1.1");
						response.StatusCode(he.code());
						response.setHeaderValue("type", "plain/text");
						response.Payload(he.getMessage().getBytes());
						response.lock();
						response.writeToTicket();
					}catch(Exception e1) {}
				}
			}
			e.printStackTrace();
		}
	}
	
	
}

/*if(packet.Url().equals("/Websocket")) {
WebsocketConnection web = Websocket.protocol.generateNewPacketObject(this);
web.switchToWebsocket(Http.class);
this.lastPacket = web;
System.out.println("Reading!");
while(true) {
	try {
		System.out.println("1");
		WebsocketFrame frame = web.getNextFrame();
		if(frame.opcode() == (byte)8)
			break;
		System.out.println("2");
		System.out.println("Frame Len: "+frame.length());
		System.out.println("3");
		Payload payload = frame.payload();
		System.out.println("Payload Len: "+payload.size());
		String pay_load = "";
		for(int i = 0; i < payload.size(); i++) {
			PayloadItem pi = payload.read();
			System.out.println(pi);
			pay_load += (char)pi.getStored();
		}
		System.out.println();
		System.out.println("4");
		WebsocketFrame f = web.createResponse();
		f.opcode((byte)1);
		f.payload(pay_load);
		f.lock();
		f.sendFrame(false);
	}catch(Exception e) {
		e.printStackTrace();
		if(e instanceof NumberFormatException)
			break;
	}
}
System.out.println("Closing Websocket Connection!");

}else {
if(packet.Url().equals("/favicon.ico")) {
	HttpPacket response = Http.protocol.generateNewPacketObject(this);
	response.Version("Http/1.1");
	response.StatusCode(404);
	response.setHeaderValue("type", "image/png");
	response.lock();
	response.writeToTicket();
}else {
	System.out.println("Running!");
}
}*/
