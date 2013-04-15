/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - Idea and first implementation
 *  Jan-Christoph Klie - Changed singleton to single enum + extension + refactor
 *  
 */

package de.dhbw.td.core.util;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;
import playn.core.Image;
import playn.core.Json;
import playn.core.Sound;
import playn.core.json.JsonParserException;
import playn.core.util.Callback;

public enum ResourceContainer {
	
	CONTAINER;
	
	public static ResourceContainer resources() {
		return CONTAINER;
	}
	
	private final String PATH_IMAGES = "images/";
	private final String PATH_TILES = "tiles/";
	private final String PATH_TOWERS = "tower/";
	private final String PATH_MENU = "menu/";	
	private final String PATH_LEVELS = "levels/";
	private final String PATH_WAVES = "waves/";
	private final String PATH_ENEMIES = "enemies/";
	private final String PATH_SOUNDS = "sound/";
	
	/*
	 * Menu IMAGES
	 */
	
	public final Image IMAGE_MENU_MAIN_BACKGROUND;
	public final Image IMAGE_MENU_INGAME_BACKGROUND;
	public final Image IMAGE_MENU_CREDITS;	
	public final Image IMAGE_MENU_HELP;
	public final Image IMAGE_MENU_RESUME;
	public final Image IMAGE_MENU_QUIT;
	public final Image IMAGE_MENU_NEW;
	
	
	/*
	 * HUD IMAGES
	 */
	public final Image IMAGE_CLOCK;
	public final Image IMAGE_PLAY;
	public final Image IMAGE_PAUSE;
	public final Image IMAGE_FAST_FORWARD;
	public final Image IMAGE_COG;
	public final Image IMAGE_HEART;
	public final Image IMAGE_CREDITS;
	
	/*
	 * MAP TILES
	 */
	public final Image IMAGE_EDGE_LEFT_BOTTOM;
	public final Image IMAGE_EDGE_LEFT_TOP;
	public final Image IMAGE_EDGE_RIGHT_BOTTOM;
	public final Image IMAGE_EDGE_RIGHT_TOP;
	public final Image IMAGE_START;
	public final Image IMAGE_FINISH;
	public final Image IMAGE_PATH_HORIZONTAL;
	public final Image IMAGE_PATH_VERTICAL;
	public final Image IMAGE_PATH_INTERSECTION;
	public final Image IMAGE_GRID;
	public final Image IMAGE_WHITE;
	
	/*
	 * TOWER IMAGES
	 */
	public final Image IMAGE_MATH_TOWER;
	public final Image IMAGE_CODE_TOWER;
	public final Image IMAGE_SOCIAL_TOWER;
	public final Image IMAGE_TECHINF_TOWER;
	public final Image IMAGE_THEOINF_TOWER;
	public final Image IMAGE_WIWI_TOWER;
	
	/*
	 * HEALTHBAR IMAGES
	 */
	public final Image IMAGE_HEALTHBAR_0;
	public final Image IMAGE_HEALTHBAR_10;
	public final Image IMAGE_HEALTHBAR_20;
	public final Image IMAGE_HEALTHBAR_30;
	public final Image IMAGE_HEALTHBAR_40;
	public final Image IMAGE_HEALTHBAR_50;
	public final Image IMAGE_HEALTHBAR_60;
	public final Image IMAGE_HEALTHBAR_70;
	public final Image IMAGE_HEALTHBAR_80;
	public final Image IMAGE_HEALTHBAR_90;
	public final Image IMAGE_HEALTHBAR_100;
	
	/*
	 * ENEMY IMAGES
	 */
	
	public final Image IMAGE_MATH_ENEMY;
	public final Image IMAGE_CODE_ENEMY;
	public final Image IMAGE_SOCIAL_ENEMY;
	public final Image IMAGE_TECHINF_ENEMY;
	public final Image IMAGE_THEOINF_ENEMY;
	public final Image IMAGE_WIWI_ENEMY;
	
	/*
	 * SOUNDS
	 */
	public final Sound SOUND_INTRO;
	
	/*
	 * LEVELS
	 */
	public final Json.Object JSON_LEVEL1;
	public final Json.Object JSON_LEVEL2;
	public final Json.Object JSON_LEVEL3;
	public final Json.Object JSON_LEVEL4;
	public final Json.Object JSON_LEVEL5;
	public final Json.Object JSON_LEVEL6;
	
	/*
	 * WAVES
	 */
	public final Json.Object JSON_WAVES_LVL1;
	public final Json.Object JSON_WAVES_LVL2;
	public final Json.Object JSON_WAVES_LVL3;
	public final Json.Object JSON_WAVES_LVL4;
	public final Json.Object JSON_WAVES_LVL5;
	public final Json.Object JSON_WAVES_LVL6;
	
	private ResourceContainer() {
		
		/*
		 * Load images
		 */
		
		IMAGE_MENU_NEW = assets().getImage(PATH_MENU + "new_game.png");
		IMAGE_MENU_INGAME_BACKGROUND = getImage(PATH_MENU + "ingame_menu_bg.png");
		IMAGE_MENU_CREDITS = getImage(PATH_MENU + "credits.png");		
		IMAGE_MENU_MAIN_BACKGROUND = getImage(PATH_MENU + "main_menu_bg.png");
		IMAGE_MENU_HELP = getImage(PATH_MENU + "help.png");
		IMAGE_MENU_RESUME = getImage(PATH_MENU + "resume_game.png");
		IMAGE_MENU_QUIT = getImage(PATH_MENU + "quit_game.png");		
		
		IMAGE_CLOCK = getImage(PATH_IMAGES + "clock.png");
		IMAGE_PLAY = getImage(PATH_IMAGES + "play.png");
		IMAGE_PAUSE = getImage(PATH_IMAGES + "pause.png");
		IMAGE_FAST_FORWARD = getImage(PATH_IMAGES + "fast_forward.png");
		IMAGE_COG = getImage(PATH_IMAGES + "cog.png");
		IMAGE_HEART = getImage(PATH_IMAGES + "heart.png");
		IMAGE_CREDITS = getImage(PATH_IMAGES + "piggy.png");
		
		IMAGE_EDGE_LEFT_BOTTOM = getImage(PATH_TILES + "edge_left_bottom.bmp");
		IMAGE_EDGE_LEFT_TOP = getImage(PATH_TILES + "edge_left_top.bmp");
		IMAGE_EDGE_RIGHT_BOTTOM = getImage(PATH_TILES + "edge_right_bottom.bmp");
		IMAGE_EDGE_RIGHT_TOP = getImage(PATH_TILES + "edge_right_top.bmp");
		IMAGE_PATH_VERTICAL = getImage(PATH_TILES + "path_vertical.bmp");
		IMAGE_PATH_HORIZONTAL = getImage(PATH_TILES + "path_horizontal.bmp");
		IMAGE_PATH_INTERSECTION = getImage(PATH_TILES + "path_empty.bmp");
		IMAGE_START = getImage(PATH_TILES + "start.bmp");
		IMAGE_FINISH = getImage(PATH_TILES + "finish.bmp");
		IMAGE_GRID = getImage(PATH_TILES + "grid.bmp");
		IMAGE_WHITE = getImage(PATH_TILES + "white.bmp");
		
		IMAGE_MATH_TOWER = getImage(PATH_TOWERS + "math.png");
		IMAGE_CODE_TOWER = getImage(PATH_TOWERS + "code.png");
		IMAGE_SOCIAL_TOWER = getImage(PATH_TOWERS + "social.png");
		IMAGE_TECHINF_TOWER = getImage(PATH_TOWERS + "techinf.png");
		IMAGE_THEOINF_TOWER = getImage(PATH_TOWERS + "theoinf.png");
		IMAGE_WIWI_TOWER = getImage(PATH_TOWERS + "wiwi.png");
		
		IMAGE_HEALTHBAR_0 = getImage(PATH_IMAGES + "0.png");
		IMAGE_HEALTHBAR_10 = getImage(PATH_IMAGES + "10.png");
		IMAGE_HEALTHBAR_20 = getImage(PATH_IMAGES + "20.png");
		IMAGE_HEALTHBAR_30 = getImage(PATH_IMAGES + "30.png");
		IMAGE_HEALTHBAR_40 = getImage(PATH_IMAGES + "40.png");
		IMAGE_HEALTHBAR_50 = getImage(PATH_IMAGES + "50.png");
		IMAGE_HEALTHBAR_60 = getImage(PATH_IMAGES + "60.png");
		IMAGE_HEALTHBAR_70 = getImage(PATH_IMAGES + "70.png");
		IMAGE_HEALTHBAR_80 = getImage(PATH_IMAGES + "80.png");
		IMAGE_HEALTHBAR_90 = getImage(PATH_IMAGES + "90.png");
		IMAGE_HEALTHBAR_100 = getImage(PATH_IMAGES + "100.png");
		
		IMAGE_MATH_ENEMY = getImage(PATH_ENEMIES + "math.png");
		IMAGE_CODE_ENEMY = getImage(PATH_ENEMIES + "code.png");
		IMAGE_SOCIAL_ENEMY = getImage(PATH_ENEMIES + "social.png");
		IMAGE_TECHINF_ENEMY = getImage(PATH_ENEMIES + "techinf.png");
		IMAGE_THEOINF_ENEMY = getImage(PATH_ENEMIES + "theoinf.png");
		IMAGE_WIWI_ENEMY = getImage(PATH_ENEMIES + "wiwi.png");
		
		/*
		 * Load sounds
		 */
		SOUND_INTRO = getSound(PATH_SOUNDS + "intro_xD");
		
		/*
		 * Load levels
		 */
		JSON_LEVEL1 = getJSON(PATH_LEVELS + "level1.json");
		JSON_LEVEL2 = getJSON(PATH_LEVELS + "level2.json");
		JSON_LEVEL3 = getJSON(PATH_LEVELS + "level3.json");
		JSON_LEVEL4 = getJSON(PATH_LEVELS + "level4.json");
		JSON_LEVEL5 = getJSON(PATH_LEVELS + "level5.json");
		JSON_LEVEL6 = getJSON(PATH_LEVELS + "level6.json");
		
		/*
		 * Load waves
		 */
		JSON_WAVES_LVL1 = getJSON(PATH_WAVES + "waves1.json");
		JSON_WAVES_LVL2 = getJSON(PATH_WAVES + "waves2.json");
		JSON_WAVES_LVL3 = getJSON(PATH_WAVES + "waves3.json");
		JSON_WAVES_LVL4 = getJSON(PATH_WAVES + "waves4.json");
		JSON_WAVES_LVL5 = getJSON(PATH_WAVES + "waves5.json");
		JSON_WAVES_LVL6 = getJSON(PATH_WAVES + "waves6.json");
	}
	
	/**
	 * Loads and parses an JSON resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading or parsing the JSON resource
	 */
	private Json.Object getJSON(String path) {
		
		Json.Object json;
		
		try {
			json = json().parse(assets().getTextSync(path));
		} catch (JsonParserException jpe) {
			// TODO: handle exception
			throw new RuntimeException("Could not parse JSON at " + path + ". " + jpe.getMessage());
		} 
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not load JSON at " + path + ". " + e.getMessage());
		}
		return json;
	}
	
	/**
	 * Loads an Image resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading the image resource
	 */
	private Image getImage(String path) {
		Image img = assets().getImageSync(path);
		img.addCallback(new Callback<Image>(){
			@Override
			public void onSuccess(Image result) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub
				throw new RuntimeException("Could not load image resource at " + cause.getMessage());
			}
		});
		return img;
	}
	
	/**
	 * Loads a Sound resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading the sound resource
	 */
	private Sound getSound(String path) {
		Sound s = assets().getSound(path);
		s.addCallback(new Callback<Sound>(){
			@Override
			public void onSuccess(Sound result) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub
				throw new RuntimeException("Could not load sound resource at " + cause.getMessage());
			}
		});
		return s;
	}
	
}
