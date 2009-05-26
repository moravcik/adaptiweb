package com.adaptiweb.utils.xmlbind;

import java.io.IOException;

class XmlParserException extends IOException {
	
	private static final long serialVersionUID = 1L;
	
	public int lineNumber;
	public int columnNumber;
	public String fileName;

	XmlParserException(String format, Object...params) {
		super(params.length == 0 ? format : String.format(format, params));
	}

	@Override
	public String getMessage() {
		return super.getMessage() + String.format(" on (%d:%d))", lineNumber, columnNumber);
	}
};
