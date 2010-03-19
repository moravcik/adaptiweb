package com.adaptiweb.gwt.widget;

import java.util.HashMap;
import java.util.LinkedList;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.mvc.model.DefaultStringModel;
import com.adaptiweb.gwt.mvc.model.StringModel;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.ListBox;

public class ListBoxComponent extends FormComponent implements StringModel  {
	
	protected final ListBox listBox = new ListBox();
	protected final StringModel model = new DefaultStringModel();
	private final HashMap<String, Integer> indexes = new HashMap<String, Integer>();
	private final LinkedList<ListBoxItem> values = new LinkedList<ListBoxItem>();
	private boolean unknownValue = false;
	private boolean enabledNull = false;
	private boolean isNull = false;
	
	public interface ListBoxItem {
		String value();
		String label();
	}
	
	public void addItem(ListBoxItem item) {
		String value = item.value();
		assert value != null : "Null item isn't allowed. Use method setNullLabel()";
		assert !indexes.containsKey(value) : "Duplicate item value " + value;
		indexes.put(value, values.size());
		values.add(item);
		listBox.insertItem(item.label(), value, listBox.getItemCount());
		
		String unknown = null;
		if (unknownValue && indexes.containsKey(unknown = listBox.getValue(0))) {
			int index = indexes.get(unknown);
			listBox.removeItem(0);
			unknownValue = false;
			if (unknown.equals(getText()))
				listBox.setSelectedIndex(enabledNull ? index + 1 : index);
		}
	}

	public ListBoxComponent() {
		initWidget(listBox);
		registrations.add(listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				model.setText(getWidgetValue());
			}
		}));
		registrations.add(model.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (!GwtGoodies.areEquals(getWidgetValue(), model.getText()))
					setWidgetValue(model.getText());
			}
		}));
		setWidgetValue(model.getText());
	}
	
	public void setNullLabel(String label) {
		if (enabledNull || isNull) listBox.setItemText(unknownValue ? 1 : 0, label);
		else listBox.insertItem(label, "", unknownValue ? 1 : 0);
		enabledNull = true;
	}
	
	private String getWidgetValue() {
		int index = listBox.getSelectedIndex();
		String value = listBox.getValue(index);
		return value.length() == 0 && index == (unknownValue ? 1 : 0) ? null : value;
	}
	
	private void setWidgetValue(String value) {
		if (value == null) {
			if (!enabledNull && !isNull) listBox.insertItem("", unknownValue ? 1 : 0);
			listBox.setSelectedIndex(unknownValue ? 1 : 0);
		}
		else if (indexes.containsKey(value)) {
			if (!enabledNull && isNull) listBox.removeItem(unknownValue ? 1 : 0);
			int index = indexes.get(value);
			if (unknownValue) index++;
			if (enabledNull) index++;
			listBox.setSelectedIndex(index);
		}
		else {
			if(unknownValue) {
				listBox.setItemText(0, value);
				listBox.setValue(0, value);
			}
			else {
				listBox.insertItem(value, 0);
				unknownValue = true;
			}
			listBox.setSelectedIndex(0);
		}
		isNull = value == null;
	}
	
	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
		return model.addValueChangeHandler(handler);
	}

	@Override
	public String getText() {
		return model.getText();
	}

	@Override
	public void setText(String text) {
		model.setText(text);
	}
	
	public boolean hasUnknownValue() {
		return unknownValue;
	}

	public ListBoxItem getItem() {
		String value = getWidgetValue();
		if (value == null || !indexes.containsKey(value)) return null;
		return values.get(indexes.get(value));
	}
}
