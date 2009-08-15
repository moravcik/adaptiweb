package com.adaptiweb.gwt.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.OptGroupElement;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.RootPanel;

public class AwListBox extends FocusWidget implements HasChangeHandlers, HasName {

	private static final int LAST_POSITION = -1;

	public static AwListBox wrap(Element element) {
		assert Document.get().getBody().isOrHasChild(element);

		AwListBox listBox = new AwListBox(element);

		listBox.onAttach();
		RootPanel.detachOnWindowClose(listBox);

		return listBox;
	}

	public AwListBox() {
		this(false);
	}

	public AwListBox(boolean isMultipleSelect) {
		super(Document.get().createSelectElement(isMultipleSelect));
		setStyleName("gwt-ListBox");
	}

	protected AwListBox(Element element) {
		super(element);
		SelectElement.as(element);
	}

	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}
	
	public void addGroup(String name) {
		OptGroupElement group = Document.get().createOptGroupElement();
		group.setLabel(name);
		insertChild(group, LAST_POSITION);
	}

	public void addItem(String item) {
		insertItem(item, LAST_POSITION);
	}

	public void addItem(String item, String value) {
		insertItem(item, value, LAST_POSITION);
	}

	public void clear() {
		getSelectElement().clear();
	}

	public int getItemCount() {
		return getSelectElement().getOptions().getLength();
	}

	public String getItemText(int index) {
		checkIndex(index);
		return getSelectElement().getOptions().getItem(index).getText();
	}

	public String getName() {
		return getSelectElement().getName();
	}

	public int getSelectedIndex() {
		return getSelectElement().getSelectedIndex();
	}

	public String getValue(int index) {
		checkIndex(index);
		return getSelectElement().getOptions().getItem(index).getValue();
	}

	public int getVisibleItemCount() {
		return getSelectElement().getSize();
	}

	public void insertItem(String item, int index) {
		insertItem(item, item, index);
	}

	public void insertItem(String item, String value, int index) {
		OptionElement option = Document.get().createOptionElement();
		option.setText(item);
		option.setValue(value);
		insertChild(option, index);
	}
	
	private void insertChild(Element element, int index) {
		assert element instanceof OptionElement || element instanceof OptGroupElement;
		SelectElement select = getSelectElement();
		
		if ((index == -1) || (index == select.getLength())) {
			select.insertBefore(element, null);
		}
		else if (checkIndex(index)) {
			OptionElement before = select.getOptions().getItem(index);
			select.insertBefore(element, before);
		}
	}

	public boolean isItemSelected(int index) {
		checkIndex(index);
		return getSelectElement().getOptions().getItem(index).isSelected();
	}

	public boolean isMultipleSelect() {
		return getSelectElement().isMultiple();
	}

	public void removeItem(int index) {
		checkIndex(index);
		getSelectElement().remove(index);
	}

	public void setItemSelected(int index, boolean selected) {
		checkIndex(index);
		getSelectElement().getOptions().getItem(index).setSelected(selected);
	}

	public void setItemText(int index, String text) {
		checkIndex(index);
		if (text == null) {
			throw new NullPointerException("Cannot set an option to have null text");
		}
		getSelectElement().getOptions().getItem(index).setText(text);
	}

	public void setName(String name) {
		getSelectElement().setName(name);
	}

	public void setSelectedIndex(int index) {
		getSelectElement().setSelectedIndex(index);
	}

	public void setValue(int index, String value) {
		checkIndex(index);
		getSelectElement().getOptions().getItem(index).setValue(value);
	}

	public void setVisibleItemCount(int visibleItems) {
		getSelectElement().setSize(visibleItems);
	}

	@Override
	protected void onEnsureDebugId(String baseID) {
		super.onEnsureDebugId(baseID);

		// Set the id of each option
		int numItems = getItemCount();
		for (int i = 0; i < numItems; i++) {
			ensureDebugId(getSelectElement().getOptions().getItem(i), baseID, "item" + i);
		}
	}

	private boolean checkIndex(int index) {
		if (index < 0 || index >= getItemCount()) {
			throw new IndexOutOfBoundsException(index + " is not in [" + 0 + "," + getItemCount() + "]");
		}
		return true;
	}

	private SelectElement getSelectElement() {
		return getElement().cast();
	}
}
