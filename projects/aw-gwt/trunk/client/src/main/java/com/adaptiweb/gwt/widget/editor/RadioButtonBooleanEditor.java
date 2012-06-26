package com.adaptiweb.gwt.widget.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;

public class RadioButtonBooleanEditor extends FlowPanel implements LeafValueEditor<Boolean> {

	Map<Boolean, RadioItem> map;
	
	private static class RadioItem {
		RadioButton button;
		SafeHtml label;
	}
	
	@UiConstructor
	public RadioButtonBooleanEditor(String groupName, SafeHtml trueLabel, SafeHtml falseLabel) {
		this.map = new HashMap<Boolean, RadioItem>();
		addButton(groupName, trueLabel, true);
		addButton(groupName, falseLabel, false);
	}
	
	private void addButton(String groupName, SafeHtml label, Boolean value) {
		RadioItem item = new RadioItem();
		item.button = new RadioButton(groupName, label);
		item.label = label;
		this.map.put(value, item);
		this.add(item.button);
	}
	
	@Override
	public void setValue(Boolean value) {
		if (value != null) {
			map.get(value).button.setValue(true);
		} else clearSelection();
	}
	
	private void clearSelection() {
		for (RadioItem item : map.values()) {
			item.button.setValue(false);
		}
	}

	@Override
	public Boolean getValue() {
		Entry<Boolean, RadioItem> entry = findSelectedEntry();
		return entry != null ? entry.getKey() : null;
	}
	
	private Entry<Boolean, RadioItem> findSelectedEntry() {
		for (Entry<Boolean, RadioItem> entry : map.entrySet()) {
			boolean isSelected = entry.getValue().button.getValue();
			if (isSelected) return entry;
		}
		return null;
	}
	
	public SafeHtml getLabel() {
		Entry<Boolean, RadioItem> entry = findSelectedEntry();
		return entry != null ? entry.getValue().label : null;
	}

}
