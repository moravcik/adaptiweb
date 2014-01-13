package com.adaptiweb.utils.commons.param;

import java.net.URI;
import java.util.Map;

public class ParameterQueryBuilder {
	
	private static final UrlEncoder plainUrlEncoder = new UrlEncoder() {
		@Override
		public String encode(String value) {
			return value;
		}
	};

	String baseUrl;
	String baseUrlDelimiter;
	
	ParameterMap params;

	UrlEncoder encoder;

	public ParameterQueryBuilder() {
		this.params = new ParameterMap();
	}
	
	@Override
	public String toString() {
		return toUrlString(encoder != null ? encoder : plainUrlEncoder);
	}

	public URI toURI() {
		return URI.create(toString());
	}

	public ParameterQueryBuilder withEncoder(UrlEncoder encoder) {
		this.encoder = encoder;
		return this;
	}
	
	public ParameterQueryBuilder withDefaultEncoder() {
		this.encoder = ParameterUtils.defaultUrlEncoder;
		return this;
	}
	
	public ParameterQueryBuilder setBaseUrl(String baseUrl) {
		// default value if not present
		if (this.baseUrlDelimiter == null) { 
			this.baseUrlDelimiter = QueryDelimiter.STANDARD.delimiter;
		}
		this.baseUrl = baseUrl;
		return this;
	}

	public ParameterQueryBuilder setBaseUrl(String baseUrl, QueryDelimiter queryDelimiter) {
		this.baseUrlDelimiter = queryDelimiter.delimiter;
		this.baseUrl = baseUrl;
		return this;
	}
	
	public ParameterQueryBuilder setBaseUrlDelimiter(QueryDelimiter queryDelimiter) {
		this.baseUrlDelimiter = queryDelimiter.delimiter;
		return this;
	}
	
	public ParameterQueryBuilder addParameter(String name, Object value) {
		this.params.put(name, value != null ? String.valueOf(value.toString()) : null);
		return this;
	}
	
	public ParameterQueryBuilder addAllParameters(Map<String, String> paramMap) {
		if (paramMap != null) this.params.putAll(paramMap);
		return this;
	}
	
	public ParameterQueryBuilder addUrlParts(String... urlParts) {
		params.addUrlParts(urlParts);
		return this;
	}
	
	public <I> ParameterQueryBuilder extractParameters(I input, Parameter<I>... parameters) {
		for (Parameter<I> parameter : parameters) {
			Object extractedValue = parameter.extractValue(input);
			params.put(parameter.getParameterName(), formatValue(extractedValue));
		}
		return this;
	}
	
	private String toUrlString(UrlEncoder encoder) {
		String urlQuery = toUrlQueryString(params, encoder);
		if (urlQuery == null || urlQuery.length() == 0) {
			return baseUrl != null ? baseUrl : "";
		} else if (baseUrl != null) {
			String delimiterPart = baseUrl.contains(baseUrlDelimiter) ? "&" : baseUrlDelimiter;
			return baseUrl + delimiterPart + urlQuery;
		} else return urlQuery;
	}
	
	public boolean isEmpty() {
		return params.isEmpty();
	}

	private static String prepareParameter(boolean needAmp, Map.Entry<String, String> paramValue, UrlEncoder encoder) {
		return paramValue.getValue() == null 
			? "" : formatParam(needAmp, paramValue.getKey(), paramValue.getValue(), encoder);
	}

	private static String formatParam(boolean needAmp, String paramName, String paramValue, UrlEncoder encoder) {
		return (needAmp ? "&" : "") + paramName + "=" + encoder.encode(paramValue);
	}

	private static String toUrlQueryString(ParameterMap parameterMap, UrlEncoder encoder) {
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> paramValue : parameterMap.entrySet()) {
			result.append(prepareParameter(result.length() > 0, paramValue, encoder));
		}
		return result.toString();
	}

	private static String formatValue(Object value) {
		if (value == null) return null;
		if (value instanceof String) return value.toString();
		if (value instanceof Boolean) return ((Boolean) value) ? "1" : "0";
		if (value instanceof Enum<?>) return ((Enum<?>) value).name();
		return String.valueOf(value);
	}

}
