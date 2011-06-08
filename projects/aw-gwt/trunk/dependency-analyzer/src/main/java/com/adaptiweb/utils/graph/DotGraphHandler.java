package com.adaptiweb.utils.graph;

import java.io.IOException;
import java.io.StringWriter;

public class DotGraphHandler {
	
	private final OrientedGraph<Vertex, Boolean> graph = new OrientedGraph<Vertex, Boolean>();
	private boolean catToBound = true;
	private GraphHierarchy graphHierarchy = new GraphHierarchy();

	public boolean isCatToBound() {
		return catToBound;
	}

	public void setCatToBound(boolean catToBound) {
		this.catToBound = catToBound;
	}

	public void handle(Vertex from, Vertex to) throws IOException {
		graphHierarchy.add(from);
		graphHierarchy.add(to);
		graph.addEdge(from, to, false);
	}
	
	public void handleStart() throws IOException {
		graphHierarchy.clear();
		graph.clear();
	}
	
	public void handleEnd() throws IOException {
		if(catToBound) graph.cutToBone();
		else graph.markRemoveable(true);
	}

	@Override
	public String toString() {
		try {
			StringWriter out = new StringWriter();
			writeTo(out);
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	public void writeTo(Appendable out) throws IOException {
		out.append("digraph jarjar {\n");
		graphHierarchy.writeTo(out);
		
		for(OrientedEdge<Vertex, Boolean> edge : graph) {
			out.append('\t');
			out.append('"').append(edge.getFromNode().getId()).append('"');
			out.append(" -> ");
			out.append('"').append(edge.getToNode().getId()).append('"');
			if(edge.getValue()) out.append(" [style=dotted]");
			out.append(";\n");
		}
		out.append("}\n");
	}
}
