/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg, Matthias Kehl - All
 *  Jan-Christoph Klie - Refactor
 */ 

package de.dhbw.td.core.tower;

/**
 * A single helper class which holds information about the level of a flavor
 * @author Lukas Berg, Matthias Kehl
 *
 */
class TowerLevel {

	public final int range;
	public final int damage;
	public final int price;
	
	TowerLevel(int range, int damage, int price) {
		this.range = range;
		this.damage = damage;
		this.price = price;
	}
}
