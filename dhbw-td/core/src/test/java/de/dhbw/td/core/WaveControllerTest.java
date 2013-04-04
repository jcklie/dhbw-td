/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.core;

import junit.framework.TestCase;
import de.dhbw.td.core.waves.*;

public class WaveControllerTest extends TestCase {

	private Wave wave;	
	private WaveController waveController;
	
	@Override
	protected void setUp() throws Exception {		
		wave = new Wave(10,1);
		waveController = new WaveController(null, 0, 0, wave);
		wave = waveController.nextWave(wave);
	}
	
	@Override
	protected void tearDown() throws Exception {
		wave = null;
	}

	
	public void testWaveWasCorrectAccordingToInputValues() {
		assertEquals(2, wave.waveNumber);
	}  	
}