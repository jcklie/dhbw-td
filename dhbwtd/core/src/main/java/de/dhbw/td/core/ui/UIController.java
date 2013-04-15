package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.*;
import static de.dhbw.td.core.util.ResourceContainer.resources;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import javax.print.attribute.standard.Media;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.game.GameState;

/**
 * 
 * @author Benedict Holste <benedict@bholste.net>
 * @author Jan-Christoph Klie
 *
 */
public class UIController {
	
	private GameState gameState;
	private HUD hud;
	private MainMenu mainMenu;
	private IngameMenu ingameMenu;
	
	private ImageLayer BACKGROUND_LAYER;
	
	private SurfaceLayer MAP_LAYER;	
	private SurfaceLayer LABEL_LAYER;
	private SurfaceLayer ENEMY_LAYER;
	private SurfaceLayer TOWER_LAYER;
	private SurfaceLayer MENU_LAYER;
	private SurfaceLayer HUD_LAYER;
	
	public UIController(GameState gameState) {
		
		this.gameState = gameState;
		
		hud = new HUD();
		mainMenu = new MainMenu();
		ingameMenu = new IngameMenu();
		
		initLayers();
	}
	
	/**
	 * Initializes the layers for each UI component.
	 */
	private void initLayers() {
		
		Image bg = resources().IMAGE_WHITE;
		BACKGROUND_LAYER = graphics().createImageLayer(bg);
		BACKGROUND_LAYER.setScale(WIDTH, HEIGHT);
		graphics().rootLayer().add(BACKGROUND_LAYER);
		
		MAP_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(MAP_LAYER);

		ENEMY_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(ENEMY_LAYER);
		
		TOWER_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(TOWER_LAYER);

		HUD_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(HUD_LAYER);
		
		LABEL_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(LABEL_LAYER);
		
		MENU_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(MENU_LAYER);
	}
	
	/**
	 * Draws all nested UI components.
	 */
	public void drawComponents() {
		mainMenu.draw(MENU_LAYER.surface());
		//ingameMenu.draw(MENU_LAYER.surface());
	}
}
