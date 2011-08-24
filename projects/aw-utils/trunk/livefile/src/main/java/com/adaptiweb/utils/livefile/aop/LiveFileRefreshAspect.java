package com.adaptiweb.utils.livefile.aop;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.adaptiweb.utils.livefile.LiveFile;


public class LiveFileRefreshAspect {

	@Autowired Map<String, LiveFile> handlers;
	
	public void refreshLiveFile(LiveFileRefresh ann) throws Throwable {
		LiveFile handler = handlers.get(ann.value());
		if (handler != null) handler.refresh();
	}
	
}
