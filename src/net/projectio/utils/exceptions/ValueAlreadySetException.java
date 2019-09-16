package net.projectio.utils.exceptions;

/**
 * This exception serves one purpose, when it is thrown you can assume that whatever value that you
 * were trying to change is in a pseudo-final stage, it can be edited directly but cannot be changed via 
 * the method that threw this exception or any methods that will throw this exception. Its purpose is to allow
 * the values to be set and set pseudo-permanently without forcing you to set it directly in the constructor.
 * @author ThePuppet
 */
public class ValueAlreadySetException extends Exception{

	private static final long serialVersionUID = 1840533114344578686L;

	public ValueAlreadySetException(String s) {
		super("[LOCKED] The value for the object \""+s+"\" has already been set to a non null value, the parent object wishes to make this change final and has thrown this error to prevent the changing of the value.");
	}
	
}
