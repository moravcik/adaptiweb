package com.adaptiweb.utils.ci;

import com.adaptiweb.utils.commons.VariableResolver;
import com.adaptiweb.utils.commons.event.AbstractEvent;

public class PropertyFileChangedEvent extends AbstractEvent<PropertyFileChangedEventHandler, PropertyFileVariableSource> {

	private VariableResolver variables;

	public PropertyFileChangedEvent(PropertyFileVariableSource source, VariableResolver variables) {
		super(source);
		this.variables = variables;
	}
	
	public VariableResolver getVariables() {
		return variables;
	}

	@Override
	protected void dispatch(PropertyFileChangedEventHandler handler) {
		handler.onPropertyFileChanged(this);
	}

}
