/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.core.event;

import playn.core.Mouse.ButtonEvent;

/**
 * 
 * @author Benedict Holste <benedict@bholste.net>
 *
 */
public interface IMouseObserver {
	/**
	 * 
	 * @param e
	 */
	public void alert(ButtonEvent e);
}
