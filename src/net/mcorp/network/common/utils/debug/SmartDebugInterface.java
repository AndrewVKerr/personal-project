package net.mcorp.network.common.utils.debug;

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
	
}
