package de.dhbw.td.core.level;

import playn.core.Json;

public interface ILevelFactory {
	
	Level loadLevel(String jsonString);
	Level loadLevel(Json.Object parsedJson);

}
