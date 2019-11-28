package net.mcorp.common.utils.debug;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This interface is designed to implement a few methods used to print out a objects variables and results of methods.
 * @author Andrew Kerr
 */
public interface SmartDebugInterface {
	
	/**
	 * This method first defined in {@linkplain SmartDebugInterface} is used to neatly print out an objects internal variables and/or function results.<br>
	 * It is intended to give quick and pretty feedback to the developer as a means of speeding up the debugging process.
	 * @param indent - {@linkplain String} - A string containing all of the previous indents leading up to this point.
	 * @param indentBy - {@linkplain String} - A string containing the characters you wish to add to the indent.
	 * @return {@linkplain String} - A string object that has been indented for easier reading.
	 * @apiNote It should be noted if you are creating a localized instance of this class that the first line should never be indented:
	 * Indenting the first line will miss-align any recursive calls to the same type of function on another object.<br>
	 * <p>
	 * <b><u>Example:</u></b><br>
	 * <b>String</b> result = "";<br>
	 * result += Simple Class Name + '[';<br>
	 * result += New Line + Indent + IndentBy + Variable/Function Name + ' = ' + Variable/Function value/result + ','; (Add comma if this is not the last line)<br>
	 * --------------> Repeat last line as many times as necessary.<br>
	 * result += New Line + Indent + ']'; (NOTE: Do not add a comma here as it will most likely be added by the calling toString method or not added at all.)<br>
	 * return result;
	 * </p>
	 */
	public String toString(String indent, String indentBy);
	
	/**
	 * Calling this method will result in an attempt to read a possible {@linkplain SmartDebug} object.
	 * If the object provided is not of a {@linkplain SmartDebug} subclass then it will simply print out
	 * that objects {@linkplain Object#toString()} result. If the object provided is null then it will return
	 * a string of "null".
	 * @param indent - {@linkplain String} - The current indent.
	 * @param indentBy - {@linkplain String} - The string that is added to the indent object.
	 * @param obj - {@linkplain Object ? extends Object} - A object that could be a subclass of SmartDebug.
	 * @return {@linkplain String} - A string object.
	 */
	public static String readSmartDebug(String indent, String indentBy, Object obj) {
		if(obj != null) {
			if(obj instanceof SmartDebug)
				return ((SmartDebug)obj).toString(indent, indentBy);
			return obj.toString();
		}
		return "null";
	}
	
	/**
	 * Calling this function will dump the result of this objects {@linkplain SmartDebug#toString()} function info the provided file.<br>
	 * NOTE: This function will delete all contents of the provided file.
	 * @param file - {@linkplain File} - The target file to dump data into.
	 * @throws IOException
	 */
	public static void dumpDebug(SmartDebugInterface debugObj, File file) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(debugObj.toString("", "\t"));
		writer.close();
	}
}
