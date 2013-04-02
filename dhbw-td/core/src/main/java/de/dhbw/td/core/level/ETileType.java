package de.dhbw.td.core.level;

public enum ETileType {	
	
	GRID("grid.bmp", 0),
	WHITE("white.bmp", 1),
	EDGE_LEFT_BOTTOM("edge_left_bottom.bmp", 2),
	EDGE_LEFT_TOP("edge_left_top.bmp", 3),
	EDGE_RIGHT_BOTTOM("edge_right_bottom.bmp", 4),
	EDGE_RIGHT_TOP("edge_right_top.bmp", 5),
	PATH_EMPTY("path_empty.bmp", 6),
	PATH_HORIZONTAL("path_horizontal.bmp", 7),
	PATH_VERTICAL("path_vertical.bmp", 8);	
	
	public final String resourceName;
	public final int tileID;
	
	private static final String pathToTiles = "tiles";
	
	public static String getPathToImage(int tileID) {
		ETileType tileType = createFromTileId(tileID);
		return String.format("%s/%s", pathToTiles, tileType.resourceName);
	}
	
	private static ETileType createFromTileId(int id) {
		switch(id) {
		case 0: return GRID;
		case 1: return WHITE;
		case 2: return EDGE_LEFT_BOTTOM;
		case 3: return EDGE_LEFT_TOP;
		case 4: return EDGE_RIGHT_BOTTOM;
		case 5: return EDGE_RIGHT_TOP;
		case 6: return PATH_EMPTY;
		case 7: return PATH_HORIZONTAL;
		case 8: return PATH_VERTICAL;
		default: throw new IllegalArgumentException("No ETileType with Tile ID:" + id);
		}
		
	}

	ETileType(String resourceName, int tileID) {
		this.resourceName = resourceName;
		this.tileID = tileID;
	}

}
