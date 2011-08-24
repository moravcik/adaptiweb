package com.adaptiweb.utils.ci;



class DefaultIntegerPropertyConverter implements PropertyConverter<Integer> {

	@Override
	public Integer convert(VariableResolver variables, String name, String defaultValue) {
		String result = variables.resolveValue(name);
		return result == null ? null : Integer.parseInt(result);
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}
}
