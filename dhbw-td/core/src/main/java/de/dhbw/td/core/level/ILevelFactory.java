/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core.level;

import playn.core.Json;

public interface ILevelFactory {
	
	Level loadLevel(String jsonString);
	Level loadLevel(Json.Object parsedJson);

}
