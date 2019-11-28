package net.mcorp.common.utils.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Smart debug is a class that forces you to create functions for debugging.
 * 
 * @author Andrew Kerr
 */
public abstract class SmartDebug implements SmartDebugInterface{
	
	/**
	 * @apiNote This method has been overwritten by {@linkplain SmartDebug}.
	 * It now returns the result of {@linkplain SmartDebugInterface#toString(String, String)}, using the parameters ( "", "\t" )
	 */
	public String toString() {
		return this.toString("", "\t");
	}
	
}
