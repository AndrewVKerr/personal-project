package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.common;

import java.io.IOException;
import java.io.InputStream;

import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.utils.NetworkingUtils;

public class CipherSuites extends NetworkingUtils {
	
	public final long length;
	public final PsudoFinalVariable<Cipher> startCipher = new PsudoFinalVariable<Cipher>();
	
	public CipherSuites(Cipher starting_cipher) {
		this.startCipher.set(starting_cipher);
		this.length = starting_cipher.length();
	}
	
	public CipherSuites(InputStream in) throws IOException {
		byte[] buffer = in.readNBytes(2);
		this.length = Long.parseLong(this.toBinStr(new byte[] {buffer[0],buffer[1]}),2);
		for(int i = 0; i < this.length; i++) {
			buffer = in.readNBytes(2);
			Long val = Long.parseLong(this.toBinStr(new byte[] {buffer[0],buffer[1]}),2);
			if(startCipher.isFinal() == false) {
				Cipher start = new Cipher(this,val);
				start.lastCipher.set(null);
				this.startCipher.set(start);
			}else {
				Cipher next = new Cipher(this,val);
				this.startCipher.get().add(next);
			}
		}
		this.startCipher.get().add(null);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"length = "+this.length+","
				+ "\n"+indent+indentBy+"startCipher = "+this.startCipher.toString(indent+indentBy, indentBy)
				+ "\n"+indent+"]";
	}
	
}
