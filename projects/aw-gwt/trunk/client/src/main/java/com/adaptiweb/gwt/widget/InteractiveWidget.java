package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.util.ConcatUtils;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;


public class InteractiveWidget extends DynamicWidget {

	private String enterStyle;
	private String leaveStyle;
	private String highlightedStyle;
	private String standardStyle;
	private boolean over;
	private boolean focus;

	public InteractiveWidget(Element element, boolean focusable) {
		setElement(element);
		
		addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				over = true;
				updateStyle();
			}
		});
		addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				over = false;
				updateStyle();
			}
		});
		addFocusHandler(new FocusHandler() {
			public void onFocus(FocusEvent event) {
				focus = true;
				updateStyle();
			}
		});
		addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				focus = true;
				updateStyle();
			}
		});
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
