/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core;

import junit.framework.TestCase;

import org.junit.Test;

public class LevelTest extends TestCase {
	

	@Test(expected = ArithmeticException.class)  
	public void test() {  
	  int i = 1/0;
	}  
 

}
