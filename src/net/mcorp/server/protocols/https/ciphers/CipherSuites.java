package net.mcorp.server.protocols.https.ciphers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.mcorp.utils.BinaryUtilitys;

public final class CipherSuites implements BinaryUtilitys{
	
	private static boolean loadedGlobals = false;
	
	private void loadGlobal() {
		if(loadedGlobals)
			return;
		addGlobalCipher(GreaseCipher.instance);
		addGlobalCipher(TLS_RSA_WITH_NULL_NULL.instance);
		loadedGlobals = true;
	}
	
	public CipherSuites() {
		this.loadGlobal();
	}
	
	public static abstract class CipherSuite implements BinaryUtilitys{
		
		private int id = -1;
		public final int id() { return this.id; };
		
		private int[] ids = null;
		public final int[] ids() { return this.ids; };
		
		public CipherSuite(int id) {
			this.id = id;
		}
		
		public CipherSuite(int[] ids) {
			this.ids = ids;
		}
		
		public boolean idMatch(int id) {
			if(this.id == -1) {
				for(int i : ids) {
					if(id == i)
						return true;
				}
				return false;
			}else {
				return (this.id == id);
			}
		}

		public final String toString() {
			return this.toString("", "\t");
		}
		
		public abstract String toString(String indent, String indentBy);
		
	}
	
	private static ArrayList<CipherSuite> gCiphers = new ArrayList<CipherSuite>();
	
	public static boolean addGlobalCipher(CipherSuite cipher) {
		if(getGlobalCipher(cipher.id) == null)
			return gCiphers.add(cipher);
		return false;
	}
	
	public static CipherSuite getGlobalCipher(int id) {
		for(CipherSuite cipher : gCiphers) {
			if(cipher.idMatch(id))
				return cipher;
		}
		return null;
	}
	
	private ArrayList<Integer> cipherIds = new ArrayList<Integer>();
	private ArrayList<CipherSuite> ciphers = new ArrayList<CipherSuite>();
	
	public Integer[] cipherIds() {
		return cipherIds.toArray(new Integer[0]);
	}
	
	public CipherSuite getCipher(int id) {
		for(CipherSuite cipher : ciphers) {
			if(cipher.idMatch(id))
				return cipher;
		}
		return null;
	}
	
	public CipherSuite getCipherAtIndex(int index) {
		return ciphers.get(index);
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
		
		givenLength = Integer.parseInt(this.getNextNBytes(in,2),2);
		for(int i = 0; i < this.givenLength; i++) {
			int id = Integer.parseInt(this.getNextNBytes(in,2),2);
			CipherSuite cipher = getGlobalCipher(id);
			this.cipherIds.add(id);
			if(cipher != null) {
				this.ciphers.add(cipher);
			}else{
				//System.err.println("Unsupported Cipher: ID["+id+"] HEXID["+this.toHexString(id)+"]");
			}
		}
		this.length = this.ciphers.size();
		
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"length = Integer["+length+"],";
		str += "\n"+indent+indentBy+"givenLength = Integer["+this.givenLength+"],";
		str += "\n"+indent+indentBy+"ciphers = ArrayList<CipherSuite>[";
		for(int i = 0; i < ciphers.size(); i++) {
			CipherSuite cipher = ciphers.get(i);
			str += "\n"+indent+indentBy+indentBy+cipher.toString(indent+indentBy+indentBy,indentBy)
						+(i < ciphers.size()-1 ? "," : "");
		}
		str += "\n"+indent+indentBy+"]";
		str += "\n"+indent+"]";
		return str;
	}
	
	
}
