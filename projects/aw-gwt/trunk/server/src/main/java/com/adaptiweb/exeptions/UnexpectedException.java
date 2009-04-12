package com.adaptiweb.exeptions;

/**
 * Meant for wrapping Exceptions which logically can't occur.
 * Better to be safe than sorry.
 */
public class UnexpectedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnexpectedException(Exception cause) {
		super(cause);
	}

}
