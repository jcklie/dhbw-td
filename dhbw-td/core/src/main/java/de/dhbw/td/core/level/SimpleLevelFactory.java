/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;
import playn.core.Image;
import playn.core.Json;

/**
 * 
 * @author Jan-Christoph Klie
 *
 */
public class SimpleLevelFactory implements ILevelFactory {
	
	private enum ETileType {	
		
		GRID("grid.bmp"),
		WHITE("white.bmp"),
		EDGE_LEFT_BOTTOM("edge_left_bottom.bmp"),
		EDGE_LEFT_TOP("edge_left_top.bmp"),
		EDGE_RIGHT_BOTTOM("edge_right_bottom.bmp"),
		EDGE_RIGHT_TOP("edge_right_top.bmp"),
		PATH_EMPTY("path_empty.bmp"),
		PATH_HORIZONTAL("path_horizontal.bmp"),
		PATH_VERTICAL("path_vertical.bmp"),
		PATH_START("start.bmp"),
		PATH_END("finish.bmp"),
		TOWER_DUMMY("code.png");
		
		public final String resourceName;

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
			case 9: return PATH_START;
			case 10: return PATH_END;
			case 11: return TOWER_DUMMY;
			default: throw new IllegalArgumentException("No ETileType with Tile ID:" + id);
			}			
		}

		ETileType(String resourceName) {
			this.resourceName = resourceName;
		}
	}

	@Override
	public Level loadLevel(String jsonString) {
		return loadLevel(json().parse(jsonString));
	}

	@Override
	public Level loadLevel(Json.Object parsedJson) {
<<<<<<< HEAD
		int width = parsedJson.getInt("width");
		int height = parsedJson.getInt("height");
		int tilesize = parsedJson.getInt("tilesize");
=======
		init(parsedJson);

		Image[][] tileMap = loadTileMap();
		Queue<Point> waypoints = generateWaypoints();
>>>>>>> b5cbd6adbd8063a7b8545c576bd999559892412b
		
		Json.Array grid = parsedJson.getArray("tiles");
		Image[][] tileMap = new Image[height][width];
		
		for(int row = 0; row < height; row++) {
			Json.Array gridRow = grid.getArray(row);
			
			for(int col = 0; col < width; col++) {
				int tileID = gridRow.getInt(col);
				String pathToImage = ETileType.getPathToImage(tileID);
				tileMap[row][col] = assets().getImageSync(pathToImage);
			}
		}
<<<<<<< HEAD
		return new Level(tileMap, tilesize, width, height);
=======
		
		return tileMap;
	}
	
	private class DirectionContainer {
		private int row;
		private int col;
		private Direction dir;
		
		private DirectionContainer(Direction dir, int row, int col) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}

		@Override
		public String toString() {
			return String.format("row=%s\ncol=%s\ndir=%s", row, col, dir);
		}		
		
	}
	
	/**
	 * Generates waypoints from map data. It utilizes
	 * the start and end specified in the json file and calculates the
	 * route to end. Throws IllegalStateException on malformed maps.
	 * 
	 * @return Queue A queue consisting of waypoints.
	 */
	private Queue<Point> generateWaypoints() {
		int col = startx;
		
		if(startx != 0){
			throw new IllegalArgumentException("Sorry, the map should start somewhere in x=0");
		}
		
		int row = starty;
		Queue<Point> waypoints = new LinkedList<Point>();
		waypoints.add(new Point(col,row));
				
		ETileType curTile = getTileType(col, row);
		DirectionContainer container = new DirectionContainer(Direction.RIGHT, row, col);
		
		while(curTile != ETileType.PATH_END){
			switch(curTile){
				case GRID: 	throw new IllegalStateException(String.format("GRID is bad: %d,%d",container.col, container.row));
				case EDGE_LEFT_BOTTOM:	handleEdgeLeftBottom(container, waypoints ); 	break;
				case EDGE_LEFT_TOP:		handleEdgeLeftTop(container, waypoints);		break;
				case EDGE_RIGHT_BOTTOM: handleEdgeRightBottom(container, waypoints);	break;
				case EDGE_RIGHT_TOP:	handleEdgeRightTop(container,waypoints);		break;
				case PATH_EMPTY:		handlePathEmpty(container, waypoints);			break;
				case PATH_HORIZONTAL:	handlePathHorizontal(container, waypoints);		break;				
				case PATH_START:		handlePathStart(container, waypoints);			break;
				case PATH_VERTICAL:		handlePathVertical(container, waypoints);		break;
				default: throw new IllegalStateException(String.format("Illegal waypoint at: %d,%d", + container.col, container.row));			
			}
			
			// set tile to next tile
			curTile = getTileType(container.col, container.row);
			
			// check whether we are still in boundary. Else raise error
			if(container.col < 0 || container.row < 0 || container.col > width-1 || container.row > height-1){
				String errorMessage = String.format("Waypoints leaving boundaries at: %d,%d", container.col, container.row);
				throw new IllegalStateException(errorMessage);
			}
			
		}
		// here we should have reached the end if not we're unlucky
		waypoints.add(new Point(container.col,container.row));
		return waypoints;
>>>>>>> b5cbd6adbd8063a7b8545c576bd999559892412b
	}
	

}
