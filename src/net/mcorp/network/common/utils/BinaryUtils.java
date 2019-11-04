package net.mcorp.network.common.utils;

import net.mcorp.network.common.utils.debug.SmartDebug;

/**
 * BinaryUtils is a class that provides some simple and repetitive functions that allow for the transferring of binary strings into numbers and vis versa.
 * @author Andrew Kerr
 */
public abstract class BinaryUtils extends SmartDebug{
	
	/**
	 * Turns the provided Integer(i) into a readable hex string. Example: i = 7, returns "0x07"
	 * @param i - {@linkplain Integer} - The integer to convert into a hex string.
	 * @return {@linkplain String} - The Hex String representation of {@linkplain Integer}(i).
	 */
	protected String toHexString(int i) {
		return toHexString(i,true);
	}
	
	/**
	 * Turns the provided Integer(i) into a readable hex string.
	 * @param i - {@linkplain Integer} - The integer to convert into a hex string.
	 * @param add0x - {@linkplain Boolean} - True: Add '0x' to beginning, False: Dont add '0x' to beginning.
	 * @return {@linkplain String} - The Hex String representation of {@linkplain Integer}(i).
	 */
	protected String toHexString(int i, boolean add0x) {
		String str = Integer.toHexString(i);
		int x = str.length() % 2;
		return (add0x ? "0x" : "")+("0".repeat(x))+str;
	}
	
	/**
	 * Turns the provided Long(l) into a readable hex string. Example: l = 70, returns "0x70"
	 * @param l - {@linkplain Long} - The long to convert into a hex string.
	 * @return {@linkplain String} - The Hex String representation of {@linkplain Long}(l).
	 */
	protected String toHexString(long l) {
		return toHexString(l,true);
	}
	
	/**
	 * Turns the provided Long(l) into a readable hex string.
	 * @param l - {@linkplain Long} - The long to convert into a hex string.
	 * @param add0x - {@linkplain Boolean} - True: Add '0x' to beginning, False: Dont add '0x' to beginning.
	 * @return {@linkplain String} - The Hex String representation of {@linkplain Long}(l).
	 */
	protected String toHexString(long l, boolean add0x) {
		String str = Long.toHexString(l);
		int x = str.length() % 2;
		return (add0x ? "0x" : "")+("0".repeat(x))+str.toUpperCase();
	}
	
	/**
	 * Turns a {@linkplain Boolean} value into a "1" or "0".
	 * @param bool - {@linkplain Boolean} - Boolean value to convert into BinaryString.
	 * @return {@linkplain String} - "1" / "0" depending on the state of the {@linkplain Boolean}(bool).
	 */
	protected String toBinStr(boolean bool) {
		return (bool ? "1" : "0");
	}
	
	/**
	 * Turns a {@linkplain Byte} value into a BinaryString.
	 * @param bite - {@linkplain Byte} - Byte value to convert into BinaryString.
	 * @param len - {@linkplain Integer} - How many 1's and 0's (bits) should be in this BinaryString.
	 * @return {@linkplain String} - A string comprised of 1's and 0's padded with leading 0's if necessary.
	 */
	protected String toBinStr(byte bite, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(bite)).replace(' ', '0');
	}
	
	/**
	 * Turns a {@linkplain Long} value into a BinaryString.
	 * @param bite - {@linkplain Long} - Long value to convert into BinaryString.
	 * @param len - {@linkplain Integer} - How many 1's and 0's (bits) should be in this BinaryString.
	 * @return {@linkplain String} - A string comprised of 1's and 0's padded with leading 0's if necessary.
	 */
	protected String toBinStr(long bite, int len) {
		return String.format("%"+len+"s",Long.toBinaryString(bite)).replace(' ', '0');
	}
	
}
