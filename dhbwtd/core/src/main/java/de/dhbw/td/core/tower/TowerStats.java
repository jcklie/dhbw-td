/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */  

package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.TOWERS;
import playn.core.Json;
import playn.core.Json.Array;
import de.dhbw.td.core.resources.ETowerText;
import de.dhbw.td.core.util.EFlavor;

/**
 * This class is used to read all information about the different towers
 * from the Json files and share them to all classes which need the information
 * @author Lukas Berg, Jan-Christoph Klie
 *
 */
public class TowerStats {
	
	private int[] cadenza;
	private TowerLevel[][] stats;
	
	private static TowerStats instance;
	
	public static TowerStats getInstance() {
		if (instance == null) {
			instance = new TowerStats();
		}
		return instance;
	}

	private TowerStats() {
		stats = new TowerLevel[TOWERS.size()][];
		cadenza = new int[TOWERS.size()];
		
		for (EFlavor flavor : TOWERS) {
			Json.Object jsonTower =  ETowerText.getTowerJsonByFlavor(flavor);

			Array jsonLevels = jsonTower.getArray("levels");
			int levelCount = jsonTower.getInt("levelCount");
			TowerLevel[] levels = new TowerLevel[levelCount];
			
			for (int i = 0; i < levelCount; i++) {
				levels[i] = getLevel(jsonLevels.getObject(i));
			}
			
			int i = flavor.ordinal();
			stats[i] = levels;
			cadenza[i] = jsonTower.getInt("cadenza");
		}
	}
	
	/**
	 * Returns the parsed stats from the given Json
	 * @param level The Json level to parse
	 * @return The level stats
	 */
	private TowerLevel getLevel(Json.Object level) {
		return new TowerLevel(level.getInt("range"), level.getInt("damage"), 
				level.getInt("price"));
	}
	
	/**
	 * Returns the stats of the given level and flavor
	 * @param flavor The flavor of the stats
	 * @param level The level of the stats
	 * @return The level stats
	 */
	private TowerLevel getLevelStats(EFlavor flavor, int level) {
		return stats[flavor.ordinal()][level];
	}
	
	/**
	 * Returns the damage of the flavor in the given level
	 * @param flavor The flavor
	 * @param level The level
	 * @return The damage
	 */
	public int getDamage(EFlavor flavor, int level) {
		return getLevelStats(flavor, level).damage;
	}
	
	/**
	 * Returns the range of the flavor in the given level
	 * @param flavor The flavor
	 * @param level The level
	 * @return The range
	 */
	public int getRange(EFlavor flavor, int level) {
		return getLevelStats(flavor, level).range;
	}
	
	/**
	 * Returns the price of the flavor in the given level to update
	 * @param flavor The flavor
	 * @param level The level
	 * @return The price. If level is out of range -1 is returned
	 */
	public int getPrice(EFlavor flavor, int level) {		
		if(level > stats[flavor.ordinal()].length) {
			return -1;
		}
		return getLevelStats(flavor, level).price;
	}
	
	/**
	 * Returns the cadenza of the flavor
	 * @param flavor The flavor
	 * @return The cadnza
	 */
	public int getCadenza(EFlavor flavor) {
		return cadenza[flavor.ordinal()];
	}
	
	/**
	 * Returns all level stats of a flavor
	 * @param flavor The flavor
	 * @return The level stats
	 */
	TowerLevel[] getTowerLevels(EFlavor flavor) {
		return stats[flavor.ordinal()];
	}
}
