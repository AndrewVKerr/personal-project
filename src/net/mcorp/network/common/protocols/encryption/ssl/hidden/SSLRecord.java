package net.mcorp.network.common.protocols.encryption.ssl.hidden;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.network.common.utils.NetworkingUtils;

public class SSLRecord extends NetworkingUtils{
	
	public final int record_type;
	public final int[] tls_version;
	public final long length_of_packet;
	
	public SSLRecord(int rt, int[] tls_v, long lop) {
		this.record_type = rt;
		this.tls_version = tls_v;
		this.length_of_packet = lop;
	}
	
	protected SSLRecord(InputStream in) throws IOException {
		byte[] buffer = in.readNBytes(5);
		if(buffer.length < 5)
			throw new IOException("[SSLRecord(InputStream):END_OF_STREAM] The client closed the connection before enough data could be sent to satisfy the construction of a SSLRecord!");
		this.record_type = buffer[0];
		this.tls_version = new int[] { buffer[1], buffer[2] };
		this.length_of_packet = Long.parseLong(this.toBinStr(new byte[] {buffer[3],buffer[4]}),2);
	}

	@Override
	public String toString(String indent, String indentBy) {
		String s = this.getClass().getSimpleName()+"[";
		s += "\n"+indent+indentBy+"record_type = "+this.record_type+",";
		s += "\n"+indent+indentBy+"tls_version = int["+this.tls_version.length+"]{";
		for( int i : this.tls_version) {
			s += "\n"+indent+indentBy+indentBy+i+",";
		}
		s += "\n"+indent+indentBy+"},";
		s += "\n"+indent+indentBy+"length_of_packet = "+this.length_of_packet;
		s += "\n"+indent+"]";
		return s;
	}
	
}
