/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */
package de.dhbw.td.core.level;

import playn.core.Image;
import de.dhbw.td.core.resources.ETileImage;

public enum ETileType {

	GRID(ETileImage.GRID),
	WHITE(ETileImage.WHITE),
	EDGE_LEFT_BOTTOM(ETileImage.EDGE_LEFT_BOTTOM),
	EDGE_LEFT_TOP(ETileImage.EDGE_LEFT_TOP),
	EDGE_RIGHT_BOTTOM(ETileImage.EDGE_RIGHT_BOTTOM),
	EDGE_RIGHT_TOP(ETileImage.EDGE_RIGHT_TOP),
	PATH_EMPTY(ETileImage.PATH_INTERSECTION),
	PATH_HORIZONTAL(ETileImage.PATH_HORIZONTAL),
	PATH_VERTICAL(ETileImage.PATH_VERTICAL),
	PATH_START(ETileImage.START),
	PATH_END(ETileImage.FINISH);

	private final Image image;

	public static ETileType createFromTileId(int id) {
		switch (id) {
		case 0: return GRID;
		case 1: return WHITE;
		case 2: return EDGE_LEFT_BOTTOM;
		case 3: return EDGE_LEFT_TOP;
		case 4: return EDGE_RIGHT_BOTTOM;
		case 5: return EDGE_RIGHT_TOP;
		case 6: return PATH_EMPTY;
		case 7: return PATH_HORIZONTAL;
		case 8: return PATH_VERTICAL;
		case 9: return PATH_START;
		case 10:return PATH_END;
		default: throw new IllegalArgumentException("No ETileType with Tile ID:" + id);
		}
	}

	ETileType(ETileImage tileImage) {
		this.image = tileImage.image;
	}
	
	public Image image() {
		return image;
	}
}
