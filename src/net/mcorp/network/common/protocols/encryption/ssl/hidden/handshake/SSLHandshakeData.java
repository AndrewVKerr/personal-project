package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.utils.NetworkingUtils;

public abstract class SSLHandshakeData extends NetworkingUtils{

	public final PsudoFinalVariable<Integer> data_type = new PsudoFinalVariable<Integer>();
	public final PsudoFinalVariable<Long> length_of_packet = new PsudoFinalVariable<Long>();
	
	public SSLHandshakeData(int dt) {
		this.data_type.set(dt);
	}
	
	protected SSLHandshakeData(int dt, InputStream in) throws IOException {
		this.data_type.set(dt);
		byte[] buffer = in.readNBytes(3);
		if(buffer.length < 3)
			throw new IOException("[SSLHandshakeData(InputStream):END_OF_STREAM] The client closed the connection before enough data could be sent to satisfy the construction of a SSLHandshakeData!");
		this.length_of_packet.set(Long.parseLong(this.toBinStr(new byte[] {buffer[0],buffer[1],buffer[2]}),2));
	}
	
	protected abstract void calcLength();
	
	public abstract String getData();
	
}
