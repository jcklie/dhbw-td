/*  Copyright (C) 2013 by Lukas Berg Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg - All
 */

package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.PROJECTILE_SPEED;
import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;

import java.util.LinkedList;
import java.util.List;

import playn.core.Image;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;


public class Tower implements IUpdateable {
	
	private int level;
	
	private final EFlavor flavor;
	private final Point position;
	private final TowerLevel[] levels;
	private final double shotRate;
	private final Image projectile;
	
	private double lastShot;
	private boolean hasShot;
	
	private Enemy target;
	private List<Enemy> enemies = new LinkedList<Enemy>();
	
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	
	public Tower(EFlavor flavor, Point position, TowerLevel[] levels, double cadenza, Image projectile) {
		this.position = new Point(position);
		this.levels = levels;
		this.flavor = flavor;
		this.shotRate = (60 * 1000) / cadenza;
		this.projectile = projectile;
	}
	
	public void upgrade() {
		if (canUpgrade()) {
			level++;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean canUpgrade() {
		return level < levels.length - 1;
	}

	private TowerLevel getTowerLevel() {
		return levels[level];
	}
	
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies == null ? new LinkedList<Enemy>() : enemies;
	}

	@Override
	public void update(double delta) {		
		lastShot += delta;

		if (hasBulletReady()) {
		
			if (targetIsInvalid()) {
				target = null;
			}
			
			if( target == null ) {
				searchNewTarget();
			}
			
			if( canShoot()) {
				shoot();
				hasShot = true;	
			} else {
				hasShot = false;
			}
			
			lastShot = 0;
		}
		
		updateProjectiles(delta);
	}

	/**
	 * Check if time to shoot or not has been shot in last interval
	 * @return
	 */
	private boolean hasBulletReady() {
		return lastShot >= shotRate || !hasShot;
	}
	
	/**
	 * Check if last target is still valid. That might be false as a result of a
	 * dead enemy or an enemy out of range
	 * 
	 * @return 
	 */
	private boolean targetIsInvalid() {
		return target != null && (!target.alive() || !inRange(target));
	}
	
	/**
	 * Check if tower can shoot. This may be false if the enemy is not on the 
	 * screen or out of range.
	 * @return
	 */
	private boolean canShoot() {
		return target != null && inRange(target) && target.isOnScreen();
	}
	
	/**
	 * Adds a new projectile to this tower which traces the current target.
	 */
	private void shoot() {
		Projectile p = new Projectile(center(), damage(), flavor, PROJECTILE_SPEED, target, projectile);
		projectiles.add(p);
	}
	
	/**
	 * Scans the enemy list for the closest enemy
	 */
	private void searchNewTarget() {

		double minDistance = -1;
		
		//Search the nearest enemy which is in range
		for (Enemy enemy : enemies) {
			double distance = getDistance(enemy);

			if ((enemy.alive() && inRange(distance)) && (distance < minDistance || target == null)) {
				target = enemy;
				minDistance = distance;						
			}
		}
	}
	
	/**
	 * Update projectiles and delete all projectiles which has hit
	 * @param delta The time passed between the last update
	 */
	private void updateProjectiles(double delta) {
		int i = 0;
		while(i < projectiles.size()) {
			Projectile p = projectiles.get(i);
			p.update(delta);

			if (p.hasHit()) {
				projectiles.remove(p);
			} else {
				i++;
			}
		}
	}

	public Point center() {
		int half = TILE_SIZE / 2;
		int centerx = position.x + half;
		int centery = position.y + half;
		return new Point(centerx, centery);
	}
	
	/**
	 * Calculates the distance between an enemy and the tower
	 * @param enemy The enemy
	 * @return The calculated distance
	 */
	private double getDistance(Enemy enemy) {
		return position.distance(enemy.center());
	}
	
	/**
	 * Checks if the enemy is in range of the tower
	 * @param enemy The enemy
	 * @return True if enemy can be shot by the tower, otherwise false	
	 */
	private boolean inRange(Enemy enemy) {
		return inRange(getDistance(enemy));
	}
	
	/**
	 * Checks if given distance is lower than range
	 * @param distance The distance
	 * @return True if distance is small enough, otherwise false
	 */
	private boolean inRange(double distance) {
		return distance <= range();
	}
	
	public List<Projectile> projectiles() { return projectiles; }
	public int level() { return level; }	
	public EFlavor flavor() { return flavor; }	
	public int damage() { return getTowerLevel().damage; }	
	public int range() { return getTowerLevel().range; }
	public int price() { return getTowerLevel().price; }	
	public Point position() { return new Point(position); 	}	
	public int x() { return position.x(); }	
	public int y() { return position.y(); }

}
