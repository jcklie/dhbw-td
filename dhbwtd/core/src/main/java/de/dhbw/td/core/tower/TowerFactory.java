package de.dhbw.td.core.tower;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;
import static playn.core.PlayN.log;

import java.util.HashMap;
import java.util.Map;

import playn.core.Image;
import playn.core.Json;
import playn.core.Json.Array;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.Point;

/**
 * 
 * @author Lukas Berg
 */
public class TowerFactory {

	private static final String PATH_TOWERS = "tower";
	private static final String PATH_PROJECTILES = "tower";

	private Map<EFlavor, Json.Object> loadedTowers = new HashMap<EFlavor, Json.Object>();
	private Map<String, Image> loadedImages = new HashMap<String, Image>();

	public Tower getTower(EFlavor flavor, Point position) {
		Json.Object jsonTower = loadedTowers.get(flavor);

		// Load and parse json of tower if still not loaded
		if (jsonTower == null) {
			log().debug("Load tower: " + flavor);
			try {
				String jsonString = assets().getTextSync(getPathToFile(flavor)+".json");
				jsonTower = json().parse(jsonString);
				loadedTowers.put(flavor, jsonTower);
			} catch (Exception e) {
				log().error(e.getMessage(), e);
				return null;
			}
		}

		Array jsonLevels = jsonTower.getArray("levels");
		int levelCount = jsonTower.getInt("levelCount");
		TowerLevel[] levels = new TowerLevel[levelCount];

		for (int i = 0; i < levelCount; i++) {
			levels[i] = getLevel(jsonLevels.getObject(i));
		}
		
		return new Tower(flavor, position, levels, jsonTower.getDouble("cadenza"), 
				getProjectileImage(jsonTower.getString("projectile")));
	}

	private TowerLevel getLevel(Json.Object level) {
		return new TowerLevel(level.getInt("range"), level.getInt("damage"),
				level.getInt("price"), getTowerImage(level.getString("image")));
	}

	private String getName(EFlavor flavor) {
		switch (flavor) {
		case MATH:
			return "math";
		case COMPUTER_ENGINEERING:
			return "techinf";
		case PROGRAMMING:
			return "code";
		case THEORETICAL_COMPUTER_SCIENCE:
			return "theoinf";
		case ECONOMICS:
			return "wiwi";
		case SOCIAL:
			return "social";
		default:
			throw new IllegalArgumentException("Given flavor is not supported");
		}
	}

	private String getPathToFile(EFlavor flavor) {
		return String.format("%s/%s.json", PATH_TOWERS, getName(flavor));
	}

	private Image getTowerImage(String src) {
		return getImage(String.format("%s/%s", PATH_TOWERS, src));
		
	}
	
	private Image getProjectileImage(String src) {
		return getImage(String.format("%s/%s", PATH_PROJECTILES, src));
	}
	
	private Image getImage(String src) {
		Image image = loadedImages.get(src);
		// Load image of src if still not loaded
		if (image == null) {
			log().debug("Load image: " + src);
			image = assets().getImageSync(src);
			loadedImages.put(src, image);
		}
		return image;
	}
}