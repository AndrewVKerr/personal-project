package net.mcorp.network.common.utils.debug;

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
	
	/**
	 * Calling this function will dump the result of this objects {@linkplain SmartDebug#toString()} function info the provided file.<br>
	 * NOTE: This function will delete all contents of the provided file.
	 * @param file - {@linkplain File} - The target file to dump data into.
	 * @throws IOException
	 */
	public final void dumpDebug(File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(toString());
		writer.close();
	}
	
	public static String readSmartDebug(String indent, String indentBy, Object obj) {
		if(obj != null) {
			if(obj instanceof SmartDebug)
				return ((SmartDebug)obj).toString(indent, indentBy);
			return obj.toString();
		}
		return "null";
	}
	
}
