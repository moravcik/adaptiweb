package com.adaptiweb.gwt.util;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;

public class MetaProperties extends HashMap<String, String> {

	private MetaProperties(String prefix) {
		GWT.log("Creating MetaProperties with prefix=" + prefix , null);
		NodeList<Element> nodes = Document.get().getElementsByTagName("META");
		for (int i = 0; i < nodes.getLength(); i++) {
			MetaElement metaElement = MetaElement.as(nodes.getItem(i));
			if (!metaElement.getName().startsWith(prefix)) continue;
			put(metaElement.getName().substring(prefix.length()), metaElement.getContent());
		}
	}

	private static final HashMap<String,MetaProperties> instances = new HashMap<String, MetaProperties>();

	public static MetaProperties instance(String prefix) {
		if (!instances.containsKey(prefix)) instances.put(prefix, new MetaProperties(prefix));
		return instances.get(prefix);
	}

	/**
	 * @param propertyName
	 * @param expectedValue
	 * @return <code>true</code> if property exists and has expected value
	 */
	public boolean check(String propertyName, String expectedValue) {
		return containsKey(propertyName) && expectedValue.equals(get(propertyName));
	}

	public String get(String key, String defaultValue) {
		return containsKey(key) ? get(key) : defaultValue;
	}
}
