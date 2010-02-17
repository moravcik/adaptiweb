package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Size {

	public static final Style MAX = new Style() {
		@Override
		public void apply(Element element) {
			element.getStyle().setWidth(100, Unit.PCT);
			element.getStyle().setHeight(100, Unit.PCT);
		}
	};

}
