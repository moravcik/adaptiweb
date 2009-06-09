package com.adaptiweb.utils.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides method {@link #replaceVariables(String)} for replacing variables in string.
 * This class also implements {@link VariableSource} and it allows simply define
 * multi variable sources in one.
 */
public class VariableResolver implements VariableSource {

	private final VariableSource[] sources;

	/**
	 * Character class defines characters allowed in variable name.
	 */
	private static final String VARIABLE_NAME_CHARACTER_CLASE = "[\\w\\-\\.]";  
	
	/**
	 * Pattern used for replacing variable references for its values in strings. 
	 */
	public static final Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$((?:"
			+ VARIABLE_NAME_CHARACTER_CLASE + "++)|(?:\\{"
			+ VARIABLE_NAME_CHARACTER_CLASE + "++\\}))");

	/**
	 * Variable sources passed in this constructor are used for searching variables.
	 * Searching is in same order as sources are. 
	 * @param sources
	 */
	public VariableResolver(VariableSource... sources) {
		this.sources = sources;
	}

	/**
	 * In given string substitute variable references with its values.
	 * Variable reference can be specified with two forms:<ol>
	 * <li><b>$<i>variableName</i></b> - this is general form</li>
	 * <li><b>${<i>variableName</i>}</b> - this form must be used if variable name
	 * is immediately followed by character allowed in variableName.</li>
	 * </ol>
	 * Variables are searched in variable sources defined in constructor. 
	 * @param string which can contains variable references.
	 * @return string with replaced variable references, if variables was found. 
	 * @see #VariableResolver(VariableSource...)
	 */
	public String replaceVariables(String string) {
		StringBuffer sb = new StringBuffer();
		Matcher m = VARIABLE_REFERENCE_PATTERN.matcher(string);
		while (m.find()) {
			String variableValue = getVariableValue(StringUtils.trim(m.group(1), "{}"));
			String replacement = variableValue != null ? variableValue : m.group();
			m.appendReplacement(sb, replacement.replace("\\", "\\\\").replace("$", "\\$"));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * Search value of variable specified by name.
	 * Values are searched in variable sources defined in {@link #VariableResolver(VariableSource...) constructor}.  
	 * @see sk.dvsk.common.utils.variable.VariableSource#getVariableValue(java.lang.String)
	 */
	public String getVariableValue(String variableName) {
		for(VariableSource source : sources) {
			String variableValue = source.getVariableValue(variableName);
			if(variableValue != null) return variableValue;
		}
		return null;
	}
	
	public static VariableSource asSource(final Map<String,String> map) {
		return new VariableSource() {
			public String getVariableValue(String variableName) throws NullPointerException {
				return map.get(variableName);
			}
		};
	}

	public Map<String, String> replaceVariables(Map<String, String> propertyMap) {
		Map<String, String> resultMap = new HashMap<String, String>(propertyMap);
		for(Map.Entry<String, String> entry : resultMap.entrySet())
			resultMap.put(entry.getKey(), replaceVariables(entry.getValue()));
		return resultMap;
	}

	public static VariableSource asSource(final Properties select) {
		return new VariableSource() {
			public String getVariableValue(String variableName) throws NullPointerException {
				return select.getProperty(variableName);
			}
		};
	}
}
