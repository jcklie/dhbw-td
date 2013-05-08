/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */ 

package de.dhbw.td.core.ui;

import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;

/**
 * 
 */
public interface IUIEventListener {
	/**
	 * 
	 * @param event
	 * @return
	 */
	public EUserAction onClick(ButtonEvent event);
	
	/**
	 * 
	 * @param event
	 * @return
	 */
	public EUserAction onKey(Event event);
}
