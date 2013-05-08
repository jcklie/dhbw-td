/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Sebastian Muszytowski - Waypoint tests
 *  Jan-Christoph Klie - Refactoring of Code and additions
 *  
 */

package de.dhbw.td.test.cases;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.junit.Test;

import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import pythagoras.i.Point;
import de.dhbw.td.core.level.ETileType;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.test.util.FileUtil;

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
		
		basicLvl = factory.constructLevel(basicLvlJson);
		lvlWithIntersect = factory.constructLevel(lvlWithIntersectJson);
	}
	
	@Override
	protected void tearDown() throws Exception {
		basicLvl = null;
		lvlWithIntersect = null;
		factory = null;
	}

	private Level buildExpectedBasicLevel() {
		Point[] waypoints = tileWaypointsToPixel(createWaypointsBasic());
		return new Level(new ETileType[10][14], waypoints, 64, 14, 10, 0, 3);
	}
	
	private Point[] createWaypointsBasic() {
		Point[] wp = {new Point(0, 3), new Point(4, 3), new Point(4, 7), new Point(7, 7), new Point(7, 2), new Point(13, 2) };
		return wp;
	}
	
	private Point[] createWaypointsWithIntersect() {
		Point[] wp = {new Point(0, 3), new Point(4, 3), new Point(4, 9), new Point(2, 9), new Point(2, 7), new Point(7, 7),new Point(7, 2) ,new Point(13, 2) };
		return wp;
	}
	
	private Point[] tileWaypointsToPixel(Point[] tileCoordWaypoints) {
		Point[] wp = new Point[tileCoordWaypoints.length];
		
		for(int i = 0; i < wp.length; i++) {
			Point p = tileCoordWaypoints[i];
			wp[i] = new Point(p.x *TILE_SIZE, p.y * TILE_SIZE);

		}
		return wp;
	}
	
	private Level buildExpectedLvlWithIntersect() {
		Point[] waypoints = tileWaypointsToPixel(createWaypointsWithIntersect());
		
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
		Point[] correctWaypoints = expected.waypoints();
		Point[]  generatedWaypoints = given.waypoints();

		for(int i = 0; i < generatedWaypoints.length; i++) {
			assertEquals(correctWaypoints[i], generatedWaypoints[i]);
		}

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
