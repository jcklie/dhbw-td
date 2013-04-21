package de.dhbw.td.test.cases;


import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.test.mock.MockImage;

public class EnemyTest extends TestCase {
	
	Enemy enemy;
	
	@Override
	protected void setUp() throws Exception {
		enemy = new Enemy(10, 1, 1, EFlavor.PROGRAMMING, getWaypoints());
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
	private Point[] getWaypoints() {
		Point[] waypoints = {new Point(0,0), new Point(1,0), new Point(1,1),new Point(0,1),new Point(0,0) };
		return waypoints;
	}
	
	/**
	 * I move an enemy in a circle here and check whether
	 * he follows his waypoints. I update with a speed
	 * of 1 px / second, so delta = 1000ms results in
	 * a movement of one position.
	 */
	public void testEnemyUpdate() {		
		Point[] wp = getWaypoints();
		int i = 0;
		assertEquals(wp[i++], enemy.currentPosition());		
		enemy.update(1000);
		
		assertEquals(wp[i++], enemy.currentPosition());		
		enemy.update(1000);
		
		assertEquals(wp[i++], enemy.currentPosition());		
		enemy.update(1000);
		
		assertEquals(wp[i++], enemy.currentPosition());		
		enemy.update(1000);
		
		assertEquals(wp[i++], enemy.currentPosition());		
		enemy.update(1000);
	}	

}
