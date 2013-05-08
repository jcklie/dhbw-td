/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - All
 */

package de.dhbw.td.core.util;

/**
 *
 * @param <T>
 */
public interface ICallback<T> {
	
	/**
	 * 
	 * @return
	 */
	public T execute();
}
