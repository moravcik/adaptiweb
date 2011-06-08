package com.adaptiweb.gwt.tracker;

import com.adaptiweb.gwt.tracker.impl.GoogleAnalyticsTracker;
import com.adaptiweb.gwt.tracker.impl.LogTracker;
import com.google.inject.Provider;

public class TrackerProvider implements Provider<Tracker> {

	@Override
	public Tracker get() {
		return isGoogleAnalyticsEnabled() ? new GoogleAnalyticsTracker() : new LogTracker();
	}
	
	native boolean isGoogleAnalyticsEnabled() /*-{
		return typeof $wnd._gaq != "undefined";
	}-*/;

}
