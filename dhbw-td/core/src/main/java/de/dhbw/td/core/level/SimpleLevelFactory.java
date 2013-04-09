/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - Basic Structure and First Implementation and final complete, total refactoring
 *  Sebastian Muszytowski - Waypoint Queue magic algorithm and first refactoring
 */

package de.dhbw.td.core.level;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import playn.core.Image;
import playn.core.Json;

/**
 * 
 * @author Jan-Christoph Klie
 * @author Sebastian Muszytowski 
 */
public class SimpleLevelFactory implements ILevelFactory {
	
	private enum Direction {
		RIGHT,
		LEFT,
		UP,
		DOWN;
	}
	
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
	
	private int width;
	private int height;
	private int tilesize;
	private int startx;
	private int starty;
	private Json.Array grid;
	
	private void init(Json.Object parsedJson){
		width = parsedJson.getInt("width");
		height = parsedJson.getInt("height");
		tilesize = parsedJson.getInt("tilesize");
		startx = parsedJson.getInt("startx");
		starty = parsedJson.getInt("starty");
		grid = parsedJson.getArray("tiles");
	}
	

	@Override
	public Level loadLevel(String jsonString) {
		Json.Object configuration = json().parse(jsonString);
		return loadLevel(configuration);
	}

	@Override
	public Level loadLevel(Json.Object parsedJson) {
		init(parsedJson);

		Image[][] tileMap = loadTileMap();
		Queue<Point> waypoints = generateWaypoints();
		
		return new Level(tileMap, waypoints, tilesize, width, height, startx, starty);
	}
	
	private Image[][] loadTileMap(){
		Image[][] tileMap = new Image[height][width];
		
		for(int row = 0; row < height; row++) {
			Json.Array gridRow = grid.getArray(row);
			
			for(int col = 0; col < width; col++) {
				int tileID = gridRow.getInt(col);
				String pathToImage = ETileType.getPathToImage(tileID);
				tileMap[row][col] = assets().getImageSync(pathToImage);
			}
		}
		
		return tileMap;
	}
	
	private class DirectionContainer {
		private Queue<Point> waypoints;
		private Direction dir;		
		private int row;
		private int col;

		public DirectionContainer(Queue<Point> waypoints, Direction dir, int row, int col) {
			this.waypoints = waypoints;
			this.dir = dir;
			this.row = row;
			this.col = col;
		}
		
		private void addWaypoint() {
			waypoints.add(new Point(col*tilesize, row*tilesize));
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

				
		ETileType curTile = getTileType(col, row);
		DirectionContainer container = new DirectionContainer(waypoints, Direction.RIGHT, row, col);
		container.addWaypoint();
		
		while(curTile != ETileType.PATH_END){
			switch(curTile){
				case GRID: 	throw new IllegalStateException(String.format("GRID is bad: %d,%d",container.col, container.row));
				case EDGE_LEFT_BOTTOM:	handleEdgeLeftBottom(container); 	break;
				case EDGE_LEFT_TOP:		handleEdgeLeftTop(container);		break;
				case EDGE_RIGHT_BOTTOM: handleEdgeRightBottom(container);	break;
				case EDGE_RIGHT_TOP:	handleEdgeRightTop(container);		break;
				case PATH_EMPTY:		handlePathEmpty(container);			break;
				case PATH_HORIZONTAL:	handlePathHorizontal(container);	break;				
				case PATH_START:		handlePathStart(container);			break;
				case PATH_VERTICAL:		handlePathVertical(container);		break;
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
		container.addWaypoint();
		
		// DIRTY HACK; FIX ME
		

		return waypoints;
	}
	
	private ETileType getTileType(int column,int row){
		return ETileType.createFromTileId(grid.getArray(row).getInt(column));
	}
	
	private void handleEdgeLeftBottom(DirectionContainer container) {
		if (container.dir == Direction.LEFT) {
			container.addWaypoint();
			container.dir = Direction.UP;
			container.row--;
		} else if (container.dir == Direction.DOWN) {
			container.addWaypoint();
			container.dir = Direction.RIGHT;
			container.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeLeftTop(DirectionContainer container) {
		if (container.dir == Direction.LEFT) {
			container.addWaypoint();
			container.dir = Direction.DOWN;
			container.row++;
		} else if (container.dir == Direction.UP) {
			container.addWaypoint();
			container.dir = Direction.RIGHT;
			container.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightBottom(DirectionContainer container) {
		if (container.dir == Direction.RIGHT) {
			container.addWaypoint();
			container.dir = Direction.UP;
			container.row--;
		} else if (container.dir == Direction.DOWN) {
			container.addWaypoint();
			container.dir = Direction.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightTop(DirectionContainer container) {
		if (container.dir == Direction.RIGHT) {
			container.addWaypoint();
			container.dir = Direction.DOWN;
			container.row++;
		} else if (container.dir == Direction.UP) {
			container.addWaypoint();
			container.dir = Direction.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handlePathEmpty(DirectionContainer container) {

		if (container.dir == Direction.LEFT) {
			container.col--;
		} else if (container.dir == Direction.RIGHT) {
			container.col++;
		} else if (container.dir == Direction.DOWN) {
			container.row++;
		} else if (container.dir == Direction.UP) {
			container.row--;
		}
	}

	private void handlePathHorizontal(DirectionContainer container) {
		if(container.dir == Direction.LEFT){
			container.col--;
		}else if(container.dir == Direction.RIGHT){
			container.col++;
		}else{
			throw new IllegalStateException("Illegal waypoint detected (not left or right) at: "+container.col+","+container.row);
		}
	}
	
	private void handlePathStart(DirectionContainer container) {
		// we force the map to start with one step to the right
		container.dir = Direction.RIGHT;
		container.col++;
	}

	private void handlePathVertical(DirectionContainer container) {
		if (container.dir == Direction.DOWN) {
			container.row++;
		} else if (container.dir == Direction.UP) {
			container.row--;
		} else {
			throw new IllegalStateException("Illegal waypoint detected (not up or down) at: " + container.col + ","
					+ container.row);
		}
	}
}
