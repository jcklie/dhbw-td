/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */

package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_PROJETILES;
import playn.core.Image;

public enum EProjectileImage {
	
	LASER("laser.png"),
	GENERIC("generic.png");
	
	public final Image image;
	
	EProjectileImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_PROJETILES + pathToImage);
	}

}
