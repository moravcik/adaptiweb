package com.adaptiweb.gwt.mvc.model;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import com.adaptiweb.gwt.mvc.ChangeEvent;
import com.adaptiweb.gwt.mvc.ChangeListener;
import com.adaptiweb.gwt.mvc.Listeners;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioGroup<T> extends Listeners<ChangeListener<T>> implements ValueChangeHandler<Boolean> {
	
	private final Map<RadioButton, T> values = new IdentityHashMap<RadioButton, T>();
	private final Map<T, RadioButton> buttons = new HashMap<T, RadioButton>();
	private RadioButton selected;
	private final String groupName;
	
	public RadioGroup(String groupName) {
		this.groupName = groupName;
	}
	
	public RadioButton createRadioButton(T value) {
		return createRadioButton(value, value.toString());
	}

	public RadioButton createRadioButton(T value, String label) {
		return createRadioButton(value, label, false);
	}
	
	public RadioButton createRadioButton(T value, String label, boolean asHtml) {
		RadioButton rb = new RadioButton(groupName, label, asHtml);
		values.put(rb, value);
		buttons.put(value, rb);
		rb.addValueChangeHandler(this);
		return rb;
	}
	
	public RadioButton getRadioButton(T value) {
		return buttons.get(value);
	}

	public void onValueChange(ValueChangeEvent<Boolean> event) {
		if (values.containsKey(event.getSource()) && event.getValue())
			setValue(values.get(event.getSource()));
	}

	public void setValue(T value) {
		if (value == null) {
			if (selected != null) {
				selected.setValue(false, false);
				selected = null;
			}
			return;
		}
		if (selected != buttons.get(value)) {
			selected = buttons.get(value);
			selected.setValue(true, false);
			fireEvent(new ChangeEvent<T>(values.get(selected)));
		}
	}
	
	public T getValue() {
		return values.get(selected);
	}

}
