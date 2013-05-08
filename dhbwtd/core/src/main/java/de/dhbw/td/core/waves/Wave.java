/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 *  Jan-Christoph Klie - Refactor and Comments
 */

package de.dhbw.td.core.waves;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.dhbw.td.core.enemies.Enemy;

/**
 * 
 * @author Martin Kiessling, Tobias Roeding, Jan-Christoph Klie
 * @version 1.0
 * 
 */
public class Wave {

	private final int enemyCount;
	private final int waveNumber;
	private List<Enemy> enemies;

	/**
	 * 
	 * @param waveNumber represents the number of the current Wave in the queue
	 * @param enemyList Contains all enemies for this wave
	 */
	public Wave(int waveNumber, List<Enemy> enemyList) {
		this.enemyCount = enemyList.size();
		this.waveNumber = waveNumber;		
		this.enemies = arrangeEnemiesInRankAndFile(enemyList);
	}
	
	/**
	 * We move the enemies off the screen so there is an actual gap between
	 * them. Purpose of it is that they appear one after another on the map
	 * and not stacked.
	 */
	private List<Enemy> arrangeEnemiesInRankAndFile(List<Enemy> enemyList) {
		List<Enemy> movedEnemies = new ArrayList<Enemy>(enemyList.size());
		
		int offset = -256;
		for( Enemy e : enemyList) {
			Enemy movedEnemy = new Enemy(e);
			movedEnemy.position().translate(offset, 0);
			offset -= 2*TILE_SIZE;
			
			movedEnemies.add(movedEnemy);
		}
		
		return movedEnemies;
	}

	/**
	 * 
	 * @return A - defensive copy - of this waves' enemy list
	 */
	public List<Enemy> enemies() {
		return Collections.unmodifiableList(enemies);
	}
	
	public int enemyCount() { return enemyCount; }
	public int waveNumber() { return waveNumber; }
	
	
}
