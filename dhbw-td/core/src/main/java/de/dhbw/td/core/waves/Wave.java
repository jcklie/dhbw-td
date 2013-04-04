/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.waves;

import java.util.Collections;
import java.util.List;

import de.dhbw.td.core.enemies.Enemy;

public class Wave {

	public final int enemyCount;
	public final int waveNumber;
	public final List<Enemy> enemies;

	public Wave(int waveNumber, List<Enemy> enemies) {
		this.enemyCount = enemies.size();
		this.waveNumber = waveNumber;
		this.enemies = enemies;
		enemies = Collections.unmodifiableList(enemies);
	}
}
