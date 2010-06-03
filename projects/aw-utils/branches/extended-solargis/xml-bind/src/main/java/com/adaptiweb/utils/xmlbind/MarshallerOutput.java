package com.adaptiweb.utils.xmlbind;

import java.io.IOException;

public interface MarshallerOutput {

	public abstract void startElement(String element) throws IOException;

	public abstract void addAttribute(String name, String value)
			throws ParserException, IOException;

	public abstract void stopElement() throws IOException;

	public abstract void addText(String text) throws IOException;

	public abstract void flush() throws IOException;

	public abstract String getCurentTagName();

}