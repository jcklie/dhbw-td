/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.enemies;

import java.awt.Point;
import java.util.Queue;

public abstract class AEnemy {
	public int maxHealth;
	public int curHealth;
	public boolean alive;
	public double speed;
	public int bounty;
	public int penalty;
	public EEnemyType enemyType;
	public Queue<Point> waypoints;

	public enum EEnemyType {
		Math(0), TechInf(1), Code(2), TheoInf(3), Wiwi(4), Social(5);

		public final int value;

		private EEnemyType(int value) {
			this.value = value;
		}
	}
}
