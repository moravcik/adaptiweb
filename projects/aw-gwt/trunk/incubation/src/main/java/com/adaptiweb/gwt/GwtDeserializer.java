package com.adaptiweb.gwt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.util.ClassUtils;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.impl.ServerSerializationStreamReader;

/**
 * Usage: T deserialized = GwtDeserializer.decode(stringSerialized).deserialize(T.class);
 */
public class GwtDeserializer {
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> typeClass, String serialized) throws SerializationException {
		ServerSerializationStreamReader reader = new ServerSerializationStreamReader(typeClass.getClassLoader(), null);
		reader.prepareToRead(serialized);
		Object obj = reader.readObject();
		if (obj != null && ClassUtils.isAssignableValue(typeClass, obj)) return (T) obj;
		else throw new SerializationException("Failed deserialization: " + typeClass.getName());
	}
	
	public static Linker decode(String encodedAndSerialized) {
		String decodedAndSerialized = encodedAndSerialized;
		try {
			decodedAndSerialized = URLDecoder.decode(encodedAndSerialized, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// UTF-8 should be supported
		}
		return new Linker(decodedAndSerialized);
	}
	
	public static class Linker {
		String serialized;

		private Linker(String serialized) {
			this.serialized = serialized;
		}
		
		public <T> T deserialize(Class<T> typeClass) throws SerializationException {
			return GwtDeserializer.deserialize(typeClass, serialized);
		}
	}
	
}
