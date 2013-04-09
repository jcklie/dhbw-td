/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
<<<<<<< HEAD
 *  Jan-Christoph Klie - Basic Structure and First Implementation
 *  Sebastian Muszytowski - Waypoint Queue and refactoring
=======
 *  Jan-Christoph Klie - Basic Structure and First Implementation and final complete, total refactoring
 *  Sebastian Muszytowski - Waypoint Queue magic algorithm and first refactoring
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
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
<<<<<<< HEAD
 * @author Sebastian Muszytowski *
=======
 * @author Sebastian Muszytowski 
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
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
		System.out.println(tileMap);
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
				tileMap[row][col] = assets().getImage(pathToImage);
			}
		}
		
		return tileMap;
	}
	
<<<<<<< HEAD
=======
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
	
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
	/**
	 * Method to generate waypoints from map data. It utilizes
	 * the start and end specified in the json file and calcuates the
	 * route to end. In case of unresolvable issues it returns an error.
	 * 
	 * @return Queue A queue consisting of points.
	 */
	private Queue<Point> generateWaypoints() {
<<<<<<< HEAD
		int column = startx;
		
		if(column != 0){
=======
		int col = startx;
		
		if(startx != 0){
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
			throw new IllegalArgumentException("Sorry, the map should start somewhere in x=0");
		}
		
		int row = starty;
<<<<<<< HEAD
		Queue<Point> queue = new LinkedList<Point>();
		queue.add(new Point(column,row));
				
		ETileType curTile = getTileType(column, row);
		Direction direction = Direction.RIGHT;
		
		while(curTile != ETileType.PATH_END){
			switch(curTile){
			case GRID:
				throw new IllegalStateException("GRID is bad: "+column+","+row);
			case EDGE_LEFT_BOTTOM:
				if(direction == Direction.LEFT){
					queue.add(new Point(column,row));
					direction = Direction.UP;
					row--;
				}else if(direction == Direction.DOWN){
					queue.add(new Point(column,row));
					direction = Direction.RIGHT;
					column++;
				}else{
					throw new IllegalStateException("Cannot send enemy through wall at: "+column+","+row);
				}
				break;
			case EDGE_LEFT_TOP:
				if(direction == Direction.LEFT){
					queue.add(new Point(column,row));
					direction = Direction.DOWN;
					row++;
				}else if(direction == Direction.UP){
					queue.add(new Point(column,row));
					direction = Direction.RIGHT;
					column++;
				}else{
					throw new IllegalStateException("Cannot send enemy through wall at: "+column+","+row);
				}
				break;
			case EDGE_RIGHT_BOTTOM:
				if(direction == Direction.RIGHT){
					queue.add(new Point(column,row));
					direction = Direction.UP;
					row--;
				}else if(direction == Direction.DOWN){
					queue.add(new Point(column,row));
					direction = Direction.LEFT;
					column--;
				}else{
					throw new IllegalStateException("Cannot send enemy through wall at: "+column+","+row);
				}
				break; 
			case EDGE_RIGHT_TOP:
				if(direction == Direction.RIGHT){
					queue.add(new Point(column,row));
					direction = Direction.DOWN;
					row++;
				}else if(direction == Direction.UP){
					queue.add(new Point(column,row));
					direction = Direction.LEFT;
					column--;
				}else{
					throw new IllegalStateException("Cannot send enemy through wall at: "+column+","+row);
				}
				break;
				// keep the current direction in this case	
				case PATH_EMPTY:
					if(direction == Direction.LEFT){
						column--;
					}else if(direction == Direction.RIGHT){
						column++;
					}else if(direction == Direction.DOWN){
						row++;
					}else if(direction == Direction.UP){
						row--;
					}
				break;
			case PATH_HORIZONTAL:
				if(direction == Direction.LEFT){
					column--;
				}else if(direction == Direction.RIGHT){
					column++;
				}else{
					throw new IllegalStateException("Illegal waypoint detected (not left or right) at: "+column+","+row);
				}
				break;
				// we force the map to start with one step to the right
			case PATH_START:
				direction = Direction.RIGHT;
				column++;
				break;
			case PATH_VERTICAL:
				if(direction == Direction.DOWN){
					row++;
				}else if(direction == Direction.UP){
					row--;
				}else{
					throw new IllegalStateException("Illegal waypoint detected (not up or down) at: "+column+","+row);
				}
				break;
			default:
				throw new IllegalStateException("Illegal waypoint detected at: "+column+","+row);
			
			}
			
			// set tile to next tile
			curTile = getTileType(column, row);
			
			// check whether we are still in boundary. Else raise error
			if(column < 0 || row < 0 || column > width-1 || row > height-1){
				throw new IllegalStateException("Illegal map file. Waypoints leaving boundaries at: "+column+","+row);
=======
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
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
			}
			
		}
		// here we should have reached the end if not we're unlucky
<<<<<<< HEAD
		queue.add(new Point(column,row));
		return queue;
=======
		waypoints.add(new Point(container.col,container.row));
		return waypoints;
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
	}
	
	private ETileType getTileType(int column,int row){
		return ETileType.createFromTileId(grid.getArray(row).getInt(column));
	}
<<<<<<< HEAD

}
=======
	
	private void handleEdgeLeftBottom(DirectionContainer c, Queue<Point> waypoints) {
		if (c.dir == Direction.LEFT) {
			waypoints.add(new Point(c.col, c.row));
			c.dir = Direction.UP;
			c.row--;
		} else if (c.dir == Direction.DOWN) {
			waypoints.add(new Point(new Point(c.col, c.row)));
			c.dir = Direction.RIGHT;
			c.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + c.col + "," + c.row);
		}
	}

	private void handleEdgeLeftTop(DirectionContainer container, Queue<Point> waypoints) {
		if (container.dir == Direction.LEFT) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.DOWN;
			container.row++;
		} else if (container.dir == Direction.UP) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.RIGHT;
			container.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightBottom(DirectionContainer container, Queue<Point> waypoints) {
		if (container.dir == Direction.RIGHT) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.UP;
			container.row--;
		} else if (container.dir == Direction.DOWN) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightTop(DirectionContainer container, Queue<Point> waypoints) {
		if (container.dir == Direction.RIGHT) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.DOWN;
			container.row++;
		} else if (container.dir == Direction.UP) {
			waypoints.add(new Point(container.col, container.row));
			container.dir = Direction.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handlePathEmpty(DirectionContainer container, Queue<Point> waypoints) {

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

	private void handlePathHorizontal(DirectionContainer container, Queue<Point> waypoints) {
		if(container.dir == Direction.LEFT){
			container.col--;
		}else if(container.dir == Direction.RIGHT){
			container.col++;
		}else{
			throw new IllegalStateException("Illegal waypoint detected (not left or right) at: "+container.col+","+container.row);
		}
	}
	
	private void handlePathStart(DirectionContainer container, Queue<Point> waypoints) {
		// we force the map to start with one step to the right
		container.dir = Direction.RIGHT;
		container.col++;
	}

	private void handlePathVertical(DirectionContainer container, Queue<Point> waypoints) {
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
>>>>>>> 98d6cc117ef0b96d4fa40f2e4e35aaa167b53593
