package net.mcorp.server.protocols.websocket;

import net.mcorp.utils.exceptions.ValueAlreadySetException;

public abstract class WebsocketHandler implements Runnable{

	public static enum WebsocketState{
		Opening,
		Open,
		Close,
		Closed,
		Error
	} 
	
	private WebsocketConnection connection;
	public synchronized final WebsocketConnection connection() { return this.connection; };
	public synchronized final void connection(WebsocketConnection connection) throws ValueAlreadySetException { 
		if(this.connection == null) 
			this.connection = connection; 
		else 
			throw new ValueAlreadySetException("WebsocketHandler.connection(WebsocketConnection)"); 
	};
	
	@Override
	public void run() {
		
	}
	
}
