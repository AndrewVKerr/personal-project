package net.mcorp.network.common.protocols.http;

import net.mcorp.common.pseudo.PseudoFinalVariable;

public class HttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9083884061225749939L;

	public final PseudoFinalVariable<Integer> status_code = new PseudoFinalVariable<Integer>();
	public final PseudoFinalVariable<String> status_text = new PseudoFinalVariable<String>();
	
	public HttpException(int status_code, String status_text) {
		this.status_code.set(status_code);
		this.status_text.set(status_text);
	}
	
	public HttpException(int status_code) {
		this.status_code.set(status_code);
		this.status_text.set("Unknown");
	}
	
	public HttpException(String status_text) {
		this.status_code.set(500);
		this.status_text.set(status_text);
	}
	
	public HttpException(Exception e) {
		this.status_code.set(500);
		this.status_text.set(e.getLocalizedMessage());
	}

	public HttpResponse convertToHttpData() {
		HttpResponse data = new HttpResponse();
		data.status_code.set(status_code.get());
		data.status_text.set(status_text.get());
		data.headers.publish();
		return data;
	}
	
}
