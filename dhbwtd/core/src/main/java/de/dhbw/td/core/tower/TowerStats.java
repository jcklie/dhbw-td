package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.TOWERS;
import playn.core.Json;
import de.dhbw.td.core.resources.ETowerText;
import de.dhbw.td.core.util.EFlavor;

public class TowerStats {
	
	private int[][] dmg;
	private int[][] range;
	private int[][] price;
	private int[] speed;
	
	public TowerStats() {
		initDmg();		
		initRange();
		initPrice();
		initSpeed();
	}
	
	private void initDmg() {
		dmg = new int[6][];
		for(EFlavor flavor : TOWERS) {
			Json.Object json = ETowerText.getTowerJsonByFlavor(flavor);
			Json.Array arr = json.getArray("levels");
			int lvlCount = json.getInt("levelCount");
			int i = flavor.ordinal();
			dmg[i] = new int[lvlCount];
			for( int level = 0 ; level < lvlCount; level++) {
				dmg[i][level] = arr.getObject(level).getInt("damage");
			}
		}		
	}
	
	private void initRange() {
		range = new int[6][];
		for(EFlavor flavor : TOWERS) {
			Json.Object json = ETowerText.getTowerJsonByFlavor(flavor);
			Json.Array arr = json.getArray("levels");
			int lvlCount = json.getInt("levelCount");
			int i = flavor.ordinal();
			range[i] = new int[lvlCount];
			for( int level = 0 ; level < lvlCount; level++) {
				range[i][level] = arr.getObject(level).getInt("range");
			}
		}		
	}

	private void initPrice() {
		price = new int[6][];
		for(EFlavor flavor : TOWERS) {
			Json.Object json = ETowerText.getTowerJsonByFlavor(flavor);
			Json.Array arr = json.getArray("levels");
			int lvlCount = json.getInt("levelCount");
			int i = flavor.ordinal();
			price[i] = new int[lvlCount];
			for( int level = 0 ; level < lvlCount; level++) {
				price[i][level] = arr.getObject(level).getInt("price");
			}
		}		
	}
	
	private void initSpeed() {
		speed = new int[6];
		for(EFlavor flavor : TOWERS) {
			Json.Object json = ETowerText.getTowerJsonByFlavor(flavor);
			int i = flavor.ordinal();
			speed[i] = json.getInt("cadenza");
		}	
	}
	
	public int getDamage(EFlavor flavor, int level) {
		return dmg[flavor.ordinal()][level];
	}
	
	public int getRange(EFlavor flavor, int level) {
		return range[flavor.ordinal()][level];
	}
	
	public int getPrice(EFlavor flavor, int level) {
		if(level > price[flavor.ordinal()].length) return -1;
		return price[flavor.ordinal()][level];
	}
	
	public int getCadenza(EFlavor flavor) {
		return speed[flavor.ordinal()];
	}
	


}
