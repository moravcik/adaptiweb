package com.adaptiweb.gwt.framework.modify;

import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.user.client.ui.HasValue;

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
}
