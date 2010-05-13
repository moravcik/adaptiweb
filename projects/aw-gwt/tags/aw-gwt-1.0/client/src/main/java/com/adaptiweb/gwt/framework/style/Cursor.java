package com.adaptiweb.gwt.framework.style;

import com.google.gwt.user.client.Element;

/**
 * <b>CSS cursor Property.</b><br>
 * The cursor property specifies the type of cursor to be displayed when pointing on an element.<br>
 * <br>Implemented by: <a href="http://www.w3schools.com/CSS/pr_class_cursor.asp">http://www.w3schools.com/CSS/pr_class_cursor.asp</a>
 * 
 * @author <a href="mailto:milan.skuhra@anasoft.sk">Milan Skuhra</a>
 */
public enum Cursor implements Style {
	/**
	 * The default cursor (often an arrow)
	 */
	DEFAULT,  	
	/**
	 * Default. The browser sets a cursor
	 */
	AUTO,
	/**
	 * The cursor render as a crosshair
	 */
	CROSSHAIR, 	
	/**
	 * The cursor render as a pointer (a hand) that indicates a link
	 */
	POINTER,
	/**
	 * The cursor indicates something that should be moved
	 */
	MOVE,
	/**
	 * The cursor indicates that an edge of a box is to be moved right (east)
	 */
	E_RESIZE, 	
	/**
	 * The cursor indicates that an edge of a box is to be moved up and right (north/east)
	 */
	NE_RESIZE, 	
	/**
	 * The cursor indicates that an edge of a box is to be moved up and left (north/west)
	 */
	NW_RESIZE,
	/**
	 * The cursor indicates that an edge of a box is to be moved up (north)
	 */
	N_RESIZE,
	/**
	 * The cursor indicates that an edge of a box is to be moved down and right (south/east)
	 */
	SE_RESIZE,
	/**
	 * The cursor indicates that an edge of a box is to be moved down and left (south/west)
	 */
	SW_RESIZE,
	/**
	 * The cursor indicates that an edge of a box is to be moved down (south)
	 */
	S_RESIZE,
	/**
	 * The cursor indicates that an edge of a box is to be moved left (west)
	 */
	W_RESIZE,
	/**
	 * The cursor indicates text
	 */
	TEXT,
	/**
	 * The cursor indicates that the program is busy (often a watch or an hourglass)
	 */
	WAIT,
	/**
	 * The cursor indicates that help is available (often a question mark or a balloon)
	 */
	HELP,
	;

	public void apply(Element element) {
		element.getStyle().setProperty("cursor", value());
	}

	public String value() {
		return name().replace('_', '-').toLowerCase();
	}
	
	/**
	 * The url of a custom cursor to be used.<br>
	 * <b>Note:</b> Always define a generic cursor at the end of the 
	 * list in case none of the url-defined cursors can be used<br>
	 * 
	 * <b>Example:</b>
	 * <code>cursor : url("first.cur"), url("second.cur"), pointer</code>
	 * 
	 * @param defaultCursor cursor on end of chain is applied if URLs aren't available 
	 * @param urls 
	 * @return
	 */
	public static Style custom(Cursor defaultCursor, String...urls) {
		StringBuffer buffer = new StringBuffer();
		
		for(String url : urls)
			buffer.append("url(\"").append(url).append("), ");
		
		if(defaultCursor == null) {
			if(buffer.length() != 0)
				buffer.setLength(buffer.length() - 2);
		} else
			buffer.append(defaultCursor.value());
		
		final String value = buffer.toString();
		
		return new Style() {

			public void apply(Element element) {
				element.getStyle().setProperty("cursor", value);
			}
		};
	}
	
}
