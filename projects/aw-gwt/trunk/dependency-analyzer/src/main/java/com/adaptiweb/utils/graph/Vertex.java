package com.adaptiweb.utils.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Vertex {
	
	private final String id;
	private Map<String, String> attributes = new HashMap<String, String>();
	
	public Vertex(String id) {
		this.id = id;
	}

	String getId() {
		return id;
	}
	
	String toDefinitionString() {
		StringBuilder sb = new StringBuilder();
		sb.append('"').append(getId()).append('"');
		if (attributes.size() > 0) {
			sb.append(" [");
			for (Entry<String, String> attribute : attributes.entrySet()) {
				sb.append(attribute.getKey()).append('=').append(attribute.getValue());
			}
			sb.append("]");
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof Vertex == false) return false;
		Vertex other = (Vertex) obj;
		return getId().equals(other.getId());
	}

	public void setLabel(String label) {
		attributes.put("label", "\"" + label + "\"");
	}

	public String[] getPath() {
		return new String[0];
	}
}
