/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie + Benedict Holste - All
 */

package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_MENU;
import playn.core.Image;

public enum EMenuImage {
	
	GAMEOVER("gameover.png"),
	MAIN_BACKGROUND("main_menu_bg.png"),
	INGAME_BACKGROUND("ingame_menu_bg.png"),
	CREDITS("credits_screen.png"),
	BTN_CREDITS("btn_credits.png"),	
	BTN_HELP("btn_help.png"),
	RESUME("btn_resume_game.png"),
	MAIN_MENU("btn_main_menu.png"),
	QUIT("btn_quit_game.png"),
	NEW("btn_new_game.png"),
	HELPSCREEN("help_screen.png"),
	SUCCESS("success.png");
	
	public final Image image;
	
	EMenuImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_MENU + pathToImage);
	}

}
