package com.adaptiweb.gwt.tracker;

import java.util.ArrayList;
import java.util.List;

import com.adaptiweb.gwt.common.tracker.CustomVariable;


public interface Tracker {
	
	void pageview(String page);

	void event(String category, String action);

	void event(String category, String action, String optionalLabel);

	void event(String category, String action, String optionalLabel, Integer optionalValue);

	void setVariable(CustomVariable var);
	
	void deleteVariable(Integer slot);
	
	void ecommerce(Ecommerce record);
	
	public static class Ecommerce {
		public String orderId; // required
		public String storeName;
		public Double total; // required
		public Double vat;
		public Double shipping;
		public String city;
		public String state;
		public String country;
		public List<EcommerceItem> items = new ArrayList<Tracker.EcommerceItem>();
		
		public static Ecommerce create(String orderId, String storeName, Double total) {
			Ecommerce record = new Ecommerce();
			record.orderId = orderId;
			record.storeName = storeName;
			record.total = total;
			return record;
		}
		
		public Ecommerce setVat(Double vat) {
			this.vat = vat;
			return this;
		}
		
		public Ecommerce setShipping(Double shipping) {
			this.shipping = shipping;
			return this;
		}
		
		public Ecommerce setLocation(String city, String state, String country) {
			this.city = city;
			this.state = state;
			this.country = country;
			return this;
		}
		
		public Ecommerce addItem(String code, String productName, String category, Double unitPrice, Double quantity) {
			EcommerceItem item = new EcommerceItem();
			item.orderId = this.orderId;
			item.code = code;
			item.productName = productName;
			item.category = category;
			item.unitPrice = unitPrice;
			item.quantity = quantity;
			this.items.add(item);
			return this;
		}
	}
	
	public static class EcommerceItem {
		public String orderId; // required
		public String code;
		public String productName;
		public String category;
		public Double unitPrice; // required
		public Double quantity;  // required
	}

}
