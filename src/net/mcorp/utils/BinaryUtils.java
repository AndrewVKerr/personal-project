package net.mcorp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

/**
 * BinaryUtils is a class that provides some simple and repetitive functions that allow for the transferring of binary strings into numbers and vis versa.
 * @author Andrew Kerr
 */
public abstract class BinaryUtils extends SmartDebug{
	
	/**
	 * Retrieves the next (n) bytes from the provided InputStream (in).
	 * @param in - {@linkplain InputStream} - The stream to listen to.
	 * @param n - {@linkplain Integer} - How many bytes to return.
	 * @return {@linkplain String} - A BinaryString
	 * @throws IOException Thrown if the {@linkplain InputStream} encounters an exception.
	 */
	protected String getNextNBytes(InputStream in, int n) throws IOException {
		String temp = "";
		for(int i = 0; i < n; i++) {
			temp += getNextByte(in);
		}
		return temp;
	}

	/**
	 * Retrieves the next byte from the provided InputStream (in).
	 * @param in - {@linkplain InputStream} - The stream to listen to.
	 * @return {@linkplain String} - A BinaryString
	 * @throws IOException Thrown if the {@linkplain InputStream} encounters an exception.
	 */
	protected String getNextByte(InputStream in) throws IOException{
		return String.format("%8s", Integer.toBinaryString(in.read())).replace(' ', '0');
	}
	
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
	
	protected void write(OutputStream out, String binaryString) throws IOException{
		try {
			for(int i = 0; i < binaryString.length()/8; i++) {
				out.write(Integer.parseUnsignedInt(binaryString.substring(i*8,i*8+8), 2));
			}
		}catch(Exception e) {
			if(e instanceof SocketException && e.getLocalizedMessage().contains("Broken pipe")) {
				IOException ioe = new IOException("[BinaryUtils.write(OutputStream,String):FORCED_CLOSED] Client forced connection closed!");
				ioe.addSuppressed(e);
				throw ioe;
			}
			throw e;
		}
	}
	
}
