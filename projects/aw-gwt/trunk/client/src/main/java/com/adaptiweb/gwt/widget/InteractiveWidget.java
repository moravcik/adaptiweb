package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.util.ConcatUtils;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;


public class InteractiveWidget extends FireWidget {

	private String enterStyle;
	private String leaveStyle;
	private String highlightedStyle;
	private String standardStyle;
	private boolean over;
	private boolean focus;

	public InteractiveWidget(Element element, boolean focusable) {
		super(element, focusable);
	}

	@Override
	public void setStyleName(String style) {
		this.standardStyle = style;
		super.setStyleName(style);
	}
	
	public void setHoverStyle(String enterStyle) {
		setHoverStyle(enterStyle, null);
	}
	
	/**
	 * @param enterStyle style applicable if widget has focus or mouse over it. 
	 * @param leaveStyle style applicable if enterStyle is not, may be {@code null}.
	 */
	public void setHoverStyle(String enterStyle, String leaveStyle) {
		this.enterStyle = enterStyle;
		this.leaveStyle = leaveStyle;
		updateStyle();
		sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.FOCUSEVENTS);
	}
	
	public void setHighlightedStyle(String highlightedStyle) {
		this.highlightedStyle = highlightedStyle;
		updateStyle();
	}
	
	public void setHighlightedStyle(String highlightedStyle, String standardStyle) {
		this.highlightedStyle = highlightedStyle;
		this.standardStyle = standardStyle;
		updateStyle();
	}

	@Override
	protected void onMouseOver(Event event) {
		over = true;
		updateStyle();
		super.onMouseOver(event);
	}
	
	@Override
	protected void onMouseOut(Event event) {
		over = false;
		updateStyle();
		super.onMouseOut(event);
	}
	
	@Override
	protected void onFocus(Event event) {
		focus = true;
		super.onFocus(event);
	}
	
	@Override
	protected void onBlur(Event event) {
		focus = false;
		updateStyle();
		super.onBlur(event);
	}
	
	protected void updateStyle() {
		if(enterStyle != null || highlightedStyle != null) {
			String majorStyle = highlightedStyle != null && isHighlighted() ? highlightedStyle : standardStyle;
			String minorStyle = enterStyle != null && isEntered() ? enterStyle : leaveStyle;
			super.setStyleName(ConcatUtils.concat(" ", majorStyle, minorStyle));
		}
	}
	
	protected boolean isEntered() {
		return over || focus;
	}

	protected boolean isHighlighted() {
		return false;
	}

}
