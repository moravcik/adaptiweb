package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public enum Font implements DynamicStyle {
	NORMAL("fontWeight"),
	BOLD("fontWeight"),
	ITALIC("fontStyle"),
	LINE_THROUGH("textDecoration"), // we need mapping from "_" to "-" to apply style
	UNDERLINE("textDecoration");
	
	private final String style;

	Font(String stylePropertyName) {
		style = stylePropertyName; 
	}
	
	public void apply(Element element) {
		element.getStyle().setProperty(style, name().replaceAll("_", "-").toLowerCase());
	}

	@Override
	public void cancel(Element element) {
		element.getStyle().setProperty(style, "");
	}

	public static Style size(final double size, final Unit unit) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setFontSize(size, unit);
			}
		};
	}

}
