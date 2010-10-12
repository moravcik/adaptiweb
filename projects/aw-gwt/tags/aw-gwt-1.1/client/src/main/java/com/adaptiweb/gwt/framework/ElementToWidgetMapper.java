package com.adaptiweb.gwt.framework;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gwt.user.client.impl.ElementMapperImpl;
import com.google.gwt.user.client.ui.Widget;

public class ElementToWidgetMapper extends ElementMapperImpl<Widget> {

	@Override
	public Iterator<Widget> iterator() {
		return new Iterator<Widget>() {
			
			final ArrayList<Widget> widgetList = getObjectList();
			int lastIndex = -1;
			int nextIndex = -1;
			
			{
				findNext();
			}

			private void findNext() {
				while (++nextIndex < widgetList.size()) {
					if (widgetList.get(nextIndex) != null) {
						return;
					}
				}
			}
			
			public boolean hasNext() {
				return nextIndex < widgetList.size();
			}

			public Widget next() {
				if (!hasNext()) throw new NoSuchElementException();
				Widget result = widgetList.get(lastIndex = nextIndex);
				findNext();
				return result;
			}

			public void remove() {
				if (lastIndex < 0) throw new IllegalStateException();
				Widget w = widgetList.get(lastIndex);
				w.removeFromParent();
				lastIndex = -1;
			}
		};
	}
}
