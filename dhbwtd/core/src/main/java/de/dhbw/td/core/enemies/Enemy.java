/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 *  Jan-Christoph Klie - Refactored Healthbar to extern class and refactored everything else, too
 */

package de.dhbw.td.core.enemies;

import java.util.LinkedList;
import java.util.Queue;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.Point;
import static playn.core.PlayN.log;

/**
 * Enemies are the things tower kill for money
 * 
 * @author Martin Kiessling, Tobias Roeding, Jan-Christoph Klie
 * 
 */
public class Enemy implements IUpdateable {

	private final int bounty;
	private final int penalty;
	private final int maxHealth;
	private final double speed;
	private final EFlavor enemyType;

	private int curHealth;
	private boolean alive;
	private Queue<Point> waypoints;
	private Point currentPosition;
	private Image enemyImage;
	private Image healthBarImage;
	private EDirection currentDirection;
	private Point currentWaypoint;
	private LinkedList<Point> fixedWaypoints;

	public Enemy(int maxHealth, double speed, int bounty, EFlavor enemyType, Queue<Point> waypoints, Image enemyImage) {
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		this.alive = true;
		this.speed = speed;
		this.bounty = bounty;
		this.penalty = bounty * 2;
		this.enemyType = enemyType;
		this.fixedWaypoints = (LinkedList<Point>) waypoints;
		this.waypoints = Level.copyWaypoints(waypoints);
		this.currentPosition = this.waypoints.peek();
		this.currentWaypoint = this.waypoints.poll();
		this.enemyImage = enemyImage;
		this.currentDirection = EDirection.RIGHT;
		currentPosition.setLocation(currentWaypoint);
		healthBarImage = HealthBar.getHealthStatus(1);
	}

	@Override
	public void update(double delta) {
		if (alive()) {
			if (currentPosition.equals(currentWaypoint)) {
				takeDamage(1);
				Point nextWaypoint = waypoints.poll();
				if (nextWaypoint == null) {
					for (Point p : fixedWaypoints) {
						waypoints.add(new Point(p));
					}
					nextWaypoint = waypoints.poll();
					currentPosition.setLocation(nextWaypoint);
				}
				adjustDirection(nextWaypoint);
				currentWaypoint = nextWaypoint;
			}
			switch (currentDirection) {
			case DOWN:
				handleDown(delta);
				break;
			case LEFT:
				handleLeft(delta);
				break;
			case RIGHT:
				handleRight(delta);
				break;
			case UP:
				handleUp(delta);
				break;
			default:
				throw new IllegalStateException("I should never be thrown!");
			}
		}
	}

	private void adjustDirection(final Point newWaypoint) {
		if (currentPosition.x() < newWaypoint.x()) {
			currentDirection = EDirection.RIGHT;
		} else if (currentPosition.x() > newWaypoint.x()) {
			currentDirection = EDirection.LEFT;
		} else if (currentPosition.y() < newWaypoint.y()) {
			currentDirection = EDirection.DOWN;
		} else if (currentPosition.y() > newWaypoint.y()) {
			currentDirection = EDirection.UP;
		}
	}

	private int distanceMovedSince(double delta) {
		return (int) (speed * delta / 1000);
	}

	private void handleDown(double delta) {
		currentPosition.translate(0, distanceMovedSince(delta));
		if (currentPosition.y() > currentWaypoint.y()) {
			currentPosition.setLocation(currentWaypoint);
		}
	}

	private void handleLeft(double delta) {
		currentPosition.translate(-distanceMovedSince(delta), 0);
		if (currentPosition.x() < currentWaypoint.x()) {
			currentPosition.setLocation(currentWaypoint);
		}
	}

	private void handleRight(double delta) {
		currentPosition.translate(distanceMovedSince(delta), 0);
		if (currentPosition.x() > currentWaypoint.x()) {
			currentPosition.setLocation(currentWaypoint);
		}
	}

	private void handleUp(double delta) {
		currentPosition.translate(0, -distanceMovedSince(delta));
		if (currentPosition.y() < currentWaypoint.y()) {
			currentPosition.setLocation(currentWaypoint);
		}
	}

	public void takeDamage(int damage) {
		curHealth -= damage;
		double relativeHealth = (double) curHealth / (double) maxHealth;

		if (curHealth <= 0) {
			die();
		} else {
			healthBarImage = HealthBar.getHealthStatus(relativeHealth);
		}
	}

	/**
	 * Kills the enemy
	 */
	private void die() {
		this.alive = false;
	}

	/**
	 * 
	 * @return current position as Point
	 */
	public Point position() {
		return currentPosition;
	}

	/**
	 * 
	 * @return current Health as integer
	 */
	public int curHealth() {
		return curHealth;
	}

	/**
	 * 
	 * @return speed as double
	 */
	public double speed() {
		return speed;
	}

	/**
	 * 
	 * @return get maximum health as integer
	 */
	public int maxHealth() {
		return maxHealth;
	}

	/**
	 * 
	 * @return boolean if enemy is alive
	 */
	public boolean alive() {
		return alive;
	}

	/**
	 * 
	 * @return get bounty of enemy as integer
	 */
	public int bounty() {
		return bounty;
	}

	/**
	 * 
	 * @return get penalty of enemy as integer
	 */
	public int penalty() {
		return penalty;
	}

	/**
	 * 
	 * @return get enemy type as EEnemyType
	 */
	public EFlavor enemyType() {
		return enemyType;
	}

	/**
	 * 
	 * @return get waypoint queue as Queue<Point>
	 */
	public Queue<Point> waypoints() {
		return waypoints;
	}
}
