/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import de.dhbw.td.core.level.SimpleLevelFactory;

/**
 * Unit test for the dhbw-td. It should be run after each checking and each
 * compiling.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {  WaveControllerTest.class, SimpleLevelFactoryTest.class })
public class AppTest{


}