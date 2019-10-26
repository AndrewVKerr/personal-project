package net.mcorp.server.protocols.https.extensions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.mcorp.utils.BinaryUtilitys;

public final class Extensions implements BinaryUtilitys{
	
	public static abstract class Extension{
		
		private int id;
		public final int id() { return this.id; };
		
		public Extension(int id) {
			this.id = id;
		}

		public final String toString() {
			return this.toString("", "\t");
		}
		
		public abstract String toString(String indent, String indentBy);
		
	}
	
	private static ArrayList<Extension> gExtensions = new ArrayList<Extension>();
	
	public static boolean addGlobalExtension(Extension extension) {
		if(getGlobalExtension(extension.id) == null)
			return gExtensions.add(extension);
		return false;
	}
	
	public static Extension getGlobalExtension(int id) {
		for(Extension extension : gExtensions) {
			if(extension.id == id)
				return extension;
		}
		return null;
	}
	
	private ArrayList<Extension> extensions = new ArrayList<Extension>();
	
	public Extension getExtension(int id) {
		for(Extension extension : extensions) {
			if(extension.id == id)
				return extension;
		}
		return null;
	}
	
	public Extension getExtensionAtIndex(int index) {
		return extensions.get(index);
	}
	
	public Extension[] getExtensions() {
		return this.extensions.toArray(new Extension[0]);
	}
	
	private int length;
	public int length() { return this.length; };
	public void calcLength() {
		length = this.extensions.size()*2;
	}
	
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
			Extension extension = getGlobalExtension(id);
			if(extension != null) {
				this.extensions.add(extension);
			}else{
				//System.err.println("Unsupported Extension: ID["+id+"] HEXID["+this.toHexString(id)+"]");
			}
		}
		this.length = this.extensions.size();
		
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"length = Integer["+length+"],";
		str += "\n"+indent+indentBy+"givenLength = Integer["+this.givenLength+"],";
		str += "\n"+indent+indentBy+"extensions = ArrayList<Extension>[";
		for(int i = 0; i < extensions.size(); i++) {
			Extension extension = extensions.get(i);
			str += "\n"+indent+indentBy+indentBy+extension.toString(indent+indentBy+indentBy+indentBy,indentBy)
						+(i < extensions.size()-1 ? "," : "");
		}
		str += "\n"+indent+indentBy+"]";
		str += "\n"+indent+"]";
		return str;
	}
	
}

