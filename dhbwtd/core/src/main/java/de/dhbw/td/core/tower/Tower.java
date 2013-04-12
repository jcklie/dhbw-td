/*  Copyright (C) 2013 by Lukas Berg Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg - All
 */

package de.dhbw.td.core.tower;

import java.awt.Point;

import playn.core.Surface;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;

public class Tower implements IDrawable, IUpdateable {
	
	private int level;
	
	private final Point position;
	private final TowerLevel[] levels;
	private final double cadenza;
	
	private double lastShot;
	private Enemy target;
	
	public Tower(Point position, TowerLevel[] levels, double cadenza) {
		this.position = new Point(position);
		this.levels = levels;
		this.cadenza = cadenza;
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
	
	public double getCadenza() {
		return cadenza;
	}
	
	public Point getPosition() {
		return new Point(position);
	}

	@Override
	public void draw(Surface surf) {
		surf.drawImage(getLevel().image, position.x, position.y);
	}

	@Override
	public void update(double delta) {
		/* TODO Let towers shoot */
	}

}
