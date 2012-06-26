package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.common.ListBoxItem;
import com.adaptiweb.gwt.common.api.HasLabel;
import com.google.gwt.text.shared.AbstractRenderer;

/**
 * source: http://stackoverflow.com/a/4931399/1167517
 */
public class EnumRenderer<E extends Enum<E>> extends AbstractRenderer<E> {

	   private String emptyValue = "";

	   @Override
	   public String render(E object) {
	       if (object == null) {
	           return emptyValue;
	       } else if (object instanceof HasLabel) {
	    	   return ((HasLabel) object).getLabel();
	       } else if (object instanceof ListBoxItem) {
	    	   return ((ListBoxItem) object).label();
	       } else return object.toString();
	   }

	   public void setEmptyValue(String emptyValue) {
	       this.emptyValue = emptyValue;
	   }
	   
}
