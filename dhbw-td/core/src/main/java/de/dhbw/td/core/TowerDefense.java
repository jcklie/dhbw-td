/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  Tobias Roeding - Add support for changing to the next level/wave when finished current
 */

package de.dhbw.td.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.log;
import static playn.core.PlayN.pointer;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.game.HUD;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;

public class TowerDefense implements Game {

	public static final String PATH_LEVELS = "levels/";
	public static final String PATH_IMAGES = "images/";
	public static final String PATH_WAVES = "waves/";
	public static final String PATH_TOWERS = "tower/";
	
	public static final int NUMBER_OF_LEVELS = 6;
	public static final int NUMBER_OF_WAVES = 12;

	private SurfaceLayer TILE_LAYER;
	private ImageLayer BACKGROUND_LAYER;
	private SurfaceLayer HUD_LAYER;

	private SurfaceLayer ENEMY_LAYER;

	private Level currentLevel;
	private LevelFactory levelLoader;

	private WaveController waveController;
	private WaveFactory waveLoader;

	private GameState stateOftheWorld;
	private HUD hud;

	private int levelNumber;

	@Override
	public void init() {
		levelNumber = 1;
		stateOftheWorld = new GameState();
		hud = new HUD(stateOftheWorld);

		loadWaveFactory();
		nextLevel();
		nextWave();
		
		/*
		 * Layer
		 */
		
		Image bg = assets().getImage("tiles/white.bmp");
		BACKGROUND_LAYER = graphics().createImageLayer(bg);
		BACKGROUND_LAYER.setScale(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(BACKGROUND_LAYER);

		// Tile layer
		TILE_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(TILE_LAYER);

		// ENEMY layer
		ENEMY_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(ENEMY_LAYER);

		// HUD layer
		HUD_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(HUD_LAYER);
		
		/*
		 * Register listener
		 */

		addMouseListener();
		addKeyboardListener();		
	}

	private void loadLevel(String pathToLevel, String pathToWaves) {
		try {
			String levelJson = assets().getTextSync(pathToLevel);
			levelLoader = new LevelFactory();
			currentLevel = levelLoader.loadLevel(levelJson);
			String WaveJson = assets().getTextSync(pathToWaves);
			waveController = waveLoader.nextWaveController(WaveJson, currentLevel.waypoints());
		} catch (Exception e) {
			log().error(e.getMessage());
		}
	}

	private void nextWave() {
		if (waveController.hasNextWave()) {
			int waveNumber = waveController.getWaves().peek().getWaveNumber();
			if(waveNumber == 0 || waveNumber == NUMBER_OF_WAVES - 1){
				stateOftheWorld.newWave(waveController.nextWave().getEnemies());
			} else {
				waveController.nextWave();
				nextWave();
			}			
		} else {
			log().info("LevelNumber " + levelNumber);
			nextLevel();
		}
	}

	private void nextLevel() {
		if (levelNumber <= NUMBER_OF_LEVELS) {
			loadLevel(getLevelName(), getWaveName());
			levelNumber++;
			
			//TODO: Hier kommt der Timer rein, nachdem die erste Wave im neuen Level kommt
			
			nextWave();
		}
	}
	
	private String getLevelName() {
		return String.format("%slevel%d.json",PATH_LEVELS, levelNumber);
	}
	
	private String getWaveName() {
		return String.format("%swaves%d.json",PATH_WAVES, levelNumber);
	}

	private void loadWaveFactory() {
		try {
			waveLoader = new WaveFactory();
		} catch (Exception e) {
			log().error(e.getMessage());
		}
	}

	private void addMouseListener() {
		pointer().setListener(new Pointer.Adapter() {

			@Override
			public void onPointerStart(Event event) {
				log().info(String.format("onPointerStart on x=%s y=%s", event.x(), event.y()));
			}

			@Override
			public void onPointerEnd(Pointer.Event event) {
				log().info(String.format("onPointerEnd on x=%s y=%s", event.x(), event.y()));
			}
		});
	}

	private void addKeyboardListener() {
		keyboard().setListener(new Keyboard.Adapter() {
			@Override
			public void onKeyDown(playn.core.Keyboard.Event event) {
				// TODO Auto-generated method stub
				super.onKeyDown(event);
			}

			@Override
			public void onKeyUp(playn.core.Keyboard.Event event) {
				log().info(event.key().toString());
			}
		});
	}

	@Override
	public void paint(float alpha) {
		Surface tileSurface = TILE_LAYER.surface();
		currentLevel.draw(tileSurface);

		Surface enemySurface = ENEMY_LAYER.surface();
		stateOftheWorld.draw(enemySurface);

		Surface hudSurface = HUD_LAYER.surface();
		hud.draw(hudSurface);
	}

	@Override
	public void update(float delta) {
		stateOftheWorld.update(delta);
		if (stateOftheWorld.allEnemiesDead == true) {
			stateOftheWorld.allEnemiesDead = false;
			nextWave();
		}
	}

	@Override
	public int updateRate() {
		return 24;
	}
}
