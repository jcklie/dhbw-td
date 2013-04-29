package de.dhbw.td.core.tower;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import playn.core.Json;
import playn.core.Json.Array;
import pythagoras.i.Point;
import de.dhbw.td.core.resources.ETowerText;
import de.dhbw.td.core.util.EFlavor;


/**
 * 
 * @author Lukas Berg
 */
public class TowerFactory {

	public Tower constructTower(EFlavor flavor, Point tileposition) {
		Json.Object jsonTower =  ETowerText.getTowerJsonByFlavor(flavor);
		Point pixelposition = new Point(tileposition.x * TILE_SIZE , tileposition.y * TILE_SIZE);

		Array jsonLevels = jsonTower.getArray("levels");
		int levelCount = jsonTower.getInt("levelCount");
		TowerLevel[] levels = new TowerLevel[levelCount];

		for (int i = 0; i < levelCount; i++) {
			levels[i] = getLevel(jsonLevels.getObject(i));
		}
		
		return new Tower(flavor, pixelposition, levels, jsonTower.getDouble("cadenza"));
	}
	
	public Tower constructTower(EFlavor flavor, int tilex, int tiley) {
		return constructTower(flavor, new Point(tilex, tiley));
	}

	private TowerLevel getLevel(Json.Object level) {
		return new TowerLevel(level.getInt("range"), level.getInt("damage"), 
				level.getInt("price"));
	}



}