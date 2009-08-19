package com.adaptiweb.gwt.framework.logic;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.google.gwt.event.shared.HandlerRegistration;

public abstract class AbstractLogicModelSet extends AbstractLogicModel
implements LogicModelSet, LogicValueChangeHandler, HasDebugInfo {
	
	private final String type;
	private final Map<LogicModel, HandlerRegistration> models
		= new IdentityHashMap<LogicModel, HandlerRegistration>();
	
	public AbstractLogicModelSet(String type, LogicModel...models) {
		this.type = type;
		for (LogicModel model : models) add(model);
	}

	protected abstract boolean eval();
	
	@Override
	public LogicModelSet add(LogicModel model) {
		if (!contains(model)) {
			models.put(model, model.addLogicValueChangeHandler(this, false));
			setLogicValue(eval());
		}
		return this;
	}

	@Override
	public LogicModelSet remove(LogicModel model) {
		if (contains(model)) {
			this.models.remove(model).removeHandler();
			setLogicValue(eval());
		}
		return this;
	}

	@Override
	public boolean contains(LogicModel model) {
		return models.containsKey(model);
	}
	
	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		setLogicValue(eval());
	}
	
	@Override
	public Iterator<LogicModel> iterator() {
		return models.keySet().iterator();
	}
	
	@Override
	public int size() {
		return models.size();
	}
	
	@Override
	public String toDebugString() {
		boolean first= true;
		StringBuilder sb = new StringBuilder();
		sb.append(toDebugString(type)).append(" {");
		
		for (LogicModel model : this) {
			if (first) first = false;
			else sb.append(", ");
			sb.append(GwtGoodies.toDebugString(model));
		}
		return sb.append('}').toString();
	}
}