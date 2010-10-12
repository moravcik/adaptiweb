package com.adaptiweb.gwt.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

public class FieldSetPanel extends ComplexPanel {

	public FieldSetPanel(String legend, boolean asHtml) {
		setElement(DOM.createFieldSet());
		if(legend != null) {
			Element l = DOM.createLegend();
			
			if(asHtml) l.setInnerHTML(legend);
			else l.setInnerText(legend);
			
			DOM.appendChild(getElement(), l);
		}
	}

	public FieldSetPanel(String label) {
		this(label, false);
	}
	
	@Override
	public void add(Widget child) {
		add(child, getElement());
	}
}
