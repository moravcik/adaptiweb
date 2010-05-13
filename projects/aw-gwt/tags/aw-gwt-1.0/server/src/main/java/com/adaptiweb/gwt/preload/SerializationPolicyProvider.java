package com.adaptiweb.gwt.preload;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 * Implementation should provide {@link SerializationPolicy}, which is required for
 * {@link RPC#encodeResponseForSuccess(java.lang.reflect.Method, Object) RPC serialization} on server side.
 */
public interface SerializationPolicyProvider {
	/**
	 * @param serviceInterfaceClass interface of GWT-RPC synchronized service (must extends {@link RemoteService})
	 * @return serialization policy for given {@code serviceInterfaceClass}
	 */
	SerializationPolicy getPolicyFor(Class<?> serviceInterfaceClass);
}
