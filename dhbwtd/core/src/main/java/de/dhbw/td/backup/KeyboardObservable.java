/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.backup;

import java.util.ArrayList;

import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;

public class KeyboardObservable extends Keyboard.Adapter {
	
	private ArrayList<IKeyboardObserver> observers;
	
	/**
	 * Constructor
	 */
	public KeyboardObservable() {
		observers = new ArrayList<IKeyboardObserver>();
	}
	
	@Override
	public void onKeyTyped(TypedEvent event) {
		//alertObservers(event);
	}
	
	@Override
	public void onKeyDown(Event event) {
		alertObservers(event);
	}
	
	/**
	 * Adds the specified object to the list of observers
	 * 
	 * @param observer The object to add 
	 */
	public void addObserver(IKeyboardObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Removes the specified observer
	 * 
	 * @param observer
	 */
	public void removeObserver(IKeyboardObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Alerts all subscribed observers about the typed event
	 * 
	 * @param e
	 */
	private void alertObservers(Event e) {
		for(IKeyboardObserver observer : observers) {
			observer.alert(e);
		}
	}
}
