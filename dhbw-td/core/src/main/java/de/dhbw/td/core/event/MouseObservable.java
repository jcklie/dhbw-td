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
	
	public MouseObservable() {
		observers = new ArrayList<IMouseObserver>();
	}
	
	@Override
	public void onMouseDown(ButtonEvent event) {
		alertObservers(event);
	}
	
	public void addObserver(IMouseObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(IMouseObserver observer) {
		observers.remove(observer);
	}
	
	private void alertObservers(ButtonEvent e) {
		for(IMouseObserver observer : observers) {
			observer.alert(e);
		}
	}
}