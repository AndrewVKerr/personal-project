package net.mcorp.server.protocols.websocket;

import java.net.SocketException;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.HandlerThread;
import net.mcorp.server.resources.transferable.WebsocketPool;
import net.mcorp.utils.exceptions.ValueAlreadySetException;

public class WebsocketThread extends HandlerThread<Websocket, WebsocketConnection>{

	public WebsocketThread(Ticket ticket) {
		super(Websocket.protocol, ticket);
	}
	
	public static enum WebsocketState{
		Opening,
		Open,
		Close,
		Closed,
		Error
	} 
	
	private WebsocketPool resource;
	public void resource(WebsocketPool resource) throws ValueAlreadySetException {
		if(this.resource != null)
			throw new ValueAlreadySetException("WebsocketHandler.resource(WebsocketConnection)"); 
		this.resource = resource;
	};
	
	private WebsocketConnection connection;
	public synchronized final WebsocketConnection connection() { return this.connection; };
	public synchronized final void connection(WebsocketConnection connection) throws ValueAlreadySetException { 
		if(this.connection == null) 
			this.connection = connection; 
		else 
			throw new ValueAlreadySetException("WebsocketHandler.connection(WebsocketConnection)"); 
	}
	
	private Exception error;
	public Exception error() { return this.error; };
	public void error(Exception e) {
		state = WebsocketState.Error;
		error = e;
	}
	
	private WebsocketState state = WebsocketState.Opening;
	public WebsocketState state() { return this.state; };
	
	@Override
	public void execute(WebsocketConnection packet) {
		this.connection = packet;
		try {
			state = WebsocketState.Open;
			while(state == WebsocketState.Open) {
				resource.onDataRecieved(packet.getNextFrame());
			}
			state = WebsocketState.Close;
		}catch(NumberFormatException nfe) {
			state = WebsocketState.Close;
			error = nfe;
		}catch(Exception e) {
			state = WebsocketState.Error;
			error = e;
		}
		if(!packet.ticket().socket.isClosed()) {
			if(state == WebsocketState.Close) {
				try {
					WebsocketFrame frame = packet.createResponse();
					frame.opcode((byte)8);
					frame.fin(true);
					frame.mask(false);
					frame.lock();
					frame.sendFrame(false);
				}catch(Exception e) {
					if(e instanceof SocketException) {
						System.err.println("Websocket client closed socket before close frame could be sent... ("+e.getLocalizedMessage()+")");
					}else {
						System.err.println("Failed to tell client about closing... Forcing the connection close...");
						e.printStackTrace();
					}
				}
			}else {
				if(error != null)
					error.printStackTrace();
				else
					System.err.println("An untracked exception caused a websocket connection to close!");
			}
			try {
				packet.ticket().close();
			}catch(Exception e){}
		}
		state = WebsocketState.Closed;
	}
	
}
