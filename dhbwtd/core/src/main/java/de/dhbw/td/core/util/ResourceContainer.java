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
import de.dhbw.td.core.TowerDefense;
import playn.core.Image;

public enum ResourceContainer {
	
	CONTAINER;
	
	public static ResourceContainer resources() {
		return CONTAINER;
	}
	
	private final String PATH_IMAGES = "images/";
	private final String PATH_TILES = "tiles/";
	private final String PATH_TOWERS = "tower/";
	private final String PATH_MENU = "menu/";
	
	/*
	 * Menu IMAGES
	 */
	
	public Image MENU_CREDITS;
	public Image MENU_BACKGROUND;
	public Image MENU_HELP;
	public Image MENU_RESUME;
	public Image MENU_QUIT;
	public Image MENU_NEW;
	
	/*
	 * HUD IMAGES
	 */
	public Image CLOCK;
	public Image PLAY;
	public Image PAUSE;
	public Image FAST_FORWARD;
	public Image COG;
	public Image HEART;
	public Image CREDITS;
	
	/*
	 * MAP TILES
	 */
	public Image EDGE_LEFT_BOTTOM;
	public Image EDGE_LEFT_TOP;
	public Image EDGE_RIGHT_BOTTOM;
	public Image EDGE_RIGHT_TOP;
	public Image START;
	public Image FINISH;
	public Image PATH_HORIZONTAL;
	public Image PATH_VERTICAL;
	public Image PATH_INTERSECTION;
	
	/*
	 * TOWER IMAGES
	 */
	public Image MATH_TOWER;
	public Image CODE_TOWER;
	public Image SOCIAL_TOWER;
	public Image TECHINF_TOWER;
	public Image THEOINF_TOWER;
	public Image WIWI_TOWER;
	
	/*
	 * HEALTHBAR IMAGES
	 */
	public Image HEALTHBAR_0;
	public Image HEALTHBAR_10;
	public Image HEALTHBAR_20;
	public Image HEALTHBAR_30;
	public Image HEALTHBAR_40;
	public Image HEALTHBAR_50;
	public Image HEALTHBAR_60;
	public Image HEALTHBAR_70;
	public Image HEALTHBAR_80;
	public Image HEALTHBAR_90;
	public Image HEALTHBAR_100;
	
	private ResourceContainer() {
		
		MENU_CREDITS = assets().getImageSync(PATH_MENU + "credits.png");
		MENU_BACKGROUND = assets().getImageSync(PATH_MENU + "menu_bg.png");
		MENU_HELP = assets().getImageSync(PATH_MENU + "help.png");
		MENU_RESUME = assets().getImageSync(PATH_MENU + "resume_game.png");
		MENU_QUIT = assets().getImageSync(PATH_MENU + "quit_game.png");
		MENU_NEW = assets().getImage(PATH_MENU + "new_game.png");
		
		CLOCK = assets().getImageSync(PATH_IMAGES + "clock.png");
		PLAY = assets().getImageSync(PATH_IMAGES + "play.png");
		PAUSE = assets().getImageSync(PATH_IMAGES + "pause.png");
		FAST_FORWARD = assets().getImageSync(PATH_IMAGES + "fast_forward.png");
		COG = assets().getImageSync(PATH_IMAGES + "cog.png");
		HEART = assets().getImageSync(PATH_IMAGES + "heart.png");
		CREDITS = assets().getImageSync(PATH_IMAGES + "piggy.png");
		
		EDGE_LEFT_BOTTOM = assets().getImageSync(PATH_TILES + "edge_left_bottom.bmp");
		EDGE_LEFT_TOP = assets().getImageSync(PATH_TILES + "edge_left_top.bmp");
		EDGE_RIGHT_BOTTOM = assets().getImageSync(PATH_TILES + "edge_right_bottom.bmp");
		EDGE_RIGHT_TOP = assets().getImageSync(PATH_TILES + "edge_right_top.bmp");
		
		MATH_TOWER = assets().getImageSync(PATH_TOWERS + "math.png");
		CODE_TOWER = assets().getImageSync(PATH_TOWERS + "code.png");
		SOCIAL_TOWER = assets().getImageSync(PATH_TOWERS + "social.png");
		TECHINF_TOWER = assets().getImageSync(PATH_TOWERS + "techinf.png");
		THEOINF_TOWER = assets().getImageSync(PATH_TOWERS + "theoinf.png");
		WIWI_TOWER = assets().getImageSync(PATH_TOWERS + "wiwi.png");
		
		HEALTHBAR_0 = assets().getImageSync(PATH_IMAGES + "0.png");
		HEALTHBAR_10 = assets().getImageSync(PATH_IMAGES + "10.png");
		HEALTHBAR_20 = assets().getImageSync(PATH_IMAGES + "20.png");
		HEALTHBAR_30 = assets().getImageSync(PATH_IMAGES + "30.png");
		HEALTHBAR_40 = assets().getImageSync(PATH_IMAGES + "40.png");
		HEALTHBAR_50 = assets().getImageSync(PATH_IMAGES + "50.png");
		HEALTHBAR_60 = assets().getImageSync(PATH_IMAGES + "60.png");
		HEALTHBAR_70 = assets().getImageSync(PATH_IMAGES + "70.png");
		HEALTHBAR_80 = assets().getImageSync(PATH_IMAGES + "80.png");
		HEALTHBAR_90 = assets().getImageSync(PATH_IMAGES + "90.png");
		HEALTHBAR_100 = assets().getImageSync(PATH_IMAGES + "100.png");
	}
	
}
