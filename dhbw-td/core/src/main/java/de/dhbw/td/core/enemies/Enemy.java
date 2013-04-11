/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 *  Jan-Christoph Klie - Refactored Healthbar to extern class and refactored everything
 */

package de.dhbw.td.core.enemies;

import java.awt.Point;
import java.util.Queue;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.util.EDirection;
import de.dhbw.td.core.util.EFlavor;

/**
 * Enemies are the things tower kill for money
 * 
 * @author Martin Kiessling, Tobias Roeding, Jan-Christoph Klie
 * 
 */
public class Enemy implements IDrawable, IUpdateable {
	
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
	private Queue<Point> fixedWaypoints;
	
	public Enemy(int maxHealth, double speed, int bounty, EFlavor enemyType, Queue<Point> waypoints, Image enemyImage) {
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		this.alive = true;
		this.speed = speed;
		this.bounty = bounty;
		this.penalty = bounty * 2;
		this.enemyType = enemyType;
		this.fixedWaypoints = waypoints;
		this.waypoints = Level.copyWaypoints(waypoints);
		this.currentPosition = this.waypoints.peek();
		this.currentWaypoint = this.waypoints.poll();
		this.enemyImage = enemyImage;	
		this.currentDirection = EDirection.RIGHT;
		
		currentPosition.translate((int) -enemyImage.height(), 0);
		healthBarImage = HealthBar.getHealthStatus(1);
	}

	@Override
	public void draw(Surface surf) {
		if (isAlive()) {
			surf.drawImage(enemyImage, currentPosition.x, currentPosition.y);
			surf.drawImage(healthBarImage, currentPosition.x + 7, currentPosition.y + 2);
		}
	}

	@Override
	public void update(double delta) {
		if (isAlive()) {
			if (currentPosition.equals(currentWaypoint)) {
				takeDamage(1);
				Point newWaypoint = waypoints.poll();
				if (newWaypoint == null) {
					die();
					for (Point p : fixedWaypoints) {
						waypoints.add((Point) p.clone());
					}
					newWaypoint = waypoints.poll();
					currentPosition.setLocation(newWaypoint);
				}
				handleNewDirection(newWaypoint);
				currentWaypoint = newWaypoint;
			}
			switch (currentDirection) {
				case DOWN:  handleDown(delta);  break;
				case LEFT:  handleLeft(delta);  break;
				case RIGHT: handleRight(delta); break;
				case UP:    handleUp(delta);    break;
				default: throw new IllegalStateException("I should never be thrown!");
			}
		}
	}
	
	private void handleDown(double delta) {
		currentPosition.translate(0, (int) (speed * delta / 1000));
		if (currentPosition.y > currentWaypoint.y) {
			currentPosition.setLocation(currentWaypoint);
		}
	}
	
	private void handleLeft(double delta) {
		currentPosition.translate((int) (-speed * delta / 1000), 0);
		if (currentPosition.x < currentWaypoint.x) {
			currentPosition.setLocation(currentWaypoint);
		}
	}
	
	private void handleRight(double delta) {
		currentPosition.translate((int) (speed * delta / 1000), 0);
		if (currentPosition.x > currentWaypoint.x) {
			currentPosition.setLocation(currentWaypoint);
		}
	}
	
	private void handleUp(double delta) {
		currentPosition.translate(0, (int) (-speed * delta / 1000));
		if (currentPosition.y < currentWaypoint.y) {
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
	
	private void handleNewDirection(final Point newWaypoint) {
		if (currentPosition.x < newWaypoint.x) {
			currentDirection = EDirection.RIGHT;
		} else if (currentPosition.x > newWaypoint.x) {
			currentDirection = EDirection.LEFT;
		} else if (currentPosition.y < newWaypoint.y) {
			currentDirection = EDirection.DOWN;
		} else if (currentPosition.y > newWaypoint.y) {
			currentDirection = EDirection.UP;
		}
	}

	private void die() {
		this.alive = false;
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
	public EFlavor getEnemyType() {
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
