/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  Sebastian Muszytowski - Add waypoint support
 */

package de.dhbw.td.core.level;

import java.util.LinkedList;
import java.util.Queue;

import de.dhbw.td.core.util.Point;
public class Level {

	private static final int TILE_OFFSET = 64; // px
	private final int height;
	private final int width;
	private final int tilesize;
	private final int startx;
	private final int starty;
	private final ETileType[][] map;
	private Queue<Point> waypoints;

	private static Queue<Point> changeCopyWaypoints(Queue<Point> waypoints) {
		LinkedList<Point> cloned = new LinkedList<Point>();
		for (Point p : waypoints) {
			cloned.add(new Point(p));
		}
		cloned.getFirst().translate(-TILE_OFFSET, 0);
		cloned.getLast().translate(TILE_OFFSET, 0);
		return cloned;
	}

	public static Queue<Point> copyWaypoints(Queue<Point> waypoints) {
		LinkedList<Point> cloned = new LinkedList<Point>();
		for (Point p : waypoints) {
			cloned.add(new Point(p));
		}
		return cloned;
	}

	public Level(ETileType[][] map, Queue<Point> waypoints, int tilesize, int width, int height, int startx, int starty) {
		this.tilesize = tilesize;
		this.width = width;
		this.height = height;
		this.startx = startx;
		this.starty = starty;
		this.waypoints = waypoints;

		if (map.length != height) {
			throw new IllegalArgumentException("Specified height differs from height of map array!");
		}

		this.map = map;
	}
	
	public int rows() {
		return height;
	}

	public int cols() {
		return width;
	}

	public int width() {
		return width * tilesize;
	}

	public int height() {
		return height * tilesize;
	}
	
	public int tilesize() {
		return tilesize;
	}

	public ETileType[][] map() {
		return map;
	}

	/**
	 * @return a <b>copy</b> of the waypoints specified for this level
	 */
	public Queue<Point> waypoints() {
		return changeCopyWaypoints(waypoints);
	}
}
