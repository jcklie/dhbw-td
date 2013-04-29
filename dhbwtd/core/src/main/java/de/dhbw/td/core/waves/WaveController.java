/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.waves;

import java.util.Queue;

/**
 * 
 * @author Martin Kiessling, Tobias Roeding
 * @version 1.0
 * 
 */
public class WaveController {

	private final Queue<Wave> waves;
	private double timeSinceLastWave = 0;
	private Wave currentWave;

	/**
	 * 
	 * @param waves Queue with all waves for single semester
	 */
	public WaveController(Queue<Wave> waves) {
		this.waves = waves;
	}

	/**
	 * 
	 * @return the current Wave
	 */
	public Wave nextWave() {
		currentWave = waves.poll();
		return currentWave;
	}
	
	public boolean hasNextWave(){
		return !waves.isEmpty();
	}

	/**
	 * 
	 * @return timeSinceLastWave as double
	 */
	public double getTimeSinceLastWave() {
		return timeSinceLastWave;
	}

	/**
	 * 
	 * @param timeSinceLastWave represents the time since the last wave
	 */
	public void setTimeSinceLastWave(double timeSinceLastWave) {
		this.timeSinceLastWave = timeSinceLastWave;
	}

	/**
	 * 
	 * @return waves as Queue<Wave>
	 */
	public Queue<Wave> getWaves() {
		return waves;
	}

	/**
	 * 
	 * @return currentWave as Wave
	 */
	public Wave getCurrentWave() {
		return currentWave;
	}
}
