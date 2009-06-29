package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum Font implements Style {
	BOLD("fontWeight"),
	ITALIC("fontStyle"),
	UNDERLINE("textDecoration");
	
	private final String style;

	Font(String stylePropertyName) {
		style = stylePropertyName; 
	}
	
	public void apply(Element element) {
		element.getStyle().setProperty(style, name().toLowerCase());
	}
}
