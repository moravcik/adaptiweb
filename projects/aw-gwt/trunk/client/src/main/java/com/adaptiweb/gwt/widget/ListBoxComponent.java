package com.adaptiweb.gwt.widget;

import java.util.HashMap;
import java.util.LinkedList;

import com.adaptiweb.gwt.common.ListBoxItem;
import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.validation.DummyValidation;
import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;
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
	private final LinkedList<ListBoxItem> items = new LinkedList<ListBoxItem>();
	private DefaultNumberModel<Integer> sizeModel;
	private DummyValidation validation;

	private boolean unknownValue = false;
	private boolean enabledNull = false;
	private boolean isNull = false;

	public interface ListBoxItemFactory<I> {
		ListBoxItem asListBoxItem(I item);
	}

	public void clear() {
		setText(null);
		autoRemoveUnknownItem();
		indexes.clear();
		items.clear();
		while(listBox.getItemCount() > 1) listBox.removeItem(1);
		updateModels();
	}

	private void autoRemoveUnknownItem() {
		if (unknownValue && !listBox.getValue(0).equals(model.getValue())) {
			listBox.removeItem(0);
			unknownValue = false;
		}
	}

	private void updateModels() {
		if (sizeModel != null) sizeModel.setValue(items.size());
		if (validation != null) validation.setValid(!hasUnknownValue() && getText() != null || items.isEmpty());
	}

	public <I> void addItems(ListBoxItemFactory<I> factory, I...items) {
		for (I item : items) add(factory.asListBoxItem(item));
		updateModels();
	}

	public void addItems(String...items) {
		final String[] holder = new String[1];
		ListBoxItem accessor = new ListBoxItem() {
			public String value() { return holder[0]; }
			public String label() { return holder[0]; }
		};
		for (String item : items) {
			holder[0] = item;
			add(accessor);
		}
		updateModels();
	}

	public void addItems(ListBoxItem...items) {
		for (ListBoxItem item : items) add(item);
		updateModels();
	}

	private void add(ListBoxItem item) {
		String value = item.value();
		assert value != null : "Null item isn't allowed. Use method setNullLabel()";
		if (indexes.containsKey(value)) return;
		indexes.put(value, items.size());
		items.add(item);
		listBox.insertItem(item.label(), value, listBox.getItemCount());

		String unknown = null;
		if (unknownValue && indexes.containsKey(unknown = listBox.getValue(0))) {
			int index = indexes.get(unknown);
			listBox.removeItem(0);
			unknownValue = false;
			if (unknown.equals(getText()))
				listBox.setSelectedIndex(enabledNull ? index + 1 : index);
		}

		updateModels();
	}

	public ListBoxComponent() {
		initWidget(listBox);
		registrations.add(listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				setText(getWidgetValue());
				autoRemoveUnknownItem();
			}
		}));
		registrations.add(model.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if (!GwtGoodies.areEquals(getWidgetValue(), model.getText()))
					setWidgetValue(model.getText());
				updateModels();
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
			autoRemoveUnknownItem();
		}
		else if (indexes.containsKey(value)) {
			if (!enabledNull && isNull) listBox.removeItem(unknownValue ? 1 : 0);
			int index = indexes.get(value);
			if (unknownValue) index++;
			if (enabledNull) index++;
			listBox.setSelectedIndex(index);
			autoRemoveUnknownItem();
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

	public HandlerRegistration addSizeChangeHandlerAndInit(ValueChangeHandler<Integer> handler) {
		if (sizeModel == null) {
			sizeModel = new DefaultNumberModel<Integer>();
			updateModels();
		}
		return sizeModel.addValueChangeHandlerAndInit(handler);
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
		return items.get(indexes.get(value));
	}

	public boolean hasItems() {
		return !items.isEmpty();
	}
	
	public void focus() {
		listBox.setFocus(true);
	}

	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public void setValue(String value, boolean fireEvent) {
		model.setValue(value, fireEvent);
	}

	public LogicModel createStandardValidation() {
		if (validation == null) {
			validation = new DummyValidation();
			updateModels();
		}
		return validation;
	}

	public void setEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
	}

	public boolean isEnabled() {
		return listBox.isEnabled();
	}
}
