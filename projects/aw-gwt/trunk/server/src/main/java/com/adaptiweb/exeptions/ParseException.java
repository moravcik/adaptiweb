package com.adaptiweb.exeptions;

/**
 * Exception used when parse error occurs.
 */
public class ParseException extends UserFaultException {

	private static final long serialVersionUID = 1L;

	public ParseException(Throwable cause) {
		super(cause);
	}

	public ParseException(String message, Object...objects) {
		super(message, objects);
	}

}
