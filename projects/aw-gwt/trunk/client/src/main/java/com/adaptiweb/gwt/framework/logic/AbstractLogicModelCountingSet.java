package com.adaptiweb.gwt.framework.logic;

public abstract class AbstractLogicModelCountingSet extends AbstractLogicModelSet {

	private int positiveCount;
	
	protected AbstractLogicModelCountingSet(String setType, LogicModel...models) {
		super(setType, models);
		setLogicValue(eval());
	}
	
	protected int getPositiveCount() {
		return positiveCount;
	}
	
	protected int getNegativeCount() {
		return size() - getPositiveCount();
	}

	@Override
	public LogicModelSet add(LogicModel model) {
		if (!contains(model) && model.getLogicValue()) positiveCount++;
		return super.add(model);
	}
	
	@Override
	public LogicModelSet remove(LogicModel model) {
		if (contains(model) && model.getLogicValue()) positiveCount--;
		return super.remove(model);
	}
	
	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		positiveCount += event.getLogicValue() ? 1 : -1;
		super.onLogicValueChange(event);
	}
}