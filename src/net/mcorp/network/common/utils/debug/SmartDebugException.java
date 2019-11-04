package net.mcorp.network.common.utils.debug;

public abstract class SmartDebugException extends Exception implements SmartDebugInterface{

	private static final long serialVersionUID = -6765431521723429858L;

	public SmartDebugException() {}
	
	public abstract String getLocalizedMessage();
	
	/**
	 * @apiNote This method has been overwritten by {@linkplain SmartDebugException}.
	 * It now returns the result of {@linkplain SmartDebugInterface#toString(String, String)}, using the parameters ( "", "\t" )
	 */
	public String toString() {
		return this.toString("", "\t");
	}
	
}
