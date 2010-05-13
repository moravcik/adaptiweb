package com.adaptiweb.gwt.mvc;

import java.util.ArrayList;


public class Listeners<L> {

    ArrayList<L> listeners;

	public void fireEvent(FireableEvent<L> event) {
		if (listeners != null)
	        for (L listener : listeners)
	        	event.fireOnListener(listener);
    }
	
	public void addListener(L l) {
		if(listeners == null)
			listeners = new ArrayList<L>();
		listeners.add(l);
	}
	
	public void removeListener(L l) {
		if(listeners != null)
			listeners.remove(l);
	}
}
