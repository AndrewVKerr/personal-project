package net.mcorp.network.common.protocols.encryption.ssl.hidden.handshake.common;

import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public class Cipher implements SmartDebugInterface{
	
	public final CipherSuites ciphers;
	public final PsudoFinalVariable<Cipher> lastCipher = new PsudoFinalVariable<Cipher>();
	public final PsudoFinalVariable<Cipher> nextCipher = new PsudoFinalVariable<Cipher>();
	
	public final long id;
	
	public Cipher(CipherSuites ciphers, long id) {
		this.ciphers = ciphers;
		this.id = id;
	}
	
	private void addCall(Cipher cipher) {
		if(this.nextCipher.isFinal()) {
			this.nextCipher.get().addCall(cipher);
		}else {
			this.nextCipher.set(cipher);
			if(cipher != null) //Allows the capping of the list without throwing an error.
				cipher.lastCipher.set(this);
		}
	}
	
	public void add(Cipher cipher) {
		if(this.length() != Long.MAX_VALUE) {
			this.addCall(cipher);
		}else {
			throw new RuntimeException("[Cipher.add(Cipher):OUT_OF_MEMORY] The amount of ciphers exceeds the maximum value of a long.");
		}
	}
	
	private long lengthCall() {
		return 1 + (this.nextCipher.get() == null ? 0 : this.nextCipher.get().lengthCall());
	}
	
	public long length() {
		return ciphers.startCipher.get().lengthCall();
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"id = "+id+","
				+ "\n"+indent+indentBy+"lastCipher = PsudoFinalVariable[...],"
				+ "\n"+indent+indentBy+"nextCipher = "+this.nextCipher+""
				+ "\n"+indent+"]";
	}
	
}
