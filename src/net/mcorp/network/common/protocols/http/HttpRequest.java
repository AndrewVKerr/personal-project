package net.mcorp.network.common.protocols.http;

import java.util.concurrent.TimeoutException;

import net.mcorp.common.*;
import net.mcorp.common.pseudo.PseudoFinalException;
import net.mcorp.common.pseudo.PseudoFinalVariable;
import net.mcorp.common.utils.debug.SmartDebug;
import net.mcorp.common.utils.debug.SmartDebugInterface;
import net.mcorp.network.common.protocols.PacketData;

public class HttpRequest extends PacketData implements SmartDebugInterface{

	public static enum Method{
		GET,
		POST
	}
	
	public final PseudoFinalException<TimeoutException> timeoutException = new PseudoFinalException<TimeoutException>(); 
	
	public final PseudoFinalVariable<Method> method = new PseudoFinalVariable<Method>();
	public final PseudoFinalVariable<String> url = new PseudoFinalVariable<String>();
	public final PseudoFinalVariable<String> version = new PseudoFinalVariable<String>();
	
	public final HttpHeaders headers = new HttpHeaders();
	
	public final PseudoFinalVariable<String> data = new PseudoFinalVariable<String>();

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"method = "+method.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"url = "+url.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"version = "+version.toString(indent+indentBy, indentBy)
				+ "\n"+indent+"]";
	}
	
}
