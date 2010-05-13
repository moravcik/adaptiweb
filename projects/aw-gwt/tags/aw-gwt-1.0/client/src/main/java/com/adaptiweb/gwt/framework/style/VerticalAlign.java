package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum VerticalAlign implements Style {
	BASELINE, SUB, SUPER, TOP, TEXT_TOP, MIDDLE, BOTTOM, TEXT_BOTTOM;

	@Override
	public void apply(Element element) {
		element.getStyle().setProperty("verticalAlign", name().toLowerCase().replace('_', '-'));
	}

}
