/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.core.waves;

import java.util.Queue;

public class WaveController {

	public final Queue<Wave> waves;
	public final int numberOfWaves;
	public final double timeSinceLastWave;
	public final Wave currentWave;
	
	public WaveController(Queue<Wave> waves, int numberOfWaves, double timeSinceLastWave, Wave currentWave){
		this.waves = waves;
		this.numberOfWaves = numberOfWaves;
		this.timeSinceLastWave = timeSinceLastWave;
		this.currentWave = currentWave;
	}
	
	public void nextWave(){
				
	}
	
	public void draw(){
		
	}
}