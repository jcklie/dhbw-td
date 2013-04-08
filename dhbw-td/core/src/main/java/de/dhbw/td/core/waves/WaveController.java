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

	public static final int NUMBEROFWAVES = 12;
	public final Queue<Wave> waves;
	public double timeSinceLastWave = 0;
	public Wave currentWave;

	/**
	 * 
	 * @param waves
	 *            Queue with all waves for single semester
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
}
