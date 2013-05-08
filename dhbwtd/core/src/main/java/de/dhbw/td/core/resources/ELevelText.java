/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */

package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_LEVELS;
import playn.core.Json;


public enum ELevelText {
	
	LEVEL1("level1.json"),
	LEVEL2("level2.json"),
	LEVEL3("level3.json"),
	LEVEL4("level4.json"),
	LEVEL5("level5.json"),
	LEVEL6("level6.json");
	
	public final Json.Object parsedJson;
	
	public static Json.Object getLevelJson(int levelNumber) {
		switch (levelNumber) {
		case 1: return LEVEL1.parsedJson;
		case 2: return LEVEL2.parsedJson;
		case 3: return LEVEL3.parsedJson;
		case 4: return LEVEL4.parsedJson;
		case 5: return LEVEL5.parsedJson;
		case 6: return LEVEL6.parsedJson;
		default:
			throw new IllegalArgumentException("There is no level with number " + levelNumber);
		}
	}
	
	ELevelText(String pathToImage) {
		parsedJson = ResourceLoader.getJSON(PATH_LEVELS + pathToImage);
	}
	

}
