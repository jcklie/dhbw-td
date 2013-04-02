package de.dhbw.td.core;

import static playn.core.PlayN.*;

import playn.core.Assets;
import playn.core.Game;
import playn.core.Layer;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.level.ILevelFactory;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.SimpleLevelFactory;

public class TowerDefense implements Game {
	
	private static final String PATH_LEVELS = "levels/";
	
	private SurfaceLayer TILE_LAYER;
	
	private Level currentLevel;
	private ILevelFactory levelLoader;
		
	@Override
	public void init() {	
		// load the first level for test purposes
		loadLevel(PATH_LEVELS + "level1.json");
		
		// Arrange Layer		
		TILE_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(TILE_LAYER);
	}
	
	private void loadLevel(String pathToLevel) {
		try {
			String levelJson = assets().getTextSync(pathToLevel);
			levelLoader = new SimpleLevelFactory();
			currentLevel = levelLoader.loadLevel(levelJson);
		} catch (Exception e) {
			log().error(e.getMessage());
		}
	}

	@Override
	public void paint(float alpha) {
		Surface tileSurface = TILE_LAYER.surface();
		currentLevel.draw(tileSurface);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public int updateRate() {
		return 25;
	}
}
