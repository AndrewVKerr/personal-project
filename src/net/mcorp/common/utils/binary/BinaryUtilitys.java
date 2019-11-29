package net.mcorp.common.utils.binary;

public class BinaryUtilitys {
	
	public static String toBinaryString(boolean bool) {
		return (bool ? "1" : "0");
	}
	
	public static String toBinaryString(int integer, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(integer)).replace(' ', '0');
	}
	
	public static String toBinaryString(byte bite, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(bite)).replace(' ', '0');
	}
	
	public static String toBinaryString(long long_, int len) {
		return String.format("%"+len+"s",Long.toBinaryString(long_)).replace(' ', '0');
	}
	
	public static String toBinaryString(short short_, int len) {
		return String.format("%"+len+"s",Integer.toBinaryString(short_)).replace(' ', '0');
	}
	
}
