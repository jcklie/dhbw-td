/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */

package de.dhbw.td.core.tower;

import pythagoras.d.Vector;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EFlavor;

/**
 * Projectile is created by a tower when it shoots at an enemy. 
 * It flies to the given target and deals damage
 * @author Lukas Berg, Matthias Kehl
 *
 */
public class Projectile implements IUpdateable {
	
	private final int damage;
	private final double speed;
	private final Enemy target;
	private final EFlavor flavor;
	
	private boolean hit;
	
	private Point currentPosition;
	
	/**
	 * Creates a new projectile with the given parameters
	 * @param position The start position
	 * @param damage The damage the projectile deals
	 * @param flavor The flavor
	 * @param speed The speed 
	 * @param target The target
	 */
	public Projectile(Point position, int damage, EFlavor flavor, double speed, Enemy target) {
		this.currentPosition = new Point(position);
		this.damage = damage;
		this.flavor = flavor;
		this.speed = speed;
		this.target = target;
	}

	@Override
	public void update(double delta) {
		//Check if target is still alive
		if (!target.alive()) {
			hit = true;
			return;
		}
		
		//Calculates the move vector
		Vector vector = vector();
		vector = vector.scale(speed / vector.length());
		vector.set(vector.x * delta / 1000, vector.y * delta / 1000);
		
		//Check if projectile will hit the target, otherwies move the projectile
		if (enemyWasHit(vector)) {
			hit = true;
			target.takeDamage(calcDamage(damage));
		} else {
			currentPosition.translate((int) vector.x, (int) vector.y);
		}
	}
	
	/**
	 * Calculates the damage the projectile deals. If projectile and target
	 * has the same flavor damage is doubled
	 * @param damage The normal damage
	 * @return The calculated damage
	 */
	private int calcDamage(int damage) {
		return flavor == target.enemyType() ? 2 * damage : damage;
	}
	
	/**
	 * Checks if the target was hit by the projectile
	 * @param v The vector from the projectile to the target
	 * @return True if target was hit otherwise false
	 */
	private boolean enemyWasHit(Vector v) {
		return Math.abs(target.center().x() - currentPosition.x()) <= Math.abs(v.x) &&
				Math.abs(target.center().y() - currentPosition.y()) <= Math.abs(v.y);
	}

	/**
	 * Calculates vector between target and current position
	 * @return The calculated vector
	 */
	private Vector vector() {
		return new Vector(target.center().x() - currentPosition.x(), 
				target.center().y() - currentPosition.y());
	}

	/**
	 * Returns if projectile has hit a tower
	 * @return True if target was hit, otherwise false
	 */
	public boolean hasHit() {
		return hit;
	}
	
	public int x() { return currentPosition.x; }
	public int y() { return currentPosition.y; }
	public double angle() { return vector().angle(); }

}
