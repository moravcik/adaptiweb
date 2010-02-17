package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Padding {

	public static final Style ZERO = new Style() {
		public void apply(Element element) {
			element.getStyle().setPadding(0, Unit.PX);
		}
	};
	
	public static final Style all(final int padding, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setPadding(padding, unit);
			}
		};
	}

	public static final Style top(final int paddingTop, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setPaddingTop(paddingTop, unit);
			}
		};
	}

	public static final Style right(final int paddingRight, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setPaddingRight(paddingRight, unit);
			}
		};
	}

	public static final Style bottom(final int paddingBottom, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setPaddingBottom(paddingBottom, unit);
			}
		};
	}

	public static final Style left(final int paddingLeft, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setPaddingLeft(paddingLeft, unit);
			}
		};
	}

}
