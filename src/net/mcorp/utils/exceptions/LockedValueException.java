package net.mcorp.utils.exceptions;

/**
 * This exception is thrown when a value being assigned has been locked.
 * @author Andrew Kerr
 */
public class LockedValueException extends Exception {

	private static final long serialVersionUID = 5818434280606888161L;

	/**
	 * Creates a new {@linkplain LockedValueException}.
	 * @param s - {@linkplain String} - The object that is locked.
	 */
	public LockedValueException(String s) {
		super("[LOCKED] The value for the object \""+s+"\" has been set into a pseudo-locked state. This prevents the editing of the value without first unlocking it.");
	}
	
}
