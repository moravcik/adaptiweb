package com.adaptiweb.gwt.framework.style;


import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

public enum Styles implements DynamicStyle {
	
	INVALID {
		public void apply(Element element) {
			StyleNameManager.addStyleName(element, toString());
		}
		public void cancel(Element element) {
			StyleNameManager.removeStyleName(element, toString());
		}
	};
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
}

class StyleNameManager extends UIObject {
	public static void addStyleName(Element element, String styleName) {
		setStyleName(element, styleName, true);
	}
	
	public static void removeStyleName(Element element, String styleName) {
		setStyleName(element, styleName, false);
	}
}
