package com.adaptiweb.exeptions;

/**
 * Any error caused by user which should be propagated back to user. 
 */
public class UserFaultException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserFaultException() {
		super();
	}

	public UserFaultException(String message) {
		super(message);
	}

	public UserFaultException(String format, Object...args) {
		super(args.length == 0 ? format : String.format(format, args));
	}

	public UserFaultException(Throwable cause, String format, Object...args) {
		super(String.format(format, args), cause);
	}

	public UserFaultException(Throwable cause) {
		super(cause);
	}

	public UserFaultException(String message, Throwable cause) {
		super(message, cause);
	}

	public static void errorIf(boolean condition, String message) throws UserFaultException {
		if(condition) throw new UserFaultException(message);
	}
}
