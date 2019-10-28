package net.mcorp.server.protocols.https.compression;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.mcorp.server.protocols.https.ciphers.GreaseCipher;
import net.mcorp.server.protocols.https.ciphers.TLS_RSA_WITH_NULL_NULL;
import net.mcorp.utils.BinaryUtilitys;

public final class CompressionMethods implements BinaryUtilitys{
	
	private static boolean loadedGlobals = false;
	
	private void loadGlobal() {
		if(loadedGlobals)
			return;
		addGlobalMethod(NO_COMPRESSION.instance);
		loadedGlobals = true;
	}
	
	public CompressionMethods() {
		this.loadGlobal();
	}
	
	public static abstract class CompressionMethod{
		
		private int id;
		public final int id() { return this.id; };
		
		public CompressionMethod(int id) {
			this.id = id;
		}

		public final String toString() {
			return this.toString("", "\t");
		}
		
		public abstract String toString(String indent, String indentBy);
		
	}
	
	private static ArrayList<CompressionMethod> gMethods = new ArrayList<CompressionMethod>();
	
	public static boolean addGlobalMethod(CompressionMethod method) {
		if(getGlobalMethod(method.id) == null)
			return gMethods.add(method);
		return false;
	}
	
	public static CompressionMethod getGlobalMethod(int id) {
		for(CompressionMethod method : gMethods) {
			if(method.id == id)
				return method;
		}
		return null;
	}
	
	private ArrayList<CompressionMethod> methods = new ArrayList<CompressionMethod>();
	
	public CompressionMethod getMethod(int id) {
		for(CompressionMethod method : methods) {
			if(method.id == id)
				return method;
		}
		return null;
	}
	
	public CompressionMethod getMethodAtIndex(int index) {
		return methods.get(index);
	}
	
	private int length;
	public int length() { return this.length; };
	
	private int givenLength;
	public int givenLength() { return this.givenLength; };
	
	private boolean readFinished = false;
	public final void read(InputStream in) throws IOException {
		if(readFinished)
			throw new IOException("Read Routine has already been ran once!");
		this.readRoutine(in);
		readFinished = true;
	}
	
	protected void readRoutine(InputStream in) throws IOException{
		
		givenLength = Integer.parseInt(this.getNextNBytes(in,1),2);
		for(int i = 0; i < this.givenLength; i++) {
			int id = Integer.parseInt(this.getNextByte(in),2);
			CompressionMethod method = getGlobalMethod(id);
			if(method != null) {
				this.methods.add(method);
			}else{
				//System.err.println("Unsupported CompressionMethod: ID["+id+"] HEXID["+this.toHexString(id)+"]");
			}
		}
		this.length = this.methods.size();
		
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"length = Integer["+length+"],";
		str += "\n"+indent+indentBy+"givenLength = Integer["+this.givenLength+"],";
		str += "\n"+indent+indentBy+"methods = ArrayList<CompressionMethod>[";
		for(int i = 0; i < methods.size(); i++) {
			CompressionMethod cipher = methods.get(i);
			str += "\n"+indent+indentBy+indentBy+cipher.toString(indent+indentBy+indentBy+indentBy,indentBy)
						+(i < methods.size()-1 ? "," : "");
		}
		str += "\n"+indent+indentBy+"]";
		str += "\n"+indent+"]";
		return str;
	}
	
	
}

