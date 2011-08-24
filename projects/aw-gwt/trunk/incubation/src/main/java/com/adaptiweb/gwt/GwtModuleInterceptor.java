package com.adaptiweb.gwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.adaptiweb.gwt.conf.GwtModuleJsApi;
import com.adaptiweb.gwt.conf.GwtModulePreferences;
import com.adaptiweb.gwt.preload.GwtPreloadManager;
import com.adaptiweb.utils.ci.VariableResolver;

public class GwtModuleInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(GwtModuleInterceptor.class);

	private Map<String, GwtModulePreferences> modules = Collections.emptyMap();

	private GwtModuleBean gwtModuleBean;

	private VariableResolver variables;

	protected GwtPreloadManager preloadManager;

	@Autowired
	public void setModules(Map<String, GwtModulePreferences> modules) {
		this.modules = modules;
	}

	@Autowired
	public void setVariables(VariableResolver variables) {
		this.variables = variables;
	}

	@Autowired(required=false)
	public void setPreloadManager(GwtPreloadManager preloadManager) {
		this.preloadManager = preloadManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView == null) return;

		String contextPath = request.getContextPath();
		String requestUri = request.getRequestURI().substring(contextPath.length());

		Set<String> matchesModules = new TreeSet<String>();

		for (Entry<String, GwtModulePreferences> module : modules.entrySet())
			if (module.getValue().matchesUri(requestUri))
				matchesModules.add(module.getKey());

		if (!matchesModules.isEmpty()) {
			logger.debug("Uri '{}' does match GWT modules: {}", requestUri, matchesModules);

			for (String moduleName : matchesModules) {
				GwtModuleBean gwtModule = getGwtModuleBean();
				gwtModule.setContextPath(request.getContextPath());
				gwtModule.setName(moduleName);

				gwtModule.setJsApis(prepareJsApis(requestUri, gwtModule.getName()));

				if (preloadManager != null) {
					Map<String, String> serializedObjects = preloadManager.getPreloadValues(moduleName, request);
					logger.info("Serialized objects: {}", serializedObjects.keySet());
					gwtModule.setSerializedObjects(serializedObjects);
				}

				modelAndView.getModel().put("gwtModule", gwtModule);
				break;
			}
		}
	}

	private List<String> prepareJsApis(String requestUri, String gwtModuleName) {
		Collection<GwtModuleJsApi> jsApis = modules.get(gwtModuleName).getJsApis();
		if (jsApis == null) return null;

		List<String> result = new ArrayList<String>(jsApis.size());
		for (GwtModuleJsApi jsApi : jsApis) {
			if (jsApi.matchesUri(requestUri)) {
				result.add(substVariables(jsApi.getUrl()));
			}
		}
		return result;
	}

	private String substVariables(String str) {
		return variables == null ? str : variables.replaceVariables(str);
	}

	@Autowired(required=false)
	protected void setGwtModuleBean(GwtModuleBean gwtModuleBean) {
		this.gwtModuleBean = gwtModuleBean;
	}

	protected GwtModuleBean getGwtModuleBean() {
		return gwtModuleBean == null ? new GwtModuleBean() : gwtModuleBean;
	}

}
