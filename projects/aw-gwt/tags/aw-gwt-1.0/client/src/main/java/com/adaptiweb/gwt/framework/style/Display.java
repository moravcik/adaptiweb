package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum Display implements Style {
	NONE,
	DEFAULT("");
	
	private final String value;

	private Display() {
		this(null);
	}

	private Display(String value) {
		this.value = value == null ? name().toLowerCase() : value;
	}

	@Override
	public void apply(Element element) {
		element.getStyle().setProperty("display", value);
	}
}
