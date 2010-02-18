package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
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
	
	public static Style size(final int size, final Unit unit) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setFontSize(size, unit);
			}
		};
	}
}
