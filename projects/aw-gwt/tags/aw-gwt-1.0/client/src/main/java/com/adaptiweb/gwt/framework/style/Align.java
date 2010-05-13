package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum Align implements Style {
	LEFT, CENTER, RIGHT;
	
	public void apply(Element element) {
		element.setAttribute("align", name().toLowerCase());
	}
}
