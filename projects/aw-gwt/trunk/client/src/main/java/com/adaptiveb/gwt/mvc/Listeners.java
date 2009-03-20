package com.adaptiveb.gwt.mvc;

import java.util.ArrayList;


public class Listeners<L, E extends FireableEvent<L>> {

    ArrayList<L> listeners;

	public void fireEvent(E event) {
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
