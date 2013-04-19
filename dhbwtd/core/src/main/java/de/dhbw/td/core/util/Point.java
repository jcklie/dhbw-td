/*  Copyright (C) 2013 by Jan-Christoph Klie Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core.util;


/**
 * Rewrite of java.awt.Point, since GWT does not compile awt packages
 * 
 * A point representing a location in (x, y) coordinate space, specified in
 * integer precision.
 * 
 * @author Jan-Christoph Klie
 * 
 */
public class Point implements Cloneable {

	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point() {
		this(0, 0);
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * Translates this point, at location (x, y), by dx along the x axis and dy
	 * along the y axis so that it now represents the point (x + dx, y + dy).
	 * 
	 * @param dx
	 *            Offset in x direction
	 * @param dy
	 *            Offset in y direction
	 */
	public void translate(int dx, int dy) {
		x += dx;
		y += dy;
	}

	/**
	 * Returns the X coordinate of the point
	 * 
	 * @return the X coordinate of the point
	 */
	public int x() {
		return x;
	}

	/**
	 * Returns the Y coordinate of the point
	 * 
	 * @return the Y coordinate of the point
	 */
	public int y() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public void setLocation(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public double distance(Point p) {
		double px = p.x() - this.x();
		double py = p.y() - this.y();
		return Math.sqrt(px * px + py * py);
	}

}
