/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */
package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_TILES;
import playn.core.Image;

public enum ETileImage {
	
	EDGE_LEFT_BOTTOM("edge_left_bottom.bmp"),
	EDGE_LEFT_TOP("edge_left_top.bmp"),
	EDGE_RIGHT_BOTTOM("edge_right_bottom.bmp"),
	EDGE_RIGHT_TOP("edge_right_top.bmp"),
	PATH_VERTICAL("path_vertical.bmp"),
	PATH_HORIZONTAL("path_horizontal.bmp"),
	PATH_INTERSECTION("path_empty.bmp"),
	START("start.bmp"),
	FINISH("finish.bmp"),
	GRID("grid.bmp"),
	WHITE("white.bmp");
	
	public final Image image;
	
	ETileImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_TILES + pathToImage);
	}

}