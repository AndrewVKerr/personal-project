package net.projectio.server.protocols.websocket;

public abstract class WebsocketHandler implements Runnable{

	private WebsocketConnection connection;
	public final WebsocketConnection connection() { return this.connection; };
	protected final void connection(WebsocketConnection connection) { this.connection = connection; };
	
}
