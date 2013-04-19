package de.dhbw.td.backup;

import playn.core.Image;

class TowerLevel {

	final int range;
	final int damage;
	final int price;
	final Image image;
	
	TowerLevel(int range, int damage, int price, Image image) {
		this.range = range;
		this.damage = damage;
		this.price = price;
		this.image = image;
	}
}
