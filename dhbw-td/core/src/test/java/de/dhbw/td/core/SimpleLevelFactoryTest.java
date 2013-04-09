/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Sebastian Muszytowski - Add basic waypoint support
 *  Jan-Christoph Klie - General testing and hard refactoring + nearly complete rewrite
 *  
 */

package de.dhbw.td.core;

import java.awt.Point;
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
import de.dhbw.td.core.level.ILevelFactory;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.SimpleLevelFactory;

public class SimpleLevelFactoryTest extends TestCase {

	private Level basicLvl;
	private Level lvlWithIntersect;
	
	private Level expectedBasicLevel;
	private Level expectedLvlWithIntersect;
	
	private ILevelFactory factory;
	
	private static Json.Object loadJson(String path) throws IOException {
		URL url = SimpleLevelFactoryTest.class.getResource(path);
		File f = new File(url.getFile());			

		FileUtil.readFile(f);
		
		Platform platform = JavaPlatform.register();
		return platform.json().parse(FileUtil.readFile(f));
	}
	
	@Override
	protected void setUp() throws Exception {		
		factory = new SimpleLevelFactory();
		
		Json.Object basicLvlJson = loadJson("/levels/basicLevel.json");
		Json.Object lvlWithIntersectJson = loadJson("/levels/lvlWithIntersect.json");	
		
		/*
		 * Be careful, I cheated and initialized these expected level with empty
		 * image array since I do not expect to test these
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
		
		waypoints.add(new Point(0,3));
		waypoints.add(new Point(4,3));
		waypoints.add(new Point(4,7));
		waypoints.add(new Point(7,7));
		waypoints.add(new Point(7,2));
		waypoints.add(new Point(13,2));
		
		return new Level(new Image[10][14], waypoints, 64, 14, 10, 0, 3);
	}
	
	private Level buildExpectedLvlWithIntersect() {
		Queue<Point> waypoints = new LinkedList<Point>();
		
		waypoints.add(new Point(0,3));
		waypoints.add(new Point(4,3));
		waypoints.add(new Point(4,9));
		waypoints.add(new Point(2,9));
		waypoints.add(new Point(2,7));
		waypoints.add(new Point(7,7));
		waypoints.add(new Point(7,2));
		waypoints.add(new Point(13,2));
		
		return new Level(new Image[10][14], waypoints, 64, 14, 10, 0, 3);
	}
	
	private void assertLevelParameterEquals(Level expected, Level given) {
		assertEquals(expected.width, given.width);
		assertEquals(expected.height, given.height);
		assertEquals(expected.tilesize, given.tilesize);
		assertEquals(expected.startx, given.startx);
		assertEquals(expected.starty, given.starty);
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
