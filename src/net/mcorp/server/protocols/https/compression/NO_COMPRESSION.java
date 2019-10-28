package net.mcorp.server.protocols.https.compression;

import net.mcorp.server.protocols.https.compression.CompressionMethods.CompressionMethod;

public class NO_COMPRESSION extends CompressionMethod {

	public static final NO_COMPRESSION instance = new NO_COMPRESSION();

	private NO_COMPRESSION() {
		super(0);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

}
