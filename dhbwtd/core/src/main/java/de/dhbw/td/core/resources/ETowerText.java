/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */
package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.*;
import playn.core.Json;
import de.dhbw.td.core.util.EFlavor;

public enum ETowerText {
	
	MATH("math.json"), 
	COMPUTER_ENGINEERING("techinf.json"), 
	PROGRAMMING("code.json"), 
	THEORETICAL_COMPUTER_SCIENCE("theoinf.json"), 
	ECONOMICS("wiwi.json"), 
	SOCIAL("social.json");
	
	private Json.Object parsedJson;
	
	public static Json.Object getTowerJsonByFlavor(EFlavor flavor) {
		ETowerText towerText = ETowerText.valueOf(flavor.name());
		return towerText.parsedJson;
	}
	
	ETowerText(String pathToImage) {
		parsedJson = ResourceLoader.getJSON(PATH_TOWERS + pathToImage);
	}

}
