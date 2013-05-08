/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie + Benedict Holste - All
 */
package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_WAVES;
import playn.core.Json;

public enum EWaveText {
	
	WAVE_1("waves1.json"),
	WAVE_2("waves2.json"),
	WAVE_3("waves3.json"),
	WAVE_4("waves4.json"),
	WAVE_5("waves5.json"),
	WAVE_6("waves6.json");
	
	private Json.Object parsedJson;
	
	public static Json.Object getWaveControllerJson(int levelNumber) {
		switch (levelNumber) {
		case 1: return WAVE_1.parsedJson;
		case 2: return WAVE_2.parsedJson;
		case 3: return WAVE_3.parsedJson;
		case 4: return WAVE_4.parsedJson;
		case 5: return WAVE_5.parsedJson;
		case 6: return WAVE_6.parsedJson;
		default: throw new IllegalArgumentException("There is no waves with number " + levelNumber);
		}
	}
	
	EWaveText(String pathToImage) {
		parsedJson = ResourceLoader.getJSON(PATH_WAVES + pathToImage);
	}


}
