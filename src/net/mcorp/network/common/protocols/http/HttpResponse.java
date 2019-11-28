package net.mcorp.network.common.protocols.http;

import net.mcorp.common.pseudo.PseudoFinalVariable;
import net.mcorp.common.utils.debug.SmartDebugInterface;
import net.mcorp.network.common.protocols.PacketData;

public class HttpResponse extends PacketData implements SmartDebugInterface{

	public final PseudoFinalVariable<Integer> status_code = new PseudoFinalVariable<Integer>();
	public final PseudoFinalVariable<String> status_text = new PseudoFinalVariable<String>();
	public final PseudoFinalVariable<String> httpVersion = new PseudoFinalVariable<String>();
	
	public final HttpHeaders headers = new HttpHeaders();
	
	public final PseudoFinalVariable<String> data = new PseudoFinalVariable<String>();
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

}
