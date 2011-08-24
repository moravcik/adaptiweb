package com.adaptiweb.utils.ci;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides method {@link #replaceVariables(String)} for replacing variables in string.
 * This class also implements {@link VariableSource} and it allows simply define
 * multi variable sources in one.
 * 
 * NOTE: planned to use Spring SpelExpression instead
 */
public final class VariableResolver implements VariableSource {

	private final VariableSource[] sources;
	
	private int initStatus;

	private final ThreadLocal<LinkedList<String>> evaluatingDynamicProperties = 
		new ThreadLocal<LinkedList<String>>() {
			protected LinkedList<String> initialValue() {
				return new LinkedList<String>();
			};
		};
	
	/**
	 * Character class defines characters allowed in variable name.
	 */
	private static final String VARIABLE_NAME_CHARACTER_CLASE = "[\\w\\-\\.]";  
	
	/**
	 * Pattern used for replacing variable references for its values in strings. 
	 */
	public static final Pattern VARIABLE_REFERENCE_PATTERN = Pattern.compile("\\$(?:" +
			"(\\$|" + VARIABLE_NAME_CHARACTER_CLASE + "++)" +
			"|" +
			"\\{(" + VARIABLE_NAME_CHARACTER_CLASE + "++)\\}" +
			")");

	/**
	 * Variable sources passed in this constructor are used for searching variables.
	 * Searching is in same order as sources are. 
	 * @param sources
	 */
	public VariableResolver(VariableSource... sources) {
		this.sources = sources;
		initStatus = sources.length;
		try {
			while (initStatus > 0) {
				VariableSource source = this.sources[initStatus - 1];
				if (source instanceof InicializableVariableSource)
					((InicializableVariableSource) source).initSource(this);
				initStatus--;
			}
		} catch (IOException e) {
			throw new IllegalStateException("Error while initializing " + (initStatus + 1) + ". variable source.", e);
		}
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
		if (string == null) return null;
		if (string.indexOf('$') == -1) return string;

		LinkedList<String> trace = evaluatingDynamicProperties.get();
		StringBuffer sb = new StringBuffer(string.length());
		Matcher m = VARIABLE_REFERENCE_PATTERN.matcher(string);
		
		while (m.find()) {
			String variableName = m.group(m.group(1) == null ? 2 : 1);
			
			if (trace.contains(variableName)) throw circularSubstitutionError(trace, variableName);
			trace.addLast(variableName);
			
			String variableValue = "$".equals(variableName) ? "$" : resolveValue(variableName);
			if (variableValue == null) throw new IllegalStateException("Missing property " + variableName);
			m.appendReplacement(sb, variableValue.replace("\\", "\\\\").replace("$", "\\$"));
			
			trace.removeLast();
		}
		m.appendTail(sb);
		
		if(trace.size() == 0) evaluatingDynamicProperties.remove();
		
		return sb.toString();
	}

	private IllegalStateException circularSubstitutionError(LinkedList<String> trace, String key) {
		StringBuilder sb = new StringBuilder("Circular substitution ");
		for (String path : trace) {
			sb.append(path);
			if (path.equals(key)) sb.append('*');
			sb.append(" <- ");
		}
		sb.append(key);
		return new IllegalStateException(sb.toString());
	}

	public String getRawValue(String variableName) {
		return getRawValue(variableName, null);
	}
	
	/**
	 * Search value of variable specified by name.
	 * Values are searched in variable sources defined in {@link #VariableResolver(VariableSource...) constructor}.  
	 * @see sk.dvsk.common.utils.variable.VariableSource#getRawValue(java.lang.String)
	 */
	public String getRawValue(String variableName, String defaultValue) {
		for(int i = initStatus; i < sources.length; i++) {
			String variableValue = sources[i].getRawValue(variableName);
			if(variableValue != null) return variableValue;
		}
		return defaultValue;
	}
	
	public String resolveValue(String varialeName) {
		return resolveValue(varialeName, null);
	}
	
	public String resolveValue(String varialeName, String defaultValue) {
		return replaceVariables(getRawValue(replaceVariables(varialeName), defaultValue));
	}
	
	/**
	 * Resolve all variables in keys and values of given map. 
	 * @param map
	 * @return new map with resolved variables.
	 */
	public Map<String, String> resolveValues(Map<String, String> map) {
		Map<String, String> result = new HashMap<String, String>(map.size());
		
		for(Map.Entry<String, String> entry : map.entrySet())
			result.put(entry.getKey(), replaceVariables(entry.getValue()));
		
		return result;
	}
	
	public Map<String, String> resolveAndReplaceValues(Map<String, String> map) {
		for(Map.Entry<String, String> entry : map.entrySet())
			map.put(entry.getKey(), replaceVariables(entry.getValue()));
		return map;
	}

	/**
	 * Resolve all variables in keys and values of given map. 
	 * @param collection
	 * @return new list with resolved variables.
	 */
	public List<String> resolveItems(Collection<String> collection) {
		List<String> result = new ArrayList<String>(collection.size());
		for(String item : collection) result.add(replaceVariables(item));
		return result;
	}
	
	public void resolveAndReplaceItems(Collection<String> collection) {
		Collection<String> aux = new ArrayList<String>(collection.size());
		for(String item : collection) aux.add(replaceVariables(item));
		
		collection.clear();
		collection.addAll(aux);
	}

	/**
	 * Create new VariableSroucer by wrapping map.
	 * @param map 
	 * @return new VariableSroucer by wrapping map.
	 */
	public static VariableSource asSource(final Map<String,String> map) {
		return new VariableSource() {
			public String getRawValue(String variableName) throws NullPointerException {
				return map.get(variableName);
			}
		};
	}
	
	/**
	 * Create new VariableSource by wrapping Properties instance.
	 * @param properties
	 * @return new VariableSource by wrapping Properties instance.
	 */
	public static VariableSource asSource(final Properties properties) {
		return new VariableSource() {
			public String getRawValue(String variableName) throws NullPointerException {
				return properties.getProperty(variableName);
			}
		};
	}

}
