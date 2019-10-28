package net.mcorp.utils;

/**
 * Smart debug is a class that forces you to create functions for debugging.
 * 
 * @author Andrew Kerr
 */
public abstract class SmartDebug {
	
	public String toString() {
		return this.toString("", "\t");
	}
	
	public abstract String toString(String indent, String indentBy);
	
}
