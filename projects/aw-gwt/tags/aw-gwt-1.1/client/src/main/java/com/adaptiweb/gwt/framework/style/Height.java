package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Height {

	public static final Style MAX = new Style() {
		@Override
		public void apply(Element element) {
			element.getStyle().setHeight(100, Unit.PCT);
		}
	};

	public static final Style exact(final int height, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setHeight(height, unit);
			}
		};
	}

}
