package net.mcorp.networked.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public abstract class NetworkingUtils extends BinaryUtils{

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
	
	protected String readUntil(InputStream in, char stop) throws IOException {
		String str = "";
		char c = (char)in.read();
		while(c != '\n') {
			str += c;
			c = (char)in.read();
		}
		return str;
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
