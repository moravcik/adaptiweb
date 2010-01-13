package com.adaptiweb.utils.commons;

/**
 * Interface defines access to some variable source.
 * There is also defined standard variable sources like environment and system variables.
 */
public interface VariableSource {

	/**
	 * Variables can have string values.
	 * @param variableName must not be {@code null}
	 * @return variable value or {@code null} if variable is not present in this source.
	 * @throws NullPointerException if variableName is {@code null}.
	 */
	String getRawValue(String variableName) throws NullPointerException;

	/**
	 * Source for environment variables.
	 * @see VariableSource
	 * @see System#getenv(String)
	 */
	static final VariableSource ENVIRONMENT = new VariableSource() {

		public String getRawValue(String variableName) {
			return System.getenv(variableName);
		}
	};

	/**
	 * Source for system variables.
	 * @see VariableSource
	 * @see System#getProperty(String)
	 */
	static final VariableSource SYSTEM = new VariableSource() {

		public String getRawValue(String variableName) {
			return System.getProperty(variableName);
		}
	};

	/**
	 * Special variable source which always returns empty string for all variable names.
	 * This source can be used as last source for searching specific variable.
	 * It has meaning when variable references are replaces in string.
	 * If value of variable isn't found in other source reference is removed.
	 * @see VariableSource
	 */
	static final VariableSource EMPTY = new VariableSource() {

		public String getRawValue(String variableName) {
			return "";
		}
	};
}
