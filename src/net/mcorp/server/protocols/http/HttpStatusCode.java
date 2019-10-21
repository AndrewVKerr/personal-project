package net.mcorp.server.protocols.http;

/**
 * This interface was created so that a programmer could define custom codes if they so wished while allowing the custom
 * code classes to be passed to the http packet as a valid response.
 * @author Andrew Kerr
 *
 */
public interface HttpStatusCode {
	
	public default int getCode() {
		return 500;
	}
	
	public default String getText() {
		return "NOT_DEFINED";
	}
	
}
