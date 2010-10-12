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
import com.google.gwt.user.client.ui.Composite;


public abstract class InteractiveWidget extends Composite {
	
	private String enterStyle;
	private String leaveStyle;
	private String highlightedStyle;
	private String standardStyle;
	private boolean over;
	private boolean focus;

	// dont forget to call initWidget in constructor
	public InteractiveWidget(boolean focusable) {		
		addDomHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				over = true;
				updateStyle();
			}
		}, MouseOverEvent.getType());
		addDomHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				over = false;
				updateStyle();
			}
		}, MouseOutEvent.getType());
		if (focusable) {
			addDomHandler(new FocusHandler() {
				public void onFocus(FocusEvent event) {
					focus = true;
					updateStyle();
				}
			}, FocusEvent.getType());
			addDomHandler(new BlurHandler() {
				public void onBlur(BlurEvent event) {
					focus = true;
					updateStyle();
				}
			}, BlurEvent.getType());
		}
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
			getWidget().setStyleName(ConcatUtils.concat(" ", majorStyle, minorStyle));
		}
	}
	
	protected boolean isEntered() {
		return over || focus;
	}

	protected boolean isHighlighted() {
		return false;
	}
	
	public void addMouseOverHandler(MouseOverHandler handler) {
		addDomHandler(handler, MouseOverEvent.getType());
	}
	
	public void addMouseOutHandler(MouseOutHandler handler) {
		addDomHandler(handler, MouseOutEvent.getType());
	}

}
