package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class ModifiedModelGroup extends ModifiedModelFactory.AbstractLogicModifiedModelSet {

	public ModifiedModelGroup() {
		super("group(or)");
	}

	@Override
	protected boolean eval() {
		return modifiedCounter > 0;
	}
	
	private <T> ConfigureableModifiedModel<T> add(ConfigureableModifiedModel<T> model) {
		super.add(model);
		return model;
	}
	
	public <T> ConfigureableModifiedModel<T> add(HasValue<T> hasValue) {
		return add(ModifiedModelFactory.create(hasValue));
	}

	public <T extends Number> ConfigureableModifiedModel<T> add(NumberModel<T> numberModel) {
		return add(ModifiedModelFactory.create(numberModel));
	}

	public ConfigureableModifiedModel<Integer> add(final ListBox lb) {
		AbstractHasCahangeHandlersModifiedModel<Integer> mm = new AbstractHasCahangeHandlersModifiedModel<Integer>(lb) {
			@Override
			protected Integer getCurrentValue() {
				int selectedIndex = lb.getSelectedIndex();
				return selectedIndex == -1 ? null : selectedIndex;
			}
		};
		return add(mm);
	}
}
