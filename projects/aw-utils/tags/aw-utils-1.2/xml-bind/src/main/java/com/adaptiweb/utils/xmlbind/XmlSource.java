package com.adaptiweb.utils.xmlbind;

public interface XmlSource {

	public boolean isComposite();

	public String getValue();

	public XmlSource[] getChildren();
	
	public String getName();
	
	public XmlSource getNext();
}
