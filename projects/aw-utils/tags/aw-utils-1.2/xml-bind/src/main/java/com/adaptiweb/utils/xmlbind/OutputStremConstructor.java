package com.adaptiweb.utils.xmlbind;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

public class OutputStremConstructor implements MarshallerOutput {
	
	enum Status { outElement, inElement, textContent };
	
	private String indent = "\t";
	private boolean format = true;
	private Status status = null;

	private Writer out;
	
	private List<String> stack = new LinkedList<String>(); 

	public OutputStremConstructor(OutputStream out) throws ParserException {
		try {
			this.out = new OutputStreamWriter(out, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see sk.anasoft.jaxb.marshaler.MarshallerOutput#startElement(java.lang.String)
	 */
	public void startElement(String element) throws IOException {
		if(Status.inElement.equals(status)) {
			out.append('>');
			enter();
		}
			
		indent(stack.size());
		out.append('<').append(element);
		stack.add(0, element);
		status = Status.inElement;
	}
	
	private final static String quoteCode = "&#x" + Integer.toHexString("\"".codePointAt(0)) + ";"; 
	
	/* (non-Javadoc)
	 * @see sk.anasoft.jaxb.marshaler.MarshallerOutput#addAttribute(java.lang.String, java.lang.String)
	 */
	public void addAttribute(String name, String value) throws ParserException, IOException {
		if(!Status.inElement.equals(status)) {
			throw new ParserException("Illegal status " + status);
		}
		value = value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
		value = value.replace("\"", quoteCode);
		out.append(' ').append(name).append('=').append('"').append(value).append('"');
	}
	
	/* (non-Javadoc)
	 * @see sk.anasoft.jaxb.marshaler.MarshallerOutput#stopElement()
	 */
	public void stopElement() throws IOException {
		if(Status.inElement.equals(status)) {
			out.append("/>");
			enter();
			stack.remove(0);
		}
		else {
			indent(stack.size() - 1);
			out.append("</").append(stack.remove(0)).append('>');
			enter();
		}
		status = Status.outElement;
	}

	/* (non-Javadoc)
	 * @see sk.anasoft.jaxb.marshaler.MarshallerOutput#addText(java.lang.String)
	 */
	public void addText(String text) throws IOException {
		if(Status.inElement.equals(status)) {
			out.append('>');
		}
		out.append(text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;"));
		status = Status.textContent;
	}
	
	private void enter() throws IOException {
		if(format) {
			out.append('\n');
		}
	}

	private void indent(int count) throws IOException {
		if(format && indent != null && !Status.textContent.equals(status)) {
			for(int i = 0; i < count; i++) out.append(indent);
		}
	}

	/* (non-Javadoc)
	 * @see sk.anasoft.jaxb.marshaler.MarshallerOutput#stop()
	 */
	public void flush() throws IOException {
		while(stack.size() > 0)
			stopElement();
		out.flush();
	}

	public String getCurentTagName() {
		return stack.get(0);
	}
}
