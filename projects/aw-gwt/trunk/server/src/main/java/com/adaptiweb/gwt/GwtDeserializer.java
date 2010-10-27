package com.adaptiweb.gwt;

import org.springframework.util.ClassUtils;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;

public class GwtDeserializer {
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> typeClass, String serialized) throws SerializationException {
		ServerSerializationStreamReader reader = new ServerSerializationStreamReader(typeClass.getClassLoader(), null);
		reader.prepareToRead(serialized);
		Object obj = reader.readObject();
		if (obj != null && ClassUtils.isAssignableValue(typeClass, obj)) return (T) obj;
		else throw new SerializationException("Failed deserialization: " + typeClass.getName());
	}
	
}
