package com.adaptiweb.gwt;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GwtModuleBean {

	/**
	 * Final name of GWT module - name assigned after compilation.
	 * Name after compilation can be different than before if root element &lt;module&gt;
	 * has attribute raname-to (in *.gwt.xml file).
	 */
	private String name;

	private String contextPath;

	private String locale;

	private boolean enabledHistorySupport = true;

	private Map<String, String> clientProperties;

	/**
	 * List of JavaScript URLs which should be included on page.
	 * <p>
	 * <b>Note:</b> JavaScript can be included also by GWT (&lt;sccript&gt; element in *.gwt.xml file),
	 * but this didn't allows dynamically include parameters into URL (like Google Maps API key).
	 * </p>
	 */
	private List<String> jsApis;

	private Map<String, String> serializedObjects;

	@Override
	public String toString() {
		return getName();
	}

	public String getHead() {
		StringBuilder buff = new StringBuilder();
		if (getLocale() != null) appendLocale(buff);
		if (getClientProperties() != null) appendClientProperties(buff);
		if (getJsApis() != null) appendJsApis(buff);
		if (getSerializedObjects() != null) appendSerializedObjects(buff);
		appendBootScript(buff);
		return buff.toString();
	}

	public String getBody() {
		StringBuilder buff = new StringBuilder();
		if (isEnabledHistorySupport()) appendHistoryFrame(buff);
		return buff.toString();
	}

	private void appendLocale(StringBuilder buff) {
		appendNewLine(buff);
		buff.append("<meta name=\"gwt:property\" content=\"locale=").append(getLocale()).append("\" />");
	}

	private void appendClientProperties(StringBuilder buff) {
		for (Entry<String,String> p : getClientProperties().entrySet()) {
			appendNewLine(buff);
			buff.append("<meta name=\"client:").append(p.getKey()).append("\" content=\"").append(p.getValue()).append("\" />");
		}
	}

	private void appendJsApis(StringBuilder buff) {
		for (String url : getJsApis()) {
			appendNewLine(buff);
			buff.append("<script language=\"javascript\" src=\"").append(url).append("\"></script>");
		}
	}

	private void appendBootScript(StringBuilder buff) {
		appendNewLine(buff);
		buff.append("<script language=\"javascript\" src=\"");
		buff.append(getContextPath()).append('/').append(getName()).append('/').append(getName());
		buff.append(".nocache.js\"></script>");
	}

	private void appendSerializedObjects(StringBuilder buff) {
		Map<String, String> serializedObjects = getSerializedObjects();
		if (!serializedObjects.isEmpty()) {
			appendNewLine(buff);
			buff.append("<script language=\"javascript\" type=\"text/javascript\">");

			for (Entry<String,String> o : serializedObjects.entrySet()) {
				appendNewLine(buff);
				String value = o.getValue().replace("'", "\\'").replace("</script>", "</' + 'script>");
				buff.append("var ").append(o.getKey()).append("='").append(value).append("';");
			}

			appendNewLine(buff);
			buff.append("</script>");
		}
	}

	private void appendNewLine(StringBuilder buff) {
		if (buff.length() > 0) buff.append("\n\t");
	}

	private void appendHistoryFrame(StringBuilder buff) {
		appendNewLine(buff);
		buff.append("<iframe src=\"javascript:''\" id=\"__gwt_historyFrame\" style=\"position:absolute;width:0;height:0;border:0\"></iframe>");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public boolean isEnabledHistorySupport() {
		return enabledHistorySupport;
	}

	public void setEnabledHistorySupport(boolean enabledHistorySupport) {
		this.enabledHistorySupport = enabledHistorySupport;
	}

	public Map<String, String> getClientProperties() {
		return clientProperties;
	}

	public void setClientProperties(Map<String, String> clientProperties) {
		this.clientProperties = clientProperties;
	}

	public List<String> getJsApis() {
		return jsApis;
	}

	public void setJsApis(List<String> jsApis) {
		this.jsApis = jsApis;
	}

	public Map<String, String> getSerializedObjects() {
		return serializedObjects;
	}

	public void setSerializedObjects(Map<String, String> serializedObjects) {
		this.serializedObjects = serializedObjects;
	}
}
