package de.dhbw.td.backup;

import static de.dhbw.td.backup.ResourceContainer.resources;
import playn.core.Image;

public enum ETileType {

	GRID(resources().IMAGE_GRID),
	WHITE(resources().IMAGE_WHITE),
	EDGE_LEFT_BOTTOM(resources().IMAGE_EDGE_LEFT_BOTTOM),
	EDGE_LEFT_TOP(resources().IMAGE_EDGE_LEFT_TOP),
	EDGE_RIGHT_BOTTOM(resources().IMAGE_EDGE_RIGHT_BOTTOM),
	EDGE_RIGHT_TOP(resources().IMAGE_EDGE_RIGHT_TOP),
	PATH_EMPTY(resources().IMAGE_PATH_INTERSECTION),
	PATH_HORIZONTAL(resources().IMAGE_PATH_HORIZONTAL),
	PATH_VERTICAL(resources().IMAGE_PATH_VERTICAL),
	PATH_START(resources().IMAGE_START),
	PATH_END(resources().IMAGE_FINISH);

	private final Image image;

	public static ETileType createFromTileId(int id) {
		switch (id) {
		case 0:
			return GRID;
		case 1:
			return WHITE;
		case 2:
			return EDGE_LEFT_BOTTOM;
		case 3:
			return EDGE_LEFT_TOP;
		case 4:
			return EDGE_RIGHT_BOTTOM;
		case 5:
			return EDGE_RIGHT_TOP;
		case 6:
			return PATH_EMPTY;
		case 7:
			return PATH_HORIZONTAL;
		case 8:
			return PATH_VERTICAL;
		case 9:
			return PATH_START;
		case 10:
			return PATH_END;
		default:
			throw new IllegalArgumentException("No ETileType with Tile ID:" + id);
		}
	}

	ETileType(Image img) {
		this.image = img;
	}
	
	public Image image() {
		return image;
	}
}
