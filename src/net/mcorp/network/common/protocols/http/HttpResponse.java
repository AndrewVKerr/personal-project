package net.mcorp.network.common.protocols.http;

import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.protocols.PacketData;
import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public class HttpResponse extends PacketData implements SmartDebugInterface{

	public final PsudoFinalVariable<Integer> status_code = new PsudoFinalVariable<Integer>();
	public final PsudoFinalVariable<String> status_text = new PsudoFinalVariable<String>();
	public final PsudoFinalVariable<String> httpVersion = new PsudoFinalVariable<String>();
	
	public final HttpHeaders headers = new HttpHeaders();
	
	public final PsudoFinalVariable<String> data = new PsudoFinalVariable<String>();
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

}
