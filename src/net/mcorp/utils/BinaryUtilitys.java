
package net.mcorp.utils;

import java.io.IOException;
import java.io.InputStream;

public interface BinaryUtilitys {
	
	public default String getNextNBytes(InputStream in, int n) throws IOException {
		String temp = "";
		for(int i = 0; i < n; i++) {
			temp += getNextByte(in);
		}
		return temp;
	}

	public default String getNextByte(InputStream in) throws IOException{
		System.out.println("Getting next byte");
		return String.format("%8s", Integer.toBinaryString(in.read())).replace(' ', '0');
	}
	
	public default String toHexString(int i) {
		String str = Integer.toHexString(i);
		int x = str.length() % 2;
		return "0x"+("0".repeat(x))+str;
	}
	
	public default String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[]";
		return str;
	}
	
}
