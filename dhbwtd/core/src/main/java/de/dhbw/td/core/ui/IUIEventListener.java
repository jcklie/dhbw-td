/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */  
package de.dhbw.td.core.ui;

import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;

public interface IUIEventListener {
	public EUserAction onClick(ButtonEvent event);
	public EUserAction onKey(Event event);
}
