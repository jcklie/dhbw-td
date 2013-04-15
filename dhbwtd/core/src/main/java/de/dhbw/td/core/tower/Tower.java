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
	
	private int level;
	
	
	private final EFlavor flavor;
	private final Point position;
	private final TowerLevel[] levels;
	private final double shotRate;
	
	private double lastShot;
	private Enemy target;
	private List<Enemy> enemies = new LinkedList<Enemy>();
	
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
	}

	@Override
	public void update(double delta) {

		lastShot += delta;

		if (lastShot >= shotRate) {
			if (target != null && (!enemies.contains(target) || !inRange(target))) {
				target = null;
			}

			if (target == null) {
				double minDistance = -1;
				for (Enemy enemy : enemies) {
					double distance = getDistance(enemy);
					if ((inRange(distance) && distance < minDistance) || target == null) {
						target = enemy;
						minDistance = distance;
					}
				}
			}
			
			if (target != null && inRange(target)) {
				log().debug("Shoting at " + target.getEnemyType() + " Distance " + getDistance(target));
				target.takeDamage(flavor == target.getEnemyType() ? getDamage()*2 : getDamage());
			}
			
			lastShot -= shotRate;
		}
		
		
	}
	
	private double getDistance(Enemy enemy) {
		double d = position.distance(enemy.getCurrentPosition());
		log().debug("Distance " + d + " Range " + getRange());
		return d;
	}
	
	private boolean inRange(Enemy enemy) {
		return inRange(getDistance(enemy));
	}
	
	private boolean inRange(double distance) {
		return distance <= getRange();
	}

}
