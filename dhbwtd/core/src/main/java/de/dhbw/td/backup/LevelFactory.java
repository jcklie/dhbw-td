/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - Basic Structure and First Implementation and final complete, total refactoring
 *  Sebastian Muszytowski - Waypoint Queue magic algorithm and first refactoring
 */

package de.dhbw.td.backup;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;


import java.util.LinkedList;
import java.util.Queue;

import playn.core.Image;
import playn.core.Json;

/**
 * 
 * @author Jan-Christoph Klie
 * @author Sebastian Muszytowski
 */
public class LevelFactory {

	private int width;
	private int height;
	private int tilesize;
	private int startx;
	private int starty;
	private Json.Array grid;

	/**
	 * Initializes the LevelFactory object
	 * 
	 * @param parsedJson
	 */
	private void init(Json.Object parsedJson) {
		width = parsedJson.getInt("width");
		height = parsedJson.getInt("height");
		tilesize = parsedJson.getInt("tilesize");
		startx = parsedJson.getInt("startx");
		starty = parsedJson.getInt("starty");
		grid = parsedJson.getArray("tiles");
	}

	public Level loadLevel(String jsonString) {
		Json.Object configuration = json().parse(jsonString);
		return loadLevel(configuration);
	}

	public Level loadLevel(Json.Object parsedJson) {
		init(parsedJson);

		Queue<Point> waypoints = generateWaypoints();
		
		ETileType[][] map = loadMap();

		return new Level(map, waypoints, tilesize, width, height, startx, starty);
	}
	
	private ETileType[][] loadMap() {
		ETileType[][] map = new ETileType[height][width];
		for (int row = 0; row < height; row++) {
			Json.Array gridRow = grid.getArray(row);
			for (int col = 0; col < width; col++) {
				map[row][col] = ETileType.createFromTileId(gridRow.getInt(col));
			}
		}
		return map;
	}

	private class DirectionContainer {
		private Queue<Point> waypoints;
		private EDirection dir;
		private int row;
		private int col;

		public DirectionContainer(Queue<Point> waypoints, EDirection dir, int row, int col) {
			this.waypoints = waypoints;
			this.dir = dir;
			this.row = row;
			this.col = col;
		}

		private void addWaypoint() {
			waypoints.add(new Point(col * tilesize, row * tilesize));
		}

	}

	/**
	 * Generates waypoints from map data. It utilizes the start and end
	 * specified in the json file and calculates the route to end. Throws
	 * IllegalStateException on malformed maps.
	 * 
	 * @return Queue A queue consisting of waypoints for the given level.
	 */
	private Queue<Point> generateWaypoints() {
		int col = startx;

		if (startx != 0) {
			throw new IllegalArgumentException("The map should start somewhere in x=0");
		}

		int row = starty;
		Queue<Point> waypoints = new LinkedList<Point>();

		ETileType curTile = getTileType(col, row);
		DirectionContainer container = new DirectionContainer(waypoints, EDirection.RIGHT, row, col);
		container.addWaypoint();

		while (curTile != ETileType.PATH_END) {
			switch (curTile) {
			case GRID:
				throw new IllegalStateException("GRID is bad: " + container.col + "," + container.row);
			case EDGE_LEFT_BOTTOM:
				handleEdgeLeftBottom(container);
				break;
			case EDGE_LEFT_TOP:
				handleEdgeLeftTop(container);
				break;
			case EDGE_RIGHT_BOTTOM:
				handleEdgeRightBottom(container);
				break;
			case EDGE_RIGHT_TOP:
				handleEdgeRightTop(container);
				break;
			case PATH_EMPTY:
				handlePathEmpty(container);
				break;
			case PATH_HORIZONTAL:
				handlePathHorizontal(container);
				break;
			case PATH_START:
				handlePathStart(container);
				break;
			case PATH_VERTICAL:
				handlePathVertical(container);
				break;
			default:
				throw new IllegalStateException("Illegal waypoint at: " + container.col + "," + container.row);
			}

			// set tile to next tile
			curTile = getTileType(container.col, container.row);

			// check whether we are still in boundary. Else raise error
			if (container.col < 0 || container.row < 0 || container.col > width - 1 || container.row > height - 1) {
				throw new IllegalStateException("Waypoints leaving boundariesat at: " + container.col + "," + container.row);
			}

		}
		// here we should have reached the end if not we're unlucky
		container.addWaypoint();
		return waypoints;
	}

	private ETileType getTileType(int column, int row) {
		return ETileType.createFromTileId(grid.getArray(row).getInt(column));
	}

	private void handleEdgeLeftBottom(DirectionContainer container) {
		if (container.dir == EDirection.LEFT) {
			container.addWaypoint();
			container.dir = EDirection.UP;
			container.row--;
		} else if (container.dir == EDirection.DOWN) {
			container.addWaypoint();
			container.dir = EDirection.RIGHT;
			container.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeLeftTop(DirectionContainer container) {
		if (container.dir == EDirection.LEFT) {
			container.addWaypoint();
			container.dir = EDirection.DOWN;
			container.row++;
		} else if (container.dir == EDirection.UP) {
			container.addWaypoint();
			container.dir = EDirection.RIGHT;
			container.col++;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightBottom(DirectionContainer container) {
		if (container.dir == EDirection.RIGHT) {
			container.addWaypoint();
			container.dir = EDirection.UP;
			container.row--;
		} else if (container.dir == EDirection.DOWN) {
			container.addWaypoint();
			container.dir = EDirection.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handleEdgeRightTop(DirectionContainer container) {
		if (container.dir == EDirection.RIGHT) {
			container.addWaypoint();
			container.dir = EDirection.DOWN;
			container.row++;
		} else if (container.dir == EDirection.UP) {
			container.addWaypoint();
			container.dir = EDirection.LEFT;
			container.col--;
		} else {
			throw new IllegalStateException("Cannot send enemy through wall at: " + container.col + "," + container.row);
		}
	}

	private void handlePathEmpty(DirectionContainer container) {

		if (container.dir == EDirection.LEFT) {
			container.col--;
		} else if (container.dir == EDirection.RIGHT) {
			container.col++;
		} else if (container.dir == EDirection.DOWN) {
			container.row++;
		} else if (container.dir == EDirection.UP) {
			container.row--;
		}
	}

	private void handlePathHorizontal(DirectionContainer container) {
		if (container.dir == EDirection.LEFT) {
			container.col--;
		} else if (container.dir == EDirection.RIGHT) {
			container.col++;
		} else {
			throw new IllegalStateException("Illegal waypoint detected (not left or right) at: " + container.col + ","
					+ container.row);
		}
	}

	private void handlePathStart(DirectionContainer container) {
		// we force the map to start with one step to the right
		container.dir = EDirection.RIGHT;
		container.col++;
	}

	private void handlePathVertical(DirectionContainer container) {
		if (container.dir == EDirection.DOWN) {
			container.row++;
		} else if (container.dir == EDirection.UP) {
			container.row--;
		} else {
			throw new IllegalStateException("Illegal waypoint detected (not up or down) at: " + container.col + ","
					+ container.row);
		}
	}
}
