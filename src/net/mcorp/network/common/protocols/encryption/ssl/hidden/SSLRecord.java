package net.mcorp.network.common.protocols.encryption.ssl.hidden;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.network.common.utils.NetworkingUtils;

public class SSLRecord extends NetworkingUtils{
	
	public final int record_type;
	public final long tls_version;
	public final long length_of_packet;
	
	public SSLRecord(int rt, long tls_v, long lop) {
		this.record_type = rt;
		this.tls_version = tls_v;
		this.length_of_packet = lop;
	}
	
	protected SSLRecord(InputStream in) throws IOException {
		byte[] buffer = in.readNBytes(5);
		if(buffer.length < 5)
			throw new IOException("[SSLRecord(InputStream):END_OF_STREAM] The client closed the connection before enough data could be sent to satisfy the construction of a SSLRecord!");
		this.record_type = buffer[0];
		this.tls_version = Long.parseLong(this.toBinStr(new byte[] {buffer[1],buffer[2]}),2);
		this.length_of_packet = Long.parseLong(this.toBinStr(new byte[] {buffer[3],buffer[4]}),2);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"record_type = "+this.record_type+","
				+ "\n"+indent+indentBy+"tls_version = "+this.tls_version+","
				+ "\n"+indent+indentBy+"length_of_packet = "+this.length_of_packet
				+ "\n"+indent+"]";
	}
	
}
