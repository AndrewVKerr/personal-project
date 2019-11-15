package net.mcorp.network.common.protocols.http;

import java.util.ArrayList;

import net.mcorp.common.PsudoFinalArrayList;
import net.mcorp.common.PsudoFinalVariable;
import net.mcorp.network.common.protocols.PacketData;
import net.mcorp.network.common.utils.debug.SmartDebug;
import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public class HttpData extends PacketData implements SmartDebugInterface{

	public static enum Method{
		GET,
		POST
	}
	
	public final PsudoFinalVariable<Method> method = new PsudoFinalVariable<Method>();
	public final PsudoFinalVariable<String> url = new PsudoFinalVariable<String>();
	public final PsudoFinalVariable<String> version = new PsudoFinalVariable<String>();
	
	public static class HttpHeaders extends PsudoFinalArrayList{
		
		private HttpHeaders() {};
		
		private ArrayList<HttpHeader> headers = new ArrayList<HttpHeader>();
		
		
		public static class HttpHeader extends SmartDebug{
			
			public final PsudoFinalVariable<String> name = new PsudoFinalVariable<String>();
			public final PsudoFinalVariable<String> value = new PsudoFinalVariable<String>();
			
			@Override
			public String toString(String indent, String indentBy) {
				return this.getClass().getSimpleName()+"["
						+ "\n"+indent+indentBy+name.toString(indent+indentBy, indentBy)+","
						+ "\n"+indent+indentBy+value.toString(indent+indentBy, indentBy)
						+ "\n"+indent+"]";
			}
			
		}

		@Override
		public String toString(String indent, String indentBy) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public final ArrayList<HttpHeaders> headers = new ArrayList<HttpHeaders>();

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"method = "+method.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"url = "+url.toString(indent+indentBy, indentBy)+","
				+ "\n"+indent+indentBy+"version = "+version.toString(indent+indentBy, indentBy)
				+ "\n"+indent+"]";
	}
	
}
