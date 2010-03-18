package com.adaptiweb.gwt.framework.style;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;

public class Border {
	
	public static final Style SOLID_SILVER = border(1, Unit.PX, BorderStyle.SOLID, Color.Silver);
	
	public static final Style STYLE_SOLID = style(BorderStyle.SOLID);
	public static final Style STYLE_DOTTED = style(BorderStyle.DOTTED);
	public static final Style STYLE_DASHED = style(BorderStyle.DASHED);
	public static final Style STYLE_NONE = style(BorderStyle.NONE);
	public static final Style STYLE_HIDDEN = style(BorderStyle.HIDDEN);
	
	public static final Style width(final int width, final Unit unit) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setBorderWidth(width, unit);
			}
		};
	}

	public static final Style width(final String width) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setProperty("borderWidth", width);
			}
		};
	}

	public static final Style style(final BorderStyle style) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setBorderStyle(style);
			}
		};
	}

	public static final Style color(final ColorType color) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setBorderColor(color.colorValue());
			}
		};
	}
	
	public static final Style border(final int width, final Unit unit, final BorderStyle style, final ColorType color) {
		return new Style() {
			@Override
			public void apply(Element element) {
				element.getStyle().setBorderWidth(width, unit);
				element.getStyle().setBorderStyle(style);
				element.getStyle().setBorderColor(color.colorValue());
			}
		};
	}
	

}
