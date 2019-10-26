
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
		return String.format("%8s", Integer.toBinaryString(in.read())).replace(' ', '0');
	}
	
	public default String toHexString(int i) {
		return toHexString(i,true);
	}
	
	public default String toHexString(int i, boolean add0x) {
		String str = Integer.toHexString(i);
		int x = str.length() % 2;
		return (add0x ? "0x" : "")+("0".repeat(x))+str;
	}
	
	public default String toHexString(long l) {
		return toHexString(l,true);
	}
	
	public default String toHexString(long l, boolean add0x) {
		String str = Long.toHexString(l);
		int x = str.length() % 2;
		return (add0x ? "0x" : "")+("0".repeat(x))+str.toUpperCase();
	}
	
	public default String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[]";
		return str;
	}
	
	public default String toBinStr(boolean bool) {
		return (bool ? "1" : "0");
	}
	
	public default String toBinStr(byte bite, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(bite)).replace(' ', '0');
	}
	
	public default String toBinStr(long bite, int len) {
		return String.format("%"+len+"s",Long.toBinaryString(bite)).replace(' ', '0');
	}
	
}
