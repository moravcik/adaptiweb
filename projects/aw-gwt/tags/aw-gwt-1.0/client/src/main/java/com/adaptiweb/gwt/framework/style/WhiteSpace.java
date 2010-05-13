package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum WhiteSpace implements Style {
	NORMAL, PRE, NOWRAP;
	
	public void apply(Element element) {
		element.getStyle().setProperty("whiteSpace", name().toLowerCase());
	}
}
