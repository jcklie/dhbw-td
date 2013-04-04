/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.waves;

import java.util.Queue;

public class WaveController {

	public static final int NUMBEROFWAVES = 36;
	public final Queue<Wave> waves;
	public double timeSinceLastWave = 0;
	public Wave currentWave;

	public WaveController(Queue<Wave> waves) {
		this.waves = waves;
	}

	public Wave nextWave() {
		currentWave = waves.poll();
		return currentWave;
	}
}
