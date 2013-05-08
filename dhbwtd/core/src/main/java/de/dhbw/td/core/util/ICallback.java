/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.core.util;

/**
 * Interface implementing a function callback
 * @param <T> the type of the callback return value
 */
public interface ICallback<T> {
	
	/**
	 * executed when callback is called
	 * @return
	 */
	public T execute();
}
