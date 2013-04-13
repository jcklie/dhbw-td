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
	
	public final Image IMAGE_MENU_CREDITS;
	public final Image IMAGE_MENU_BACKGROUND;
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
	
	private ResourceContainer() {
		
		IMAGE_MENU_CREDITS = assets().getImageSync(PATH_MENU + "credits.png");
		IMAGE_MENU_BACKGROUND = assets().getImageSync(PATH_MENU + "menu_bg.png");
		IMAGE_MENU_HELP = assets().getImageSync(PATH_MENU + "help.png");
		IMAGE_MENU_RESUME = assets().getImageSync(PATH_MENU + "resume_game.png");
		IMAGE_MENU_QUIT = assets().getImageSync(PATH_MENU + "quit_game.png");
		IMAGE_MENU_NEW = assets().getImage(PATH_MENU + "new_game.png");
		
		IMAGE_CLOCK = assets().getImageSync(PATH_IMAGES + "clock.png");
		IMAGE_PLAY = assets().getImageSync(PATH_IMAGES + "play.png");
		IMAGE_PAUSE = assets().getImageSync(PATH_IMAGES + "pause.png");
		IMAGE_FAST_FORWARD = assets().getImageSync(PATH_IMAGES + "fast_forward.png");
		IMAGE_COG = assets().getImageSync(PATH_IMAGES + "cog.png");
		IMAGE_HEART = assets().getImageSync(PATH_IMAGES + "heart.png");
		IMAGE_CREDITS = assets().getImageSync(PATH_IMAGES + "piggy.png");
		
		IMAGE_EDGE_LEFT_BOTTOM = assets().getImageSync(PATH_TILES + "edge_left_bottom.bmp");
		IMAGE_EDGE_LEFT_TOP = assets().getImageSync(PATH_TILES + "edge_left_top.bmp");
		IMAGE_EDGE_RIGHT_BOTTOM = assets().getImageSync(PATH_TILES + "edge_right_bottom.bmp");
		IMAGE_EDGE_RIGHT_TOP = assets().getImageSync(PATH_TILES + "edge_right_top.bmp");
		IMAGE_PATH_VERTICAL = assets().getImageSync(PATH_TILES + "path_vertical.bmp");
		IMAGE_PATH_HORIZONTAL = assets().getImageSync(PATH_TILES + "path_horizontal.bmp");
		IMAGE_PATH_INTERSECTION = assets().getImageSync(PATH_TILES + "path_empty.bmp");
		IMAGE_START = assets().getImageSync(PATH_TILES + "start.bmp");
		IMAGE_FINISH = assets().getImageSync(PATH_TILES + "finish.bmp");
		IMAGE_GRID = assets().getImageSync(PATH_TILES + "grid.bmp");
		
		IMAGE_MATH_TOWER = assets().getImageSync(PATH_TOWERS + "math.png");
		IMAGE_CODE_TOWER = assets().getImageSync(PATH_TOWERS + "code.png");
		IMAGE_SOCIAL_TOWER = assets().getImageSync(PATH_TOWERS + "social.png");
		IMAGE_TECHINF_TOWER = assets().getImageSync(PATH_TOWERS + "techinf.png");
		IMAGE_THEOINF_TOWER = assets().getImageSync(PATH_TOWERS + "theoinf.png");
		IMAGE_WIWI_TOWER = assets().getImageSync(PATH_TOWERS + "wiwi.png");
		
		IMAGE_HEALTHBAR_0 = assets().getImageSync(PATH_IMAGES + "0.png");
		IMAGE_HEALTHBAR_10 = assets().getImageSync(PATH_IMAGES + "10.png");
		IMAGE_HEALTHBAR_20 = assets().getImageSync(PATH_IMAGES + "20.png");
		IMAGE_HEALTHBAR_30 = assets().getImageSync(PATH_IMAGES + "30.png");
		IMAGE_HEALTHBAR_40 = assets().getImageSync(PATH_IMAGES + "40.png");
		IMAGE_HEALTHBAR_50 = assets().getImageSync(PATH_IMAGES + "50.png");
		IMAGE_HEALTHBAR_60 = assets().getImageSync(PATH_IMAGES + "60.png");
		IMAGE_HEALTHBAR_70 = assets().getImageSync(PATH_IMAGES + "70.png");
		IMAGE_HEALTHBAR_80 = assets().getImageSync(PATH_IMAGES + "80.png");
		IMAGE_HEALTHBAR_90 = assets().getImageSync(PATH_IMAGES + "90.png");
		IMAGE_HEALTHBAR_100 = assets().getImageSync(PATH_IMAGES + "100.png");
	}
	
}
