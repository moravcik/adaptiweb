package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Width {

	public static final Style MAX = new Style() {
		@Override
		public void apply(Element element) {
			element.getStyle().setWidth(100, Unit.PCT);
		}
	};

	public static final Style exact(final int width, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setWidth(width, unit);
			}
		};
	}

}
