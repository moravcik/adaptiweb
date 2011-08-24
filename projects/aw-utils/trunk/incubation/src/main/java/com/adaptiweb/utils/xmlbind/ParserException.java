package com.adaptiweb.utils.xmlbind;


public class ParserException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParserException(Throwable e) {
		super(e);
	}

	public ParserException(String msg) {
		super(msg);
	}

}
