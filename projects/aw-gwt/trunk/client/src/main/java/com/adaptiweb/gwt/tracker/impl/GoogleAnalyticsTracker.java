package com.adaptiweb.gwt.tracker.impl;

import com.adaptiweb.gwt.common.tracker.CustomVariable;
import com.google.gwt.i18n.client.NumberFormat;


public class GoogleAnalyticsTracker extends LogTracker {

	private NumberFormat doubleFormat;
	
	public GoogleAnalyticsTracker() {
		NumberFormat.setForcedLatinDigits(true);
		doubleFormat = NumberFormat.getFormat("#.##");
		NumberFormat.setForcedLatinDigits(false);
	}
	
	@Override
	public void pageview(String page) {
		super.pageview(page);
		trackPageview(page);
	}
	
	native void trackPageview(String page) /*-{
		$wnd._gaq.push(['_trackPageview', page]);
	}-*/;

	@Override
	public void event(String category, String action, String optionalLabel, Integer optionalValue) {
		super.event(category, action, optionalLabel, optionalValue);
		if (optionalValue != null) {
			trackEvent(category, action, optionalLabel, optionalValue);
		} else {
			trackEvent(category, action, optionalLabel);
		}
	}

	native void trackEvent(String category, String action, String optLabel) /*-{
		$wnd._gaq.push(['_trackEvent', category, action, optLabel]);
	}-*/;

	native void trackEvent(String category, String action, String optLabel, int optValue) /*-{
		$wnd._gaq.push(['_trackEvent', category, action, optLabel, optValue]);
	}-*/;
	
	@Override
	public void setVariable(CustomVariable var) {
		super.setVariable(var);
		trackSetVariable(var.getSlot(), var.getName(), var.getValue(), var.getScope().getValue());
	}
	
	native void trackSetVariable(Integer slot, String name, String value, int scope) /*-{
		$wnd._gaq.push(['_setCustomVar', slot, name, value, scope]);
	}-*/;
	
	@Override
	public void deleteVariable(Integer slot) {
		super.deleteVariable(slot);
		trackDeleteVariable(slot);
	}
	
	native void trackDeleteVariable(Integer slot) /*-{
		$wnd._gaq.push(['_deleteCustomVar', slot]);
	}-*/;
	
	@Override
	public void ecommerce(Ecommerce record) {
		super.ecommerce(record);
		trackAddTrans(record.orderId, record.storeName, convertDouble(record.total), convertDouble(record.vat), convertDouble(record.shipping), record.city, record.state, record.country);
		for (EcommerceItem item : record.items) {
			trackAddItem(item.orderId, item.code, item.productName, item.category, convertDouble(item.unitPrice), convertDouble(item.quantity));
		}
		trackTrans();
	}
	
	native void trackAddTrans(String orderId, String storeName, String total, String tax, String shipping, String city, String state, String country) /*-{
		$wnd._gaq.push(['_addTrans', orderId, storeName, total, tax, shipping, city, state, country]);
	}-*/;

	native void trackAddItem(String orderId, String code, String productName, String category, String unitPrice, String quantity) /*-{
		$wnd._gaq.push(['_addItem', orderId, code, productName, category, unitPrice, quantity]);
	}-*/;
	
	native void trackTrans() /*-{
		$wnd._gaq.push(['_trackTrans']);
	}-*/;
	
	private String convertDouble(Double value) {
		return value != null ? doubleFormat.format(value) : "0";
	}
	
}
