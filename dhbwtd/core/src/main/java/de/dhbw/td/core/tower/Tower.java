/*  Copyright (C) 2013 by Lukas Berg Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg - All
 */

package de.dhbw.td.core.tower;

import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import playn.core.Surface;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.Point;

public class Tower implements IDrawable, IUpdateable {
	
	private static final int PROJECTILE_SPEED = 600;
	
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
		shotRate = (60 * 1000) / cadenza;
	}
	
	public boolean canUpgrade() {
		return level < levels.length - 1; 
	}
	
	public void upgrade() {
		if (canUpgrade()) {
			level++;
		}
	}
	
	public void setTarget(Enemy target) {
		this.target = target;
	}
	
	private TowerLevel getLevel() {
		return levels[level];
	}
	
	public int getDamage() {
		return getLevel().damage;
	}
	
	public int getRange() {
		return getLevel().range;
	}
	
	public int getPrice() {
		return getLevel().price;
	}
	
	public Point getPosition() {
		return new Point(position);
	}
	
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies == null ? new LinkedList<Enemy>() : enemies;
	}

	@Override
	public void draw(Surface surf) {
		surf.drawImage(getLevel().image, position.getX(), position.getY());
		
		for (Projectile projectile : projectiles) {
			projectile.draw(surf);
		}
	}

	@Override
	public void update(double delta) {		
		lastShot += delta;

		//Check if time to shoot or not has been shot in last interval
		if (lastShot >= shotRate || !hasShot) {
		
			//Check if last target is still valid
			if (target != null && (!target.isAlive() || !inRange(target))) {
				target = null;
			}

			//Search new target
			if (target == null) {
				double minDistance = -1;
				
				//Search the nearest enemy which is in range
				for (Enemy enemy : enemies) {
					double distance = getDistance(enemy);
					if ((inRange(distance) && distance < minDistance) || target == null) {
						target = enemy;
						minDistance = distance;
					}
				}
			}
			
			//Check if tower can shoot
			if (target != null && inRange(target)) {
				hasShot = true;	
				log().debug("Shoting at " + target.getEnemyType() + " Distance " + getDistance(target));
				projectiles.add(new Projectile(getPosition(), getDamage(), PROJECTILE_SPEED, target, getLevel().image));
			} else {
				hasShot = false;
			}
			
			lastShot = 0;
		}
		
		//Update projectiles and delete all projectiles which has hit a tower
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
	
	/**
	 * Calculates the distance between an enemy and the tower
	 * @param enemy The enemy
	 * @return The calculated distance
	 */
	private double getDistance(Enemy enemy) {
		return position.distance(enemy.getCurrentPosition());
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
		return distance <= getRange();
	}

}
