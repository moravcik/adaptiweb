package com.adaptiweb.server.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GwtPreloadManager {
	
	@Autowired
	protected SerializationPolicyProvider policyProvider;
	
	private final Map<String, List<Loader>> loaders = new HashMap<String, List<Loader>>();
	
	@Autowired
	public void init(Map<String, RemoteServiceServlet> gwtServices) {
	}
	
	public Map<String, String> getPreloadValues(String gwtModul) {
		Map<String, String> result = new HashMap<String, String>(determineResultSize(gwtModul));

//		String value = null;
//		try { value = RPC.encodeResponseForSuccess(ReflectionUtils.findMethod(TileLayerService.class, "getTileLayers"), tiles, policyProvider.getPolicyFor(TileLayerService.class)); }
//		catch (SerializationException e) { e.printStackTrace(); }
//		if (value != null) serializedObjects.put(TileLayerBase.class.getName().replace('.', '_') + "_array", value);

		return result;
	}
	
	private int determineResultSize(String gwtModul) {
		int result = 0;
		if (loaders.containsKey(null)) result += loaders.get(null).size();
		if (loaders.containsKey(gwtModul)) result += loaders.get(gwtModul).size();
		return result ;
	}

	private static class Loader {
		public String load(SerializationPolicyProvider policyProvider) {
//			try {
//				return value = RPC.encodeResponseForSuccess(ReflectionUtils.findMethod(TileLayerService.class, "getTileLayers"), tiles, policyProvider.getPolicyFor(TileLayerService.class));
//			} catch (SerializationException e) {
//				e.printStackTrace();
//			}
			return null;
		}
	}
}
