package com.adaptiweb.server.utils;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;

import com.adaptiweb.utils.commons.InicializableVariableSource;
import com.adaptiweb.utils.commons.VariableResolver;

/**
 * Dynamic variable source for resolving Google Maps API Key according to server name (from request).
 * <p>You can configure this source to {@link VariableResolver}.
 * <pre>
 *   &lt;bean name="config" class="{@link com.adaptiweb.utils.commons.VariableResolver}" primary="true"&gt;
 *     &lt;constructor-arg&gt;
 *       &lt;list&gt;
 *         &lt;ref bean="configProperties"/&gt;
 *         &lt;bean class="{@link eu.geomodel.monitor.server.GoogleMapsApiKeyVariableSource}"&gt;
 *           &lt;property name="<b>variableName</b>" value="google.maps.api.key"/&gt;
 *         &lt;/bean&gt;
 *       &lt;/list&gt;
 *     &lt;/constructor-arg&gt;
 *   &lt;/bean&gt;
 * </pre>
 * <p>Property <b>variableName</b> has default value <b>"googleMaps.apiKey"</b>.
 * <h5>How does this variable source works?</h5><ul>
 * <li>Source is resolving only variable name equals to value of property {@link #variableName} and only if request is available
 * (thread handling HTTP requests call).
 * <li>Server name (from request) is normalized to max two-level domain name.
 * <li>After that, it resolves new variable with name <br><code>{@link #variableName} + "." <i>normalizedServerName</i></code><br>
 *  ... that variable should be configured in other variable source (e.g. {@link com.adaptiweb.utils.ci.PropertyFileVariableSource})
 * </ul>
 * 
 * @see com.adaptiweb.utils.commons.VariableResolver
 */
public class GoogleMapsApiKeyVariableSource implements InicializableVariableSource {

	private static final Logger logger = LoggerFactory.getLogger(GoogleMapsApiKeyVariableSource.class);

	public String variableName = "googleMaps.apiKey";

	@Autowired HttpServletRequest request;

	private VariableResolver variables;

	@Override
	public String getRawValue(String variableName) {
		if (variables == null || RequestContextHolder.getRequestAttributes() == null)
			return null;

		if (this.variableName.equals(variableName)) {
			String serverName = normalizeDomainName(request.getServerName());
			String property = variableName + "." + serverName;
			String result = variables.resolveValue(property, "");
			logger.info("Api key for server name '{}' is {}", serverName, result.length() == 0 ? "${" + property + "}" : result);
			return result;
		} else
			return null;
	}

	@Override
	public void initSource(VariableResolver variables) throws IOException {
		this.variables = variables;
	}

	private static final Pattern IP_ADDRESS = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");

	public static String normalizeDomainName(String serverName) {
		if (IP_ADDRESS.matcher(serverName).matches())
			return serverName;
		int index = serverName.lastIndexOf('.');
		if (index == -1)
			return serverName;
		index = serverName.lastIndexOf('.', index - 1);
		return index == -1 ? serverName : serverName.substring(index + 1);
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
