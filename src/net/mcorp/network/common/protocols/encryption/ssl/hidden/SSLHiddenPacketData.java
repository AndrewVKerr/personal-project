package net.mcorp.network.common.protocols.encryption.ssl.hidden;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.encryption.EncryptedPacketData;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.SSLClientHello;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.SSLHandshakeData;

public class SSLHiddenPacketData extends EncryptedPacketData {
	
	public final SSLHiddenLayer hiddenLayer;
	public final SSLRecord record;
	public final SSLHandshakeData handshake;
	
	public SSLHiddenPacketData(SSLHiddenLayer hiddenLayer) throws ConnectionException, IOException {
		this.hiddenLayer = hiddenLayer;
		InputStream in = this.hiddenLayer.ssl.connection.getRawInputStream();
		this.record = new SSLRecord(in);
		long type = in.read();
		if(type == 1) {
			this.handshake = new SSLClientHello(in);
		}else {
			this.handshake = null;
		}
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+this.hiddenLayer.toString(indent+indentBy,indentBy)+","
				+ "\n"+indent+indentBy+this.record.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+this.handshake.toString(indent+indentBy, indentBy)+""
				+ "\n"+indent+"]";
	}
	
}
