package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Margin {

	public static final Style all(final int margin, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setMargin(margin, unit);
			}
		};
	}

	public static final Style top(final int marginTop, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setMarginTop(marginTop, unit);
			}
		};
	}

	public static final Style right(final int marginRight, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setMarginRight(marginRight, unit);
			}
		};
	}

	public static final Style bottom(final int marginBottom, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setMarginBottom(marginBottom, unit);
			}
		};
	}

	public static final Style left(final int marginLeft, final Unit unit) {
		return new Style() {
			public void apply(Element element) {
				element.getStyle().setMarginLeft(marginLeft, unit);
			}
		};
	}

}
