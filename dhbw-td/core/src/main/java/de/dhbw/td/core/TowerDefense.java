/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  Tobias Roeding - Add support for changing to the next level/wave when finished current
 *  Benedict Holste - Clean up and refactoring
 */

package de.dhbw.td.core;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.mouse;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.event.KeyboardObservable;
import de.dhbw.td.core.event.MouseObservable;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.game.HUD;

public class TowerDefense implements Game {

	public static final String PATH_LEVELS = "levels/";
	public static final String PATH_IMAGES = "images/";
	public static final String PATH_WAVES = "waves/";
	public static final String PATH_TOWERS = "tower/";

	private ImageLayer BACKGROUND_LAYER;
	private SurfaceLayer TILE_LAYER;
	private SurfaceLayer HUD_LAYER;
	private SurfaceLayer ENEMY_LAYER;

	private GameState stateOftheWorld;
	private HUD hud;

	private static MouseObservable mouse;
	private static KeyboardObservable keyboard;

	@Override
	public void init() {
		
		/*
		 * Register listener
		 */

		mouse = new MouseObservable();
		mouse().setListener(mouse);

		keyboard = new KeyboardObservable();
		keyboard().setListener(keyboard);
		
		/*
		 * Init components
		 */
		
		stateOftheWorld = new GameState();
		hud = new HUD(stateOftheWorld);

		/*
		 * Layer
		 */

		int width = stateOftheWorld.getCurrentLevel().width();
		int height = stateOftheWorld.getCurrentLevel().height();

		// BACKGROUND layer
		Image bg = assets().getImage("tiles/white.bmp");
		BACKGROUND_LAYER = graphics().createImageLayer(bg);
		BACKGROUND_LAYER.setScale(width, height);
		graphics().rootLayer().add(BACKGROUND_LAYER);

		// TILE layer
		TILE_LAYER = graphics().createSurfaceLayer(width, height);
		graphics().rootLayer().add(TILE_LAYER);

		// ENEMY layer
		ENEMY_LAYER = graphics().createSurfaceLayer(width, height);
		graphics().rootLayer().add(ENEMY_LAYER);

		// HUD layer
		HUD_LAYER = graphics().createSurfaceLayer(width, height);
		graphics().rootLayer().add(HUD_LAYER);
	}

	@Override
	public void paint(float alpha) {

		Surface tileSurface = TILE_LAYER.surface();
		stateOftheWorld.drawLevel(tileSurface);

		Surface enemySurface = ENEMY_LAYER.surface();
		stateOftheWorld.drawEnemies(enemySurface);

		Surface hudSurface = HUD_LAYER.surface();
		hud.draw(hudSurface);
	}

	@Override
	public void update(float delta) {
		if (!stateOftheWorld.isPaused()) {
			stateOftheWorld.update(delta);
		}
	}

	@Override
	public int updateRate() {
		return 24;
	}

	/**
	 * 
	 * @return
	 */
	public static MouseObservable getMouse() {
		return mouse;
	}

	/**
	 * 
	 * @return 
	 */
	public static KeyboardObservable getKeyboard() {
		return keyboard;
	}
}