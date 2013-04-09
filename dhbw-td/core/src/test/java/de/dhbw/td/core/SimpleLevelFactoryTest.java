/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.core;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.level.ILevelFactory;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.SimpleLevelFactory;

public class SimpleLevelFactoryTest extends TestCase {

	private Level lvl;	
	private ILevelFactory factory;
	
	@Override
	protected void setUp() throws Exception {		
		URL url = this.getClass().getResource("/levels/level1.json");
		File f = new File(url.getFile());			

		FileUtil.readFile(f);
		
		Platform platform = JavaPlatform.register();
		Json.Object jason = platform.json().parse(FileUtil.readFile(f));
		
		factory = new SimpleLevelFactory();
		lvl = factory.loadLevel(jason);
	}
	
	@Override
	protected void tearDown() throws Exception {
		lvl = null;
		factory = null;
	}

	@Test
	public void testLevelWasLoadedAsSpecifiedInJson() {
		assertEquals(14, lvl.width);
		assertEquals(10, lvl.height);
		assertEquals(64, lvl.tilesize);
		assertEquals(0, lvl.startx);
		assertEquals(3, lvl.starty);
	}  
	
	@Test
	public void testGeneratedWaypoints(){
		Queue<Point> ref = new LinkedList<Point>();
		ref.add(new Point(0,3));
		ref.add(new Point(4,3));
		ref.add(new Point(4,7));
		ref.add(new Point(7,7));
		ref.add(new Point(7,2));
		ref.add(new Point(13,2));
		while(!ref.isEmpty()){
			assertFalse(lvl.waypoints.isEmpty());
			assertEquals(ref.poll(), lvl.waypoints.poll());
		}
		assertTrue(lvl.waypoints.isEmpty());
	}
	
	@Test
	public void testGeneratedWaypointsCross() throws IOException{
		URL url = this.getClass().getResource("/levels/crosstest.json");
		File f = new File(url.getFile());			

		FileUtil.readFile(f);
		
		Platform platform = JavaPlatform.register();
		Json.Object jason = platform.json().parse(FileUtil.readFile(f));
		
		factory = new SimpleLevelFactory();
		lvl = factory.loadLevel(jason);
		
		Queue<Point> ref = new LinkedList<Point>();
		ref.add(new Point(0,3));
		ref.add(new Point(4,3));
		ref.add(new Point(4,9));
		ref.add(new Point(2,9));
		ref.add(new Point(2,7));
		ref.add(new Point(7,7));
		ref.add(new Point(7,2));
		ref.add(new Point(13,2));
		while(!ref.isEmpty()){
			assertFalse(lvl.waypoints.isEmpty());
			assertEquals(ref.poll(), lvl.waypoints.poll());
		}
		assertTrue(lvl.waypoints.isEmpty());
		
		factory = null;
		lvl = null;
	}
	
	@Test
	public void testDummy() {
		assertTrue(true);
		
	}

}
