																						/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
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

	private SurfaceLayer ENEMY_LAYER;

	private Level currentLevel;
	private ILevelFactory levelLoader;

	private WaveController waveController;
	private IWaveFactory waveLoader;

	private GameState stateOftheWorld;
	private HUD hud;

	@Override
	public void init() {
		// GameState
		stateOftheWorld = new GameState();

		// load waveFactory
		loadWaveFactory();

		// load the first level for test purposes

		loadLevel(PATH_LEVELS + "level1.json", PATH_WAVES + "waves.json");
		nextWave();
		
		// Background layer is plain white
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
		hud = new HUD(stateOftheWorld);
		HUD_LAYER = graphics().createSurfaceLayer(currentLevel.width(), currentLevel.height());
		graphics().rootLayer().add(HUD_LAYER);
		
		// set Listener for mouse events
		addMouseListener();
		
		// set Listener for keyboard events
		addKeyboardListener();
;
	}

	private void loadLevel(String pathToLevel, String pathToWaves) {
		try {
			String levelJson = assets().getTextSync(pathToLevel);
			levelLoader = new SimpleLevelFactory();
			currentLevel = levelLoader.loadLevel(levelJson);
			String WaveJson = assets().getTextSync(pathToWaves);
			waveController = waveLoader.nextWaveController(WaveJson, currentLevel.waypoints());
		} catch (Exception e) {
			log().error(e.getMessage());
		}
	}

	private void nextWave() {
		stateOftheWorld.newWave(waveController.nextWave().getEnemies());
	}

	private void loadWaveFactory() {
		try {
			waveLoader = new SimpleWaveFactory();
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
	}

	@Override
	public int updateRate() {
		return 100;
	}
}
