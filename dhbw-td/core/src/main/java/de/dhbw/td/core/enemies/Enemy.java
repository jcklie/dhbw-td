/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.enemies;

import static playn.core.PlayN.assets;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import playn.core.Image;
import de.dhbw.td.core.util.EDirection;

/**
 * 
 * @author Martin Kiessling, Tobias Roeding
 * @version 1.0
 * 
 */
public class Enemy extends AEnemy {
	public Enemy(int maxHealth, double speed, int bounty, EEnemyType enemyType, Queue<Point> waypoints, Image enemyImage) {
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		this.alive = true;
		this.speed = speed;
		this.bounty = bounty;
		this.penalty = bounty * 2;
		this.enemyType = enemyType;
		this.waypoints = new LinkedList<Point>(waypoints);
		this.currentPosition = this.waypoints.peek();
		this.currentWaypoint = this.waypoints.poll();
		this.enemyImage = enemyImage;	
		this.currentDirection = EDirection.RIGHT;
		for (EHealthBarType e : healthBarTypeArray) {
			String pathToImage = EHealthBarImage.getPathToImage(e);
			this.healthBarImages[e.ordinal()] = assets().getImageSync(pathToImage);
		}
		this.healthBarImage = healthBarImages[10];
	}
}
