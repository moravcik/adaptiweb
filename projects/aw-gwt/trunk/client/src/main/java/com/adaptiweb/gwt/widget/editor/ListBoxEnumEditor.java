package com.adaptiweb.gwt.widget.editor;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.adaptiweb.gwt.common.ListBoxItem;
import com.adaptiweb.gwt.common.api.HasLabel;
import com.adaptiweb.gwt.widget.EnumRenderer;
import com.google.gwt.editor.client.LeafValueEditor;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ValueListBox;

/**
 * Check {@link ValueListBox} with {@link EnumRenderer} as alternative 
 */
public class ListBoxEnumEditor<E extends Enum<E>> extends ListBox implements LeafValueEditor<E> {

	private List<E> enumValues = new ArrayList<E>();
	
	@UiConstructor
	@SuppressWarnings("unchecked")
    public ListBoxEnumEditor(String nullLabel, EnumSet<?> enumSet) {
		addItem(nullLabel, Direction.DEFAULT, null);
		setEnumValues((EnumSet<E>) enumSet);
    }
	
	@UiChild(limit=1, tagname="enumvalues")
	public void setEnumValues(EnumSet<E> values) {
		for (E value : values) {
			addEnumValue(value);
		}
	}

	@UiChild(tagname="enumvalue")
	public void addEnumValue(E value) {
		String label = value.name();
		if (value instanceof HasLabel) {
			label = ((HasLabel) value).getLabel();
		}
		if (value instanceof ListBoxItem) {
			label = ((ListBoxItem) value).label();
		}
		addItem(label, value.name());
		enumValues.add(value);
	}    

    @Override
    public void setValue(E value) {
        if (value == null) setSelectedIndex(0); // nullSelectLabel
        else setSelectedIndex(enumValues.indexOf(value) + 1); 
    }

    @Override
    public E getValue() {
    	int valueIndex = getSelectedIndex() - 1;
    	return valueIndex < 0 ? null : enumValues.get(valueIndex);
    }
}