package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.UIObject;

public enum VisualStyle implements DynamicStyle {

	visible {
		public void apply(Element element) {
			UIObject.setVisible(element, true);
		}
		public void cancel(Element element) {
			UIObject.setVisible(element, false);
		}
	},
	enabled {
		public void apply(Element element) {
			element.removeAttribute("disabled");
		}
		public void cancel(Element element) {
			element.setAttribute("disabled", "disabled");
		}
	}
	;
}
