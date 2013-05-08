/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - idea and first version
 *  Jan-Christoph Klie - additions
 */ 
package de.dhbw.td.core.ui;

/**
 * Enum holding the possible UI actions
 * a user can perform
 */
public enum EUserAction {
	NONE,
	PAUSE_GAME,
	NEW_GAME, 
	RESUME_GAME,
	QUIT_GAME, 
	MAIN_MENU, 
	INGAME_MENU, 
	NEW_MATH_TOWER,
	NEW_CODE_TOWER, 
	NEW_TECH_INF_TOWER, 
	NEW_THEO_INF_TOWER, 
	NEW_ECO_TOWER, 
	NEW_SOCIAL_TOWER,
	UPGRADE,
	SELL;
}