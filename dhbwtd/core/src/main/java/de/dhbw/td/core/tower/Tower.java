/*  Copyright (C) 2013 by Lukas Berg Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg - All
 */

package de.dhbw.td.core.tower;

import static playn.core.PlayN.log;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.ui.IDrawable;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.Point;

public class Tower implements IDrawable, IUpdateable {

	private static final int PROJECTILE_SPEED = 450;
	
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
	
	private TowerLevel level() {
		return levels[level];
	}
	
	public EFlavor flavor() {
		return flavor;
	}
	
	public int demage() {
		return level().damage;
	}
	
	public int range() {
		return level().range;
	}
	
	public int price() {
		return level().price;
	}
	
	public Point position() {
		return new Point(position);
	}
	
	public int x() {
		return position.x();
	}
	
	public int y() {
		return position.y();
	}
	
	public List<Projectile> projectiles() {
		return projectiles;
	}
	
	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies == null ? new LinkedList<Enemy>() : enemies;
	}

	@Override
	public void draw(Surface surf) {
		surf.drawImage(level().image, position.x(), position.y());
		
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
			if (target != null && (!target.alive() || !inRange(target))) {
				target = null;
			}

			//Search new target
			if (target == null) {
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
			
			//Check if tower can shoot
			if (target != null && inRange(target)) {
				hasShot = true;	
				log().debug("Shoting at " + target.enemyType() + " Distance " + getDistance(target));
				Projectile p = new Projectile(position(), demage(), flavor, PROJECTILE_SPEED, target, projectile);
				projectiles.add(p);
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

}
