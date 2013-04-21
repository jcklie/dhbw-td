/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  Sebastian Muszytowski - Add waypoint support
 */

package de.dhbw.td.core.level;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pythagoras.i.Point;


public class Level {

	private static final int TILE_OFFSET = 64; // px
	private final int height;
	private final int width;
	private final int tilesize;
	private final int startx;
	private final int starty;
	private final ETileType[][] map;
	private Point[] waypoints;

	public Level(ETileType[][] map,Point[] waypoints, int tilesize, int width, int height, int startx, int starty) {
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
	
	/**
	 * @return a copy of the waypoints specified for this level
	 */
	public Point[] waypoints() {		
		return copyWaypoints(waypoints);
	}
	
	public static Point[] copyWaypoints( Point[] waypointOriginal) {
		Point[] waypointsCopy = new Point[waypointOriginal.length];
		for(int i = 0; i < waypointOriginal.length; i++ ) {
			waypointsCopy[i] = waypointOriginal[i].clone();
		}
		return waypointsCopy;
	}
	
	public int rows() { return height; }
	public int cols() { return width; }
	public int width() { return width * tilesize; }
	public int height() { return height * tilesize;	}	
	public int tilesize() { return tilesize; }
	public ETileType[][] map() { return map; }
	

}
