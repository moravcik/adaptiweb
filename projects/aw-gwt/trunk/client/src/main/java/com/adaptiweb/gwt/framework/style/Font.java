package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum Font implements Style {
	BOLD("font-weight"),
	ITALIC("font-style"),
	UNDERLINE("text-decoration");
	
	private final String style;

	Font(String stylePropertyName) {
		style = stylePropertyName; 
	}
	
	public void apply(Element element) {
		element.getStyle().setProperty(style, name().toLowerCase());
	}
}
