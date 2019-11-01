package net.mcorp.webInterface.exceptions;

public class RunnableException extends Exception{
	
	private static final long serialVersionUID = 6974200404240353546L;
	
	public RunnableException() {
		super("[UNKNOWN:UNKNOWN_REASON] A unknown exception occured whilst executing a unknown section of code!");
	}
	
	public RunnableException(String info) {
		super(info);
	}
	
}
