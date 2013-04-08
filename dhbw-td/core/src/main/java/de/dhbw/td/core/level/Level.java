/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  Sebastian Muszytowski - Add waypoint support
 */

package de.dhbw.td.core.level;

import java.awt.Point;
import java.util.Arrays;
import java.util.Queue;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.game.IDrawable;

public class Level implements IDrawable{
	
	public final int height;
	public final int width;
	public final int tilesize;
	public final Image[][] map;
	public final Queue<Point> waypoints;

	public Level(Image[][] map, Queue<Point> waypoints, int tilesize, int width, int height) {
		this.tilesize = tilesize;
		this.width = width;
		this.height = height;
		this.waypoints = waypoints;
			
		if( map.length != height) {
			throw new IllegalArgumentException("Specified height differs from height of map array!");
		}
		
		this.map = map;
	}
	
	public int width() {
		return width * tilesize;
	}
	
	public int height() {
		return height * tilesize;
	}
	
	@Override
	public void draw(Surface surf) {
		surf.clear();
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {				
				surf.drawImage(map[row][col], col*tilesize, row*tilesize);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("height=%s\nwidth=%s\ntilesize=%s\nmap=%s",
				height, width, tilesize, Arrays.toString(map));
	}


}
