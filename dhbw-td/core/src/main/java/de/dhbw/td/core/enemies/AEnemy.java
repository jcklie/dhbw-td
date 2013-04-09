/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.enemies;

import java.awt.Point;
import java.util.Queue;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.*;

/**
 * abstract class for an enemy
 * 
 * @author Martin Kiessling, Tobias Roeding
 * @version 1.0
 * 
 */
public abstract class AEnemy implements IDrawable, IUpdateable {
	protected int maxHealth;
	protected int curHealth;
	protected boolean alive;
	protected double speed;
	protected int bounty;
	protected int penalty;
	protected EEnemyType enemyType;
	protected Queue<Point> waypoints;
	protected Point currentPosition;
	protected Image enemyImage;
	protected EDirection currentDirection;
	protected Point currentWaypoint;

	public enum EEnemyType {
		Math, TechInf, Code, TheoInf, Wiwi, Social;
	}

	@Override
	public void draw(Surface surf) {
		surf.clear();
		surf.drawImage(enemyImage, currentPosition.x, currentPosition.y);
	}

	@Override
	public void update(double delta) {
		currentWaypoint = waypoints.peek();
		if (currentPosition == currentWaypoint) {
			Point newWaypoint = waypoints.poll();
			if (currentPosition.x < newWaypoint.x) {
				currentDirection = EDirection.RIGHT;
			} else if (currentPosition.x > newWaypoint.x) {
				currentDirection = EDirection.LEFT;
			} else if (currentPosition.y < newWaypoint.y) {
				currentDirection = EDirection.DOWN;
			} else if (currentPosition.y < newWaypoint.y) {
				currentDirection = EDirection.UP;
			} 
			currentWaypoint = newWaypoint;
		}
		switch (currentDirection) {
		case DOWN:
			currentPosition.translate(0, (int) (speed * delta / 1000));
			if (currentPosition.y > currentWaypoint.y) {
				currentPosition = currentWaypoint;
			}
			break;
		case LEFT:
			currentPosition.translate((int) (-speed * delta / 1000), 0);
			if (currentPosition.x < currentWaypoint.x) {
				currentPosition = currentWaypoint;
			}
			break;
		case RIGHT:
			currentPosition.translate((int) (speed * delta / 1000), 0);
			if (currentPosition.x > currentWaypoint.x) {
				currentPosition = currentWaypoint;
			}
			break;
		case UP:
			currentPosition.translate(0, (int) (-speed * delta / 1000));
			if (currentPosition.y < currentWaypoint.y) {
				currentPosition = currentWaypoint;
			}
			break;
		default:
			break;
		}
	}

	public void takeDamage(int damage) {
		if ((curHealth -= damage) < 0) {
			alive = false;
		}
	}

	/**
	 * 
	 * @return current position as Point
	 */
	public Point getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * 
	 * @return current Health as integer
	 */
	public int getCurHealth() {
		return curHealth;
	}

	/**
	 * 
	 * @return speed as double
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 
	 * @return get maximum health as integer
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * 
	 * @return boolean if enemy is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * 
	 * @return get bounty of enemy as integer
	 */
	public int getBounty() {
		return bounty;
	}

	/**
	 * 
	 * @return get penalty of enemy as integer
	 */
	public int getPenalty() {
		return penalty;
	}

	/**
	 * 
	 * @return get enemy type as EEnemyType
	 */
	public EEnemyType getEnemyType() {
		return enemyType;
	}

	/**
	 * 
	 * @return get waypoint queue as Queue<Point>
	 */
	public Queue<Point> getWaypoints() {
		return waypoints;
	}
}
