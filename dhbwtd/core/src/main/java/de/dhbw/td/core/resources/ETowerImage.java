/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie + Benedict Holste - All
 */

package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_TOWERS;
import playn.core.Image;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.GameConstants;

/**
 * Container for all the tower images
 */
public enum ETowerImage {
	
	CODE_LVL1("code_lvl1.png"),
	CODE_LVL2("code_lvl2.png"),
	CODE_LVL3("code_lvl3.png"),
	CODE_LVL4("code_lvl4.png"),
	MATH_LVL1("math_lvl1.png"),
	MATH_LVL2("math_lvl2.png"),
	MATH_LVL3("math_lvl3.png"),
	MATH_LVL4("math_lvl4.png"),
	SOCIAL_LVL1("social_lvl1.png"),
	SOCIAL_LVL2("social_lvl2.png"),
	SOCIAL_LVL3("social_lvl3.png"),
	SOCIAL_LVL4("social_lvl4.png"),
	SOCIAL_LVL5("social_lvl5.png"),
	SOCIAL_LVL6("social_lvl6.png"),
	TECHINF_LVL1("techinf_lvl1.png"),
	TECHINF_LVL2("techinf_lvl2.png"),
	TECHINF_LVL3("techinf_lvl3.png"),
	TECHINF_LVL4("techinf_lvl4.png"),
	TECHINF_LVL5("techinf_lvl5.png"),
	THEOINF_LVL1("theoinf_lvl1.png"),
	THEOINF_LVL2("theoinf_lvl2.png"),
	THEOINF_LVL3("theoinf_lvl3.png"),
	THEOINF_LVL4("theoinf_lvl4.png"),
	THEOINF_LVL5("theoinf_lvl5.png"),
	WIWI_LVL1("wiwi_lvl1.png"),
	WIWI_LVL2("wiwi_lvl2.png"),
	WIWI_LVL3("wiwi_lvl3.png"),
	WIWI_LVL4("wiwi_lvl4.png"),
	WIWI_LVL5("wiwi_lvl5.png"),
	WIWI_LVL6("wiwi_lvl6.png");
		
	private final Image image;

	ETowerImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_TOWERS + pathToImage);
	}
	
	public static Image getTowerImage(EFlavor flavor, int levelNumber) {
		ETowerImage towerImage;
		String prefix = GameConstants.mapFlavorToImagePrefix(flavor);
		String enumName = String.format("%s_LVL%d", prefix, levelNumber);
		towerImage = ETowerImage.valueOf(enumName);
		return towerImage.image;
	}
	


}
