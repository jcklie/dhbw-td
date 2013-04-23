/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 *  Jan-Christoph Klie - Refactored Healthbar to extern class and refactored everything else, too
 */

package de.dhbw.td.core.enemies;

import pythagoras.i.Point;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;

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
	private Point[] waypoints;
	private Point currentPosition;
	private EDirection currentDirection;
	private int pointerTocurrentWaypoint;

	public Enemy(int maxHealth, double speed, int bounty, EFlavor enemyType, Point[] waypoints) {
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.bounty = bounty;
		this.enemyType = enemyType;		
		this.waypoints = waypoints;	
		
		curHealth = maxHealth;
		alive = true;		
		penalty = bounty * 2;		
		currentDirection = EDirection.RIGHT;		
		pointerTocurrentWaypoint= 0;
		
		currentPosition = new Point(currentWaypoint());
	}
	
	/**
	 * Copy constructor
	 * @param e The enemy which attributes shall be copied
	 */
	public Enemy(Enemy e) {
		this(e.maxHealth, e.speed, e.bounty, e.enemyType, e.waypoints );
		this.currentPosition = e.currentPosition;
	}

	@Override
	public void update(double delta) {
		if (alive()) {			
			if (currentPosition.equals( currentWaypoint())) {
				pointerTocurrentWaypoint++;
				
				/*
				 * If the last waypoint is reached, teleport to
				 * first waypoint
				 */
				if(isLastWaypoint() ) {
					pointerTocurrentWaypoint = 0;
					currentPosition.setLocation(currentWaypoint());
				}

				//takeDamage(1);
				
				adjustDirection(currentWaypoint());
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
	
	private Point currentWaypoint() {
		return waypoints[pointerTocurrentWaypoint];
	}
	
	private boolean isLastWaypoint() {
		return pointerTocurrentWaypoint == waypoints.length - 1;
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
		if (currentPosition.y() > currentWaypoint().y()) {
			currentPosition.setLocation(currentWaypoint());
		}
	}

	private void handleLeft(double delta) {
		currentPosition.translate(-distanceMovedSince(delta), 0);
		if (currentPosition.x() < currentWaypoint().x()) {
			currentPosition.setLocation(currentWaypoint());
		}
	}

	private void handleRight(double delta) {
		currentPosition.translate(distanceMovedSince(delta), 0);
		if (currentPosition.x() > currentWaypoint().x()) {
			currentPosition.setLocation(currentWaypoint());
		}
	}

	private void handleUp(double delta) {
		currentPosition.translate(0, -distanceMovedSince(delta));
		if (currentPosition.y() < currentWaypoint().y()) {
			currentPosition.setLocation(currentWaypoint());
		}
	}

	public void takeDamage(int damage) {
		curHealth -= damage;
		
		if (curHealth <= 0) {
			die();
		}
	}

	/**
	 * Kills the enemy
	 */
	private void die() {
		this.alive = false;
	}

	public Point position() { return currentPosition;	}
	public int curHealth() { return curHealth; }
	public double speed() { return speed;	}
	public int maxHealth() { return maxHealth; }
	public boolean alive() { return alive; }
	public int bounty() { return bounty; }
	public int penalty() { return penalty;	 }
	public EFlavor enemyType() { return enemyType; }
	public Point currentPosition() { return currentPosition; };

}
