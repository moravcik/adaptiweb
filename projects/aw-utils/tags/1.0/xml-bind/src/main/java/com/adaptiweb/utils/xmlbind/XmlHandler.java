package com.adaptiweb.utils.xmlbind;

import java.util.Map;

public interface XmlHandler {

	public void ignoreableWhitespaces(String content);

	public void contentString(String content);

	public void beginElement(String tagName, Map<String, String> attrs);

	public void endElement(String tagName);

	public void processInstruction(String name, Map<String, String> attrs);
	
	public Object done();
}
