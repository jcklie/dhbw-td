/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.core.event;

import java.util.ArrayList;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import de.dhbw.td.core.event.IMouseObserver;

public class MouseObservable extends Mouse.Adapter {
	
	private ArrayList<IMouseObserver> observers;
	
	/**
	 * Constructor
	 */
	public MouseObservable() {
		observers = new ArrayList<IMouseObserver>();
	}
	
	@Override
	public void onMouseDown(ButtonEvent event) {
		alertObservers(event);
	}
	
	/**
	 * Adds the specified object to the list of observers
	 * 
	 * @param observer The object to add
	 */
	public void addObserver(IMouseObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Removes the specified observer 
	 * 
	 * @param observer The observer to remove
	 */
	public void removeObserver(IMouseObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Alerts all subscribed observers about the fired mouse event
	 * 
	 * @param e The ButtonEvent fired by the mouse
	 */
	private void alertObservers(ButtonEvent e) {
		for(IMouseObserver observer : observers) {
			observer.alert(e);
		}
	}
}