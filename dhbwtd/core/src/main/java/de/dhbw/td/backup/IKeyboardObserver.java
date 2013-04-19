/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.backup;

import playn.core.Keyboard.Event;

/**
 * 
 * @author Benedict Holste <benedict@bholste.net>
 *
 */
public interface IKeyboardObserver {
	/**
	 * 
	 * @param e
	 */
	public void alert(Event e);
}
