/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.game.HUD;
import de.dhbw.td.core.level.ILevelFactory;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.SimpleLevelFactory;
import de.dhbw.td.core.waves.IWaveFactory;
import de.dhbw.td.core.waves.SimpleWaveFactory;
import de.dhbw.td.core.waves.WaveController;

public class TowerDefense implements Game {
	
	public static final String PATH_LEVELS = "levels/";
	public static final String PATH_IMAGES = "images/";
	public static final String PATH_WAVES = "waves/";
	public static final String PATH_TOWERS = "tower/";
	
	private SurfaceLayer TILE_LAYER;
	private ImageLayer BACKGROUND_LAYER;
	private SurfaceLayer HUD_LAYER;
	
	private Level currentLevel;
	private ILevelFactory levelLoader;
	
	private WaveController waveController;
	private IWaveFactory waveLoader;
	
	private GameState stateOftheWorld;
	private HUD hud;
		
	@Override
	public void init() {	
		// Game State
		stateOftheWorld = new GameState();
		
		// load the first level for test purposes
		loadLevel(PATH_LEVELS + "level1.json");
		
		// load values for all waves
		loadWaveController(PATH_WAVES + "waves.json");
		
		// Background layer is plain white
		Image bg = assets().getImage("tiles/white.bmp");		
		BACKGROUND_LAYER = graphics().createImageLayer(bg);
		BACKGROUND_LAYER.setScale(currentLevel.width(), currentLevel.height());		
		graphics().rootLayer().add(BACKGROUND_LAYER);
		
		// Tile layer 
		TILE_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(TILE_LAYER);
		
		// HUD layer
		hud = new HUD(stateOftheWorld);
		HUD_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		HUD_LAYER.addListener(hud.new HUDListener());
		graphics().rootLayer().add(HUD_LAYER);
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
	
	private void loadWaveController(String pathToWaveValues) {
		try {
			String WaveJson = assets().getTextSync(pathToWaveValues);
			waveLoader = new SimpleWaveFactory();
			waveController = waveLoader.nextWaveController(WaveJson, null);
		} catch (Exception e) {
			log().error(e.getMessage());
		}
	}

	@Override
	public void paint(float alpha) {
		Surface tileSurface = TILE_LAYER.surface();
		currentLevel.draw(tileSurface);
		
		Surface hudSurface = HUD_LAYER.surface();
		hud.draw(hudSurface);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public int updateRate() {
		return 100;
	}
}
