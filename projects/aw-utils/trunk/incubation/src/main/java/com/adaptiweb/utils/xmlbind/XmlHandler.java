package com.adaptiweb.utils.xmlbind;

import java.util.Map;

public interface XmlHandler {

	void ignoreableWhitespaces(String content);

	void contentString(String content);

	void cdataString(String cdata);
	
	void beginElement(String tagName, Map<String, String> attrs);

	void endElement(String tagName);

	void processInstruction(String name, Map<String, String> attrs);
	
	Object done();

}
