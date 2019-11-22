package net.mcorp.network.common.protocols.http;

import java.util.concurrent.TimeoutException;

import net.mcorp.common.*;
import net.mcorp.network.common.protocols.PacketData;
import net.mcorp.network.common.utils.debug.SmartDebug;
import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public class HttpRequest extends PacketData implements SmartDebugInterface{

	public static enum Method{
		GET,
		POST
	}
	
	public final PsudoFinalException<TimeoutException> timeoutException = new PsudoFinalException<TimeoutException>(); 
	
	public final PsudoFinalVariable<Method> method = new PsudoFinalVariable<Method>();
	public final PsudoFinalVariable<String> url = new PsudoFinalVariable<String>();
	public final PsudoFinalVariable<String> version = new PsudoFinalVariable<String>();
	
	public final HttpHeaders headers = new HttpHeaders();
	
	public final PsudoFinalVariable<String> data = new PsudoFinalVariable<String>();

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"method = "+method.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"url = "+url.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"version = "+version.toString(indent+indentBy, indentBy)
				+ "\n"+indent+"]";
	}
	
}
