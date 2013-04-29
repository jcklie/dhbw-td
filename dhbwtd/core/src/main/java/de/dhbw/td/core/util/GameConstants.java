package de.dhbw.td.core.util;

import de.dhbw.td.core.ui.EUserAction;

public class GameConstants {
	
	/*
	 * MAP/UI CONSTANTS
	 */
	public static final int TILE_SIZE = 64;
	public static final int ROWS = 10;
	public static final int COLS = 14;
	public static final int WIDTH = 896;
	public static final int HEIGHT = 640;
	public static final float FONTSIZE = 32f;
	
	/*
	 * GAME CONSTANTS
	 */
	public static final int FACTOR_DELTA_FF = 4;
	public static final int INITIAL_CREDITS = 25;
	public static final int INITIAL_LIFEPOINTS = 100;
	public static final int NO_OF_LEVELZ = 6;
	
	/*
	 * PATHS
	 */
	public static final String PATH_IMAGES = "images/";
	public static final String PATH_TILES = "tiles/";
	public static final String PATH_TOWERS = "tower/";
	public static final String PATH_MENU = "menu/";	
	public static final String PATH_LEVELS = "levels/";
	public static final String PATH_WAVES = "waves/";
	public static final String PATH_ENEMIES = "enemies/";
	public static final String PATH_SOUNDS = "sound/";
	public static final String PATH_PROJETILES = "projectiles/";
	
	/*
	 * ENEMY CONSTANTS
	 */
	
	public static final int NUMBER_OF_WAVES = 12;
	public static final int NUMBER_OF_ATTRIBUTES = 3;
	public static final int UB_ENEMY_TYPES = 6;
	
	/*
	 * Tower constants
	 */
	
	public static final int PROJECTILE_SPEED = 450;
	public static final double RETURN_PERCENTAGE = 0.85;
	public static final int BASIC_COST = 8;
	
	public static int toTile(int pos) {
		return (int)Math.floor(pos/64);
	}
	
	public static String mapFlavorToImagePrefix(EFlavor flavor) {
		switch(flavor) {
		case COMPUTER_ENGINEERING: return "TECHINF";
		case ECONOMICS: return "WIWI";
		case MATH: return "MATH";
		case PROGRAMMING: return "CODE";
		case SOCIAL: return "SOCIAL";
		case THEORETICAL_COMPUTER_SCIENCE: return "THEOINF";
		default: throw new IllegalStateException("I should not be thrown");
		
		}
	}
	
	public static EFlavor mapActionToFlavor(EUserAction action) {
		switch(action) {
		case NEW_MATH_TOWER: return EFlavor.MATH;
		case NEW_TECH_INF_TOWER: return EFlavor.COMPUTER_ENGINEERING;
		case NEW_CODE_TOWER: return EFlavor.PROGRAMMING;
		case NEW_THEO_INF_TOWER: return EFlavor.THEORETICAL_COMPUTER_SCIENCE;
		case NEW_ECO_TOWER: return EFlavor.ECONOMICS;
		case NEW_SOCIAL_TOWER: return EFlavor.SOCIAL;
		default: throw new IllegalArgumentException("No mapping!");
		}

	}
}
