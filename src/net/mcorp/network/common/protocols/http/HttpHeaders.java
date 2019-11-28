package net.mcorp.network.common.protocols.http;

import net.mcorp.common.pseudo.PseudoFinalArrayList;
import net.mcorp.common.pseudo.PseudoFinalVariable;
import net.mcorp.common.utils.debug.SmartDebug;
import net.mcorp.common.utils.debug.SmartDebugInterface;

public class HttpHeaders implements SmartDebugInterface{
	
	public HttpHeaders() {};
	
	private PseudoFinalArrayList<HttpHeader> headers = new PseudoFinalArrayList<HttpHeader>();
	public HttpHeader getHeader(String name) {
		for(HttpHeader header : (HttpHeader[])headers.toArray()) {
			if(header.name.get() == name)
				return header;
		}
		return null;
	}
	
	public void setHeader(String name, String value) {
		HttpHeader header = this.getHeader(name);
		if(header == null) {
			header = this.addHeader(new HttpHeader());
			header.name.set(name);
		}
		header.value.set(value);
	}
	
	public HttpHeader addHeader(HttpHeader header) {
		if(headers.isFinal())
			throw new RuntimeException("[HttpHeaders.addHeader(HttpHeader):FINAL] Cannot add header, isFinal flag is set.");
		this.headers.add(header);
		return header;
	}
	
	public void publish() {
		headers.publish();
	}
	
	public boolean isFinal() {
		return headers.isFinal();
	}
	
	public static class HttpHeader extends SmartDebug{
		
		public final PseudoFinalVariable<String> name = new PseudoFinalVariable<String>();
		public final PseudoFinalVariable<String> value = new PseudoFinalVariable<String>();
		
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
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"headers = "+headers.toString(indent+indentBy, indentBy)
				+ "\n"+indent+"]";
	}

	public PseudoFinalArrayList<HttpHeader> getHeaders() {
		if(this.isFinal())
			return this.headers;
		return null;
	}
	
}