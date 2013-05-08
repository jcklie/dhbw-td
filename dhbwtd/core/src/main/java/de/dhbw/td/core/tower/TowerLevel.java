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
