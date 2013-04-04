/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.enemies;

import java.util.Queue;

public abstract class AEnemy {
	public int maxHealth;
	public int curHealth;
	public boolean alive;
	public double speed;
	public int bounty;
	public int penalty;
	public EEnemyType enemyType;

	// public Queue<Tuple> waypoints;

	public enum EEnemyType {
		Math(1), TechInf(2), Code(3), TheoInf(4), Wiwi(5), Social(6);

		private int value;

		private EEnemyType(int value) {
			this.value = value;
		}
	}
}
