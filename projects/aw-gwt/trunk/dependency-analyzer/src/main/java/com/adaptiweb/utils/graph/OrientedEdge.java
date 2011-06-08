package com.adaptiweb.utils.graph;

/**
 * @param <T> type of vertexes in graph
 * @param <V> type of value of edge
 */
public class OrientedEdge<T extends Vertex, V> {

	private final T fromNode;
	private final T toNode;
	private V value;
	
	public OrientedEdge(T fromNode, T toNode, V value) {
		assertNotNull(fromNode);
		assertNotNull(toNode);
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.value = value;
	}

	private void assertNotNull(T node) {
		if(node == null)
			throw new IllegalArgumentException("null value");
	}

	public T getFromNode() {
		return fromNode;
	}

	public T getToNode() {
		return toNode;
	}
	
	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + fromNode.hashCode();
		result = prime * result + toNode.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj instanceof OrientedEdge<?, ?> == false) return false;
		final OrientedEdge<?,?> other = (OrientedEdge<?,?>) obj;
		return getToNode().equals(other.getToNode())
			&& getFromNode().equals(other.getFromNode());
	}
	
}
