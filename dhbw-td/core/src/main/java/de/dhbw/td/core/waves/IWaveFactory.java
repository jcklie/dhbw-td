/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.waves;

import playn.core.Json;

public interface IWaveFactory {
	
	WaveController nextWaveController(String jsonString);
	WaveController nextWaveController(Json.Object parsedJson);
}
