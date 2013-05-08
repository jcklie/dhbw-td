/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All 
 */

package de.dhbw.td.core.fsm;

/**
 * Defines an action, also know as callback or anonymous function. It is ugly but java
 * has no other way of defining them.
 *
 */
public interface IAction {
	
	IAction NONE = new IAction() {

		@Override
		public void execute() {
			return;	
		}
		
	};
	
	void execute();

}
