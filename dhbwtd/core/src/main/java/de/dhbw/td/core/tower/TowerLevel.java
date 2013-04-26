package de.dhbw.td.core.tower;

import playn.core.Image;

class TowerLevel {

	public final int range;
	public final int damage;
	public final int price;
	public final Image image;
	
	TowerLevel(int range, int damage, int price, Image image) {
		this.range = range;
		this.damage = damage;
		this.price = price;
		this.image = image;
	}
}
