/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Lukas Berg, Matthias Kehl - All
 *  Jan-Christoph Klie - Refactor
 */
package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import pythagoras.i.Point;
import de.dhbw.td.core.util.EFlavor;

/**
 * Class which is used to abstract the mechanism of creating a tower
 * @author Lukas Berg
 *
 */
public class TowerFactory {
	
	private TowerStats stats = TowerStats.getInstance();

	/**
	 * Creates a tower with the given flavor at the specified position
	 * @param flavor The flavor
	 * @param tileposition The position of the tower
	 * @return The created tower
	 */
	public Tower constructTower(EFlavor flavor, Point tileposition) {
		Point pixelposition = new Point(tileposition.x * TILE_SIZE , tileposition.y * TILE_SIZE);		
		
		return new Tower(flavor, pixelposition, stats.getTowerLevels(flavor), 
				stats.getCadenza(flavor));
	}
	
	/**
	 * Creates a tower with the given flavor at the specified position
	 * @param flavor The flavor
	 * @param tilex The x position of the tower
	 * @param tiley The y position of the tower
	 * @return The created tower
	 */
	public Tower constructTower(EFlavor flavor, int tilex, int tiley) {
		return constructTower(flavor, new Point(tilex, tiley));
	}
}