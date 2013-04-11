package de.dhbw.td.core;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.util.EFlavor;

public class EnemyTest extends TestCase {
	
	Enemy enemy;
	
	@Override
	protected void setUp() throws Exception {
		enemy = new Enemy(10, 1, 1, EFlavor.Code, getWaypoints(), null);
	}
	
	@Override
	protected void tearDown() throws Exception {
		enemy = null;
	}
	
	/**
	 * These waypoints describe a circle starting at (x=0, y=0)
	 * o -> o
	 * ^	|
	 * |	v
	 * o <- o
	 * @return Waypoint queue 
	 */
	private Queue<Point> getWaypoints() {
		Queue<Point> waypoints = new LinkedList<Point>();
		
		waypoints.add(new Point(0,0));
		waypoints.add(new Point(1,0));
		waypoints.add(new Point(1,1));
		waypoints.add(new Point(0,1));
		waypoints.add(new Point(0,0));
		
		return waypoints;
	}
	
	/**
	 * I move an enemy in a circle here and check whether
	 * he follows his waypoints. I update with a speed
	 * of 1 px / second, so delta = 1000ms results in
	 * a movement of one position.
	 */
	public void testEnemyUpdate() {		
		Queue<Point> wp = getWaypoints();
		assertEquals(wp.poll(), enemy.getCurrentPosition());		
		enemy.update(1000);
		
		assertEquals(wp.poll(), enemy.getCurrentPosition());		
		enemy.update(1000);
		
		assertEquals(wp.poll(), enemy.getCurrentPosition());		
		enemy.update(1000);
		
		assertEquals(wp.poll(), enemy.getCurrentPosition());		
		enemy.update(1000);
		
		assertEquals(wp.poll(), enemy.getCurrentPosition());		
		enemy.update(1000);
	}	

}
