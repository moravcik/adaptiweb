package com.adaptiweb.utils.csvbind;

public class CsvException extends IllegalArgumentException {

	private static final long serialVersionUID = -38084189128457506L;
	
	private String[] line;

	public CsvException() {
		super();
	}

	public CsvException(String message, Throwable cause, String[] line) {
		super(message, cause);
		this.line = line;
	}

	public CsvException(String message, Throwable cause) {
		super(message, cause);
	}

	public CsvException(String message, String[] line) {
		super(message);
		this.line = line;
	}

	public CsvException(Throwable cause, String[] line) {
		super(cause);
		this.line = line;
	}
	
	/**
	 * @return the line
	 */
	public String[] getLine() {
		return line;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(String[] line) {
		this.line = line;
	}

}
