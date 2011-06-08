package com.adaptiweb.gwt.tracker.impl;

import com.adaptiweb.gwt.common.tracker.CustomVariable;
import com.adaptiweb.gwt.tracker.Tracker;
import com.google.gwt.core.client.GWT;


public class LogTracker implements Tracker {

	@Override
	public void pageview(String page) {
		GWT.log("TRACKER pageview: " + page);
	}

	@Override
	public void event(String category, String action) {
		event(category, action, null, null);
	}
	
	@Override
	public void event(String category, String action, String optionalLabel) {
		event(category, action, optionalLabel, null);
	}
	
	@Override
	public void event(String category, String action, String optionalLabel, Integer optionalValue) {
		StringBuilder str = new StringBuilder("TRACKER event:")
			.append(" ").append("category=").append(category)
			.append(" ").append("action=").append(action);
		if (optionalLabel != null)
			str.append(" ").append("label=").append(optionalLabel);
		if (optionalValue != null)
			str.append(" ").append("value=").append(optionalValue);
		GWT.log(str.toString());
	}
	
	@Override
	public void setVariable(CustomVariable var) {
		GWT.log("TRACKER setVariable: slot=" + var.getSlot() + " name=" + var.getName() + " value=" + var.getValue() + " scope=" + var.getScope().name());
	}
	
	@Override
	public void deleteVariable(Integer slot) {
		GWT.log("TRACKER deleteVariable: slot=" + slot);
	}
	
	@Override
	public void ecommerce(Ecommerce record) {
		StringBuilder str = new StringBuilder("TRACKER ecommerce:")
			.append(" orderID=").append(record.orderId).append(" storeName=").append(record.storeName).append("\n")
			.append(" total=").append(record.total).append(" tax=").append(record.vat).append(" shipping=").append(record.shipping);
		GWT.log(str.toString());
		int itemIndex = 1;
		for (EcommerceItem item : record.items) {
			str = new StringBuilder("TRACKER ecommerce item: ").append((itemIndex++)).append(".")
				.append(" code").append(item.code).append(" productName=").append(item.productName).append(" category=").append(item.category)
				.append(" unitPrice=").append(item.unitPrice).append(" quantity=").append(item.quantity);
			GWT.log(str.toString());
		}
	}
	
}
