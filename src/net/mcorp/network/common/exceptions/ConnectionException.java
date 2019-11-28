package net.mcorp.network.common.exceptions;

import net.mcorp.common.utils.debug.SmartDebugException;

public class ConnectionException extends SmartDebugException{

	public static enum ConnectionFailState{
		INPUT_CLOSED,
		OUTPUT_CLOSED,
		SOCKET_CLOSED,
		CLIENT_RESET,
		SERVER_RESET,
		PROTOCOL_ERROR,
		RESTRICTED_IO
	}
	
	private static final long serialVersionUID = -4620329650419201945L;

	private ConnectionFailState state;
	private ProtocolException enclosed;
	private String caller;
	private String message;
	
	public ConnectionException(ProtocolException pe) {
		this.state = ConnectionFailState.PROTOCOL_ERROR;
		this.enclosed = pe;
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];//maybe this number needs to be corrected
		this.caller = e.getClassName()+"."+e.getMethodName();
		this.message = null;
	}
	
	public ConnectionException(ConnectionFailState state) {
		this.state = state;
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];//maybe this number needs to be corrected
		this.caller = e.getClassName()+"."+e.getMethodName();
		this.message = null;
	}
	
	public ConnectionException(ConnectionFailState state, String customMessage) {
		this.state = state;
		StackTraceElement e = Thread.currentThread().getStackTrace()[2];//maybe this number needs to be corrected
		this.caller = e.getClassName()+"."+e.getMethodName();
		this.message = null;
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
		+ "\n"+indent+indentBy+"caller = String["+this.caller+"],"
		+ "\n"+indent+indentBy+"message = String["+this.message+"],"
		+ "\n"+indent+indentBy+"enclosed = "+(this.enclosed != null ? this.enclosed.toString(indent+indentBy,indentBy) : null )+","
		+ "\n"+indent+indentBy+"state = "+this.state
		+ indent+"]";
	}

	@Override
	public String getLocalizedMessage() {
		if(this.state != ConnectionFailState.PROTOCOL_ERROR) {
			return "["+this.caller+":"+this.state+"] "+this.enclosed.getLocalizedMessage();
		}else {
			return "["+this.caller+":"+this.state+"] "+this.message;
		}
	}
	
}
