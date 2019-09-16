package net.projectio.utils.exceptions;

import java.io.IOException;

public class TimedOutException extends IOException {
	
	private static final long serialVersionUID = 7775412772078512311L;

	public TimedOutException(String str) {
		super(str);
	}
	
}
