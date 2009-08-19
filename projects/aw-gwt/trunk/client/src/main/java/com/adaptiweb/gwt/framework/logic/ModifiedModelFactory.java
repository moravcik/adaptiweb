package com.adaptiweb.gwt.framework.logic;


public class ModifiedModelFactory {
	
	private ModifiedModelFactory() {}

	public static LogicModelSet and(LogicModel...models) {
		return new AbstractLogicModelCountingSet("and", models) {
			@Override
			protected boolean eval() {
				return getPositiveCount() == size();
			}
		};
	}

	public static LogicModelSet or(LogicModel...models) {
		return new AbstractLogicModelCountingSet("or", models) {
			@Override
			protected boolean eval() {
				return getPositiveCount() > 0;
			}
		};
	}

	public static LogicModelSet xor(LogicModel...models) {
		return new AbstractLogicModelCountingSet("xor", models) {
			@Override
			protected boolean eval() {
				return getPositiveCount() == 0 || getPositiveCount() == size();
			}
		};
	}

	public static LogicModel not(LogicModel model) {
		return new AbstractLogicModelSet("not", model) {
			@Override
			protected boolean eval() {
				assert size() == 1;
				for (LogicModel model : this)
					return !model.getLogicValue();
				throw new Error("Dead code!");
			}
		};
	}
}
