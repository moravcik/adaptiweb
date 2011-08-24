package com.adaptiweb.utils.xmlbind;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;



public class XmlParser  {
	
	private StringBuffer buffer = new StringBuffer();
	private boolean containsWhiteSpaceOnly = true;
	private boolean isCloseElement;
	private boolean isEmptyElement;
	private LinkedList<Tag> stack = new LinkedList<Tag>();
	protected String lastAttrName;
	protected char attrQuoteChar = '\0';
	
	private static class Tag {
		private final String name;
		private final Map<String,String> attrs = new HashMap<String, String>();

		public Tag(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj) return true;
			if(obj == null || getClass() != obj.getClass()) return false;
			final Tag other = (Tag) obj;
			return name.equals(other.name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		public String toString() {
			return name;
		}
		
		public void putAttr(String name, String value) {
			attrs.put(name, value);
		}

		public String getName() {
			return name;
		}

		public Map<String, String> getAttrs() {
			return attrs;
		}
	}
	
	private abstract class State {
		void enter(State previusState) throws XmlParserException {}
		abstract State next(char c) throws XmlParserException;
		void exit(State nextState) throws XmlParserException {}
	}
	
		
	private final State INIT_STATE = new State() {
		State next(char c) throws XmlParserException {
			if(c == '<')
				return TAG_STATE;
			else if(!Character.isWhitespace(c))
				throw error("Invalid character %d", Character.toCodePoint('\0', c));
			else
				return this;
		}
	};
	
	private final State CONTENT_STATE = new State() {
		State next(char c) {
			if(c == '<')
				return TAG_STATE;
			else {
				buffer.append(c);
				containsWhiteSpaceOnly &= Character.isWhitespace(c);
				return this;
			}
		}
		void enter(State previousState) throws XmlParserException {
			buffer.setLength(0);
			containsWhiteSpaceOnly = true;
		}

		void exit(State nextState) throws XmlParserException {
			if(containsWhiteSpaceOnly)
				handler.ignoreableWhitespaces(buffer.toString());
			else
				handler.contentString(XmlEntityCoder.decode(buffer.toString()));
		}
	};
	
	private final State COMMENT_STATE = new State() {

		private final char[] commentEnd = "-->".toCharArray();
		private final char[] cdataEnd = "]]>".toCharArray();
		private final char[] cdata = "[CDATA[".toCharArray();
		private char[] end;
		public int status, cdataStatus;
		
		@Override
		void enter(State any) {
			status = 0;
			cdataStatus = 0;
			end = commentEnd;
		}

		@Override
		State next(char c) throws XmlParserException {
			if (c == end[status]) status++;
			else status = 0;
			
			if (cdataStatus >= 0 && cdataStatus < cdata.length && c == cdata[cdataStatus]) cdataStatus++;
			else cdataStatus = -1;
			if (cdataStatus == cdata.length) end = cdataEnd;
			
			buffer.append(c);
			
			return status == end.length ? CONTENT_STATE : this;
		}
		
		void exit(State nextState) throws XmlParserException {
			if(end == cdataEnd)
				handler.cdataString(buffer.substring(cdata.length, buffer.length() - cdataEnd.length));
		};
	};
	
	private final State TAG_STATE = new State() {
		State next(char c) {
			if(c == '!' && buffer.length() == 0) {
				return COMMENT_STATE;
			}
			else if(c == '>') {
				return CONTENT_STATE;
			}
			else if(c == '/') {
				if(buffer.length() == 0) isCloseElement = true;
				else isEmptyElement = true;
			}
			else if(Character.isWhitespace(c)) {
				return ATTRNAME_STATE;
			}
			else {
				buffer.append(c);
			}
			return this;
		}
		
		void enter(State previousState) throws XmlParserException {
			buffer.setLength(0);
			isCloseElement = false;
			isEmptyElement = false;
		}

		void exit(State nextState) throws XmlParserException {
			if(isCloseElement) {
				String tagName = buffer.toString();
				if(!tagName.equals(stack.removeFirst().getName()))
					throw error("No matching end element (%s)", tagName);
				handler.endElement(tagName);
			}
			else if(nextState != COMMENT_STATE) {
				stack.addFirst(new Tag(buffer.toString()));
				if(nextState == CONTENT_STATE) element();
			}
		}
	};
	
	private final State ATTRNAME_STATE = new State() {
		State next(char c) {
			if(c == '>') {
				return CONTENT_STATE;
			}
			else if(c == '/') {
				isEmptyElement = true;
			}
			else if(c == '=') {
				return ATTRVALUE_STATE;
			}
			else if(!Character.isWhitespace(c)) {
				buffer.append(c);
			}
			return this;
		}
		void enter(State previusState) {
			buffer.setLength(0);
		}
		void exit(State nextState) {
			lastAttrName = buffer.toString(); 
			if(nextState == CONTENT_STATE) element();
		}
	};

	private final State ATTRVALUE_STATE = new State() {
		State next(char c) {
			if(c == '>' && attrQuoteChar == '\0') {
				return CONTENT_STATE;
			}
			else if(attrQuoteChar == '\0' && (c == '\'' || c == '"') || attrQuoteChar == c) {
				attrQuoteChar = attrQuoteChar == '\0' ? c : '\0';
				if(attrQuoteChar == '\0') {
					return ATTRNAME_STATE;
				}
			}
			else if(attrQuoteChar != '\0' || !Character.isWhitespace(c)) {
				buffer.append(c);
			}
			else if(Character.isWhitespace(c) && buffer.length() > 0) {
				return ATTRNAME_STATE;
			}
			return this;
		}
		void enter(State previusState) {
			buffer.setLength(0);
		}
		void exit(State nextState) {
			stack.getFirst().putAttr(lastAttrName, XmlEntityCoder.decode(buffer.toString()));
			if(nextState == CONTENT_STATE) element();
		};
	};
	
	private State currentState = INIT_STATE;

	private int lineNumber;
	private int columnsNumber;
	private int position;
	
	private final XmlHandler handler;

	private XmlParser(XmlHandler handler) {
		this.handler = handler;
	}

	protected void element() {
		Tag tag = stack.getFirst();
		if(tag.getName().startsWith("?")) {
			stack.remove();
			handler.processInstruction(tag.getName().substring(1), tag.getAttrs());
		}
		else {
			handler.beginElement(tag.getName(), tag.getAttrs());
			if(isEmptyElement) handler.endElement(stack.removeFirst().getName());
		}
	}

	protected XmlParserException error(String format, Object...params) {
		return new XmlParserException(format, params);
	}

	public static Object parse(Reader input, XmlHandler handler) throws IOException {
		return new XmlParser(handler).parse(input);
	}
	
	private Object parse(Reader input) throws IOException {
		State nextState, previousState = null;
		int token;
		lineNumber = 1;
		columnsNumber = 0;
		position = 0;
		try {
			while((token = input.read()) != -1) {
				position++;
				columnsNumber++;
				
				if(previousState != currentState) {
					currentState.enter(previousState);
					previousState = currentState;
				}
				
				nextState = currentState.next((char) token);
				
				if(nextState != currentState) {
					currentState.exit(nextState);
					currentState = nextState;
				}
				
				if(token == '\n') {
					lineNumber++;
					columnsNumber = 0;
				}
			}
		} catch(XmlParserException e) {
			e.lineNumber = lineNumber;
			e.columnNumber = columnsNumber;
			throw e;
		}
		return handler.done();
	}

	public int getColumnsNumber() {
		return columnsNumber;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getPosition() {
		return position;
	}
	
}
