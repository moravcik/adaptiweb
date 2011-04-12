package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

public enum Rotation implements Style {
	PLUS_90("90deg", 1),
	PLUS_180("180deg", 2),
	MINUS_90("-90deg", 3);
	
	String rotate;
	int rotation;
	
	private Rotation(String rotate, int rotation) {
		this.rotate = rotate;
		this.rotation = rotation;
	}

	public void apply(Element element) {
		element.getStyle().setProperty("MozTransform", "rotate(" + rotate + ")");
		element.getStyle().setProperty("WebkitTransform", "rotate(" + rotate + ")");
		element.getStyle().setProperty("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + rotation + ")");
	}
}
