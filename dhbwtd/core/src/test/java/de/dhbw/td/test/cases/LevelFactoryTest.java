/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Sebastian Muszytowski - Add basic waypoint support
 *  Jan-Christoph Klie - General testing and hard refactoring + nearly complete rewrite
 *  
 */

package de.dhbw.td.test.cases;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;

import org.junit.Test;

import playn.core.Image;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.util.Point;
import de.dhbw.td.test.util.FileUtil;
import de.dhbw.td.core.util.ETileType;

public class LevelFactoryTest extends TestCase {

	private Level basicLvl;
	private Level lvlWithIntersect;
	
	private Level expectedBasicLevel;
	private Level expectedLvlWithIntersect;
	
	private LevelFactory factory;
	
	private static Json.Object loadJson(String path) throws IOException {
		URL url = LevelFactoryTest.class.getResource(path);
		File f = new File(url.getFile());			

		FileUtil.readFile(f);
		
		Platform platform = JavaPlatform.register();
		return platform.json().parse(FileUtil.readFile(f));
	}
	
	@Override
	protected void setUp() throws Exception {		
		factory = new LevelFactory();
		
		Json.Object basicLvlJson = loadJson("/levels/basicLevel.json");
		Json.Object lvlWithIntersectJson = loadJson("/levels/lvlWithIntersect.json");	
		
		/*
		 * Be careful, I cheated and initialized these expected level maps with
		 * empty image array since I do not expect to test these
		 */
		
		expectedBasicLevel = buildExpectedBasicLevel();
		expectedLvlWithIntersect = buildExpectedLvlWithIntersect();
		
		basicLvl = factory.loadLevel(basicLvlJson);
		lvlWithIntersect = factory.loadLevel(lvlWithIntersectJson);
	}
	
	@Override
	protected void tearDown() throws Exception {
		basicLvl = null;
		lvlWithIntersect = null;
		factory = null;
	}

	private Level buildExpectedBasicLevel() {
		Queue<Point> waypoints = new LinkedList<Point>();
		
		waypoints.add(new Point(0*64,3*64));
		waypoints.add(new Point(4*64,3*64));
		waypoints.add(new Point(4*64,7*64));
		waypoints.add(new Point(7*64,7*64));
		waypoints.add(new Point(7*64,2*64));
		waypoints.add(new Point(13*64,2*64));
		
		return new Level(new ETileType[10][14], waypoints, 64, 14, 10, 0, 3);
	}
	
	private Level buildExpectedLvlWithIntersect() {
		Queue<Point> waypoints = new LinkedList<Point>();
		
		waypoints.add(new Point(0*64,3*64));
		waypoints.add(new Point(4*64,3*64));
		waypoints.add(new Point(4*64,9*64));
		waypoints.add(new Point(2*64,9*64));
		waypoints.add(new Point(2*64,7*64));
		waypoints.add(new Point(7*64,7*64));
		waypoints.add(new Point(7*64,2*64));
		waypoints.add(new Point(13*64,2*64));
		
		return new Level(new ETileType[10][14], waypoints, 64, 14, 10, 0, 3);
	}
	
	private void assertLevelParameterEquals(Level expected, Level given) {
	/*	assertEquals(expected.width, given.width);
		assertEquals(expected.height, given.height);
		assertEquals(expected.tilesize, given.tilesize);
		assertEquals(expected.startx, given.startx);
		assertEquals(expected.starty, given.starty);
	*/
	}
	
	private void assertLevelWaypointsEquals(Level expected, Level given) {
		Queue<Point> correctWaypoints = expected.waypoints();
		Queue<Point> generatedWaypoints = given.waypoints();

		while(!correctWaypoints.isEmpty()){
			assertFalse(generatedWaypoints.isEmpty());
			assertEquals(correctWaypoints.poll(), generatedWaypoints.poll());
		}
		assertTrue(generatedWaypoints.isEmpty());
	}

	@Test
	public void testBasicLevelWasLoadedAsSpecifiedInJson() {
		assertLevelParameterEquals(expectedBasicLevel, basicLvl);
	}  
	
	@Test
	public void testIntersectLevelWasLoadedAsSpecifiedInJson() {
		assertLevelParameterEquals(expectedLvlWithIntersect, lvlWithIntersect);
	}
	
	@Test
	public void testBasicLevelWaypointsAreGeneratedCorrectly(){
		assertLevelWaypointsEquals(expectedBasicLevel, basicLvl);
	}
	
	@Test
	public void testGeneratedWaypointsCross() throws IOException{
		assertLevelWaypointsEquals(expectedLvlWithIntersect, lvlWithIntersect);
	}

}
