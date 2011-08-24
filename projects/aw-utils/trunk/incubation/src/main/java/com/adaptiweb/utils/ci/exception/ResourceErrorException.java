package com.adaptiweb.utils.ci.exception;

/**
 * Exception used when resource load exception occurs.
 * Usually wrap {@link java.io.IOException} when accessing resources.
 */
public class ResourceErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceErrorException(Throwable cause) {
		super(cause);
	}
	
	public ResourceErrorException(Throwable cause, String message, Object...objects) {
		super(objects.length == 0 ? message : String.format(message, objects), cause);
	}
	
}
