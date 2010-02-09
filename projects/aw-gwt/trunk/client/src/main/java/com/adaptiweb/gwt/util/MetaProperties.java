package com.adaptiweb.gwt.util;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

public class MetaProperties extends HashMap<String, String> {
	
	private MetaProperties(String prefix) {
		GWT.log("Creating MetaProperties with prefix=" + prefix , null);
		NodeList<Element> nodes = Document.get().getElementsByTagName("META");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element metaElement = nodes.getItem(i);
			String name = metaElement.getAttribute("name");
			if (name == null || !name.startsWith(prefix)) continue;
			name = name.substring(prefix.length());
			put(name, metaElement.getAttribute("content"));
		}
	}
	
	private static final HashMap<String,MetaProperties> instances = new HashMap<String, MetaProperties>();
	
	public static MetaProperties instance(String prefix) {
		if (!instances.containsKey(prefix)) instances.put(prefix, new MetaProperties(prefix));
		return instances.get(prefix);
	}
}
