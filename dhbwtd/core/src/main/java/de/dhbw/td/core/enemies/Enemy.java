/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 *  Jan-Christoph Klie - Refactor
 */

package de.dhbw.td.core.enemies;

import static de.dhbw.td.core.util.GameConstants.*;
import pythagoras.i.Point;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;

/**
 * Enemies are the things tower kill for money
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
	private boolean reachedEnd;
	private Point[] waypoints;
	private Point position;
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
		
		position = new Point(currentWaypoint());
	}
	
	/**
	 * Copy constructor
	 * @param e The enemy which attributes shall be copied
	 */
	public Enemy(Enemy e) {
		this(e.maxHealth, e.speed, e.bounty, e.enemyType, e.waypoints );
		this.position = e.position;
	}

	@Override
	public void update(double delta) {
		if (alive()) {			
			if (position.equals( currentWaypoint())) {
				pointerTocurrentWaypoint++;
				
				/*
				 * If the last waypoint is reached, teleport to
				 * first waypoint
				 */
				if(isLastWaypoint() ) {
					pointerTocurrentWaypoint = 0;
					position.setLocation(currentWaypoint());
					reachedEnd = true;
				}
				
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
	
	public boolean hasReachedEnd() {
		return reachedEnd;
	}
	
	public void breachAcknowledged() {
		reachedEnd = false;
	}

	private void adjustDirection(final Point newWaypoint) {
		if (position.x() < newWaypoint.x()) {
			currentDirection = EDirection.RIGHT;
		} else if (position.x() > newWaypoint.x()) {
			currentDirection = EDirection.LEFT;
		} else if (position.y() < newWaypoint.y()) {
			currentDirection = EDirection.DOWN;
		} else if (position.y() > newWaypoint.y()) {
			currentDirection = EDirection.UP;
		}
	}

	private int distanceMovedSince(double delta) {
		return (int) (speed * delta / 1000);
	}

	private void handleDown(double delta) {
		position.translate(0, distanceMovedSince(delta));
		if (position.y() > currentWaypoint().y()) {
			position.setLocation(currentWaypoint());
		}
	}

	private void handleLeft(double delta) {
		position.translate(-distanceMovedSince(delta), 0);
		if (position.x() < currentWaypoint().x()) {
			position.setLocation(currentWaypoint());
		}
	}

	private void handleRight(double delta) {
		position.translate(distanceMovedSince(delta), 0);
		if (position.x() > currentWaypoint().x()) {
			position.setLocation(currentWaypoint());
		}
	}

	private void handleUp(double delta) {
		position.translate(0, -distanceMovedSince(delta));
		if (position.y() < currentWaypoint().y()) {
			position.setLocation(currentWaypoint());
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
	
	public boolean isOnScreen() {
		int x = position.x;	// X position in pixel
		int y = position.y;	// Y position in pixel
		return x >= 0 && y >= 0 && x <= WIDTH && y <= HEIGHT;
	}
	
	public Point center() {
		int half = TILE_SIZE / 2;
		int centerx = position.x + half;
		int centery = position.y + half;
		return new Point(centerx, centery);
	}

	public Point position() { return position;	}
	public int curHealth() { return curHealth; }
	public double speed() { return speed;	}
	public int maxHealth() { return maxHealth; }
	public boolean alive() { return alive; }
	public int bounty() { return bounty; }
	public int penalty() { return penalty;	 }
	public EFlavor enemyType() { return enemyType; }

}
