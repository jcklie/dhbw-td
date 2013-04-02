package de.dhbw.td.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;
import playn.core.Image;
import playn.core.Json;

public class SimpleLevelFactory implements ILevelFactory {

	@Override
	public Level loadLevel(String jsonString) {
		return loadLevel(json().parse(jsonString));
	}

	@Override
	public Level loadLevel(Json.Object parsedJson) {		
		int width = parsedJson.getInt("width");
		int height = parsedJson.getInt("height");
		int tilesize = parsedJson.getInt("tilesize");
		
		Json.Array grid = parsedJson.getArray("tiles");
		final Image[][] tileMap = new Image[height][width];
		
		for(int row = 0; row < height; row++) {
			Json.Array gridRow = grid.getArray(row);
			
			for(int col = 0; col < width; col++) {
				int tileID = gridRow.getInt(col);
				String pathToImage = ETileType.getPathToImage(tileID);
				tileMap[row][col] = assets().getImage(pathToImage);
			}
		}
		return new Level(tileMap, tilesize, width, height);
	}
	

}
