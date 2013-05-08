/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */

package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.PROJECTILE_SPEED;
import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;

import java.util.LinkedList;
import java.util.List;

import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;

/**
 * Towers are placed on the map to protect the player against the eval enemies.
 * A tower can be upgraded and sold. It creates projectiles which attack the
 * enemies.
 * @author Lukas Berg, Matthias Kehl
 *
 */
public class Tower implements IUpdateable {
	
	private int level;
	
	private final EFlavor flavor;
	private final Point position;
	private final TowerLevel[] levels;
	private final double shotRate;
	
	private double lastShot;
	private boolean hasShot;
	
	private Enemy target;
	private List<Enemy> enemies = new LinkedList<Enemy>();
	
	private List<Projectile> projectiles = new LinkedList<Projectile>();
	
	public Tower(EFlavor flavor, Point position, TowerLevel[] levels, double cadenza) {
		this.position = new Point(position);
		this.levels = levels;
		this.flavor = flavor;
		this.shotRate = (60 * 1000) / cadenza;
	}
	
	/**
	 * Makes an upgrade on the tower
	 */
	public void upgrade() {
		if (canUpgrade()) {
			level++;
		}
	}

	/**
	 * Returns if the tower can be upgraded or not
	 * @return True if tower can be upgraded otherwise false
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

			searchNewTarget();

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
		Projectile p = new Projectile(center(), damage(), flavor, PROJECTILE_SPEED, target);
	//	Projectile p = new Projectile(position(), damage(), flavor, PROJECTILE_SPEED, target);
		projectiles.add(p);
	}
	
	/**
	 * Scans the enemy list for the next enemy which is alive and in range
	 */
	private void searchNewTarget() {
		for (Enemy enemy : enemies) {
			
			if (enemy.alive() && inRange(enemy)) {
				target = enemy;
				return;
			}
		}
		//No enemy found
		target = null;
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
		return position.distance(enemy.position());
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
	public int upgradeCost() { return canUpgrade() ? levels[level+1].price : 0; } 
	public Point position() { return new Point(position); 	}	
	public int x() { return position.x(); }	
	public int y() { return position.y(); }

}
