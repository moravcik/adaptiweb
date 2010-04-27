package com.adaptiweb.gwt;

import java.util.Collections;
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

public class GwtModuleInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(GwtModuleInterceptor.class);

	private Map<String, GwtModulePreferences> modules = Collections.emptyMap();

	private GwtModuleBean gwtModuleBean;
	
	@Autowired
	public void setModules(Map<String, GwtModulePreferences> modules) {
		this.modules = modules;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String contextPath = request.getContextPath();
		String requestUri = request.getRequestURI().substring(contextPath.length());
		
		Set<String> matchesModules = new TreeSet<String>();
		
		for (Entry<String, GwtModulePreferences> module : modules.entrySet())
			if (module.getValue().matchesUri(requestUri))
				matchesModules.add(module.getKey());

		if (!matchesModules.isEmpty()) {
			logger.info("Uri '{}' does match GWT modules: {}", requestUri, matchesModules);
			
			GwtModuleBean gwtModule = getGwtModuleBean();
			gwtModule.setContextPath(request.getContextPath());
			gwtModule.setName(matchesModules.iterator().next());
			
			modelAndView.getModel().put("gwtModule", gwtModule);
		}
	}
	
	@Autowired(required=false)
	protected void setGwtModuleBean(GwtModuleBean gwtModuleBean) {
		this.gwtModuleBean = gwtModuleBean;
	}
	
	protected GwtModuleBean getGwtModuleBean() {
		return gwtModuleBean == null ? new GwtModuleBean() : gwtModuleBean;
	}

}
