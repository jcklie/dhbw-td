/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.test.cases;

import java.awt.Point;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;
import de.dhbw.td.test.util.FileUtil;

public class WaveControllerTest extends TestCase {

	private WaveFactory waveLoader;
	private WaveController waveController;
	private Json.Object jason;
	private Queue<Point> waypoints;

	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/wave1.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		jason = platform.json().parse(FileUtil.readFile(f));

		waypoints = new LinkedList<Point>();
		
		waypoints.add(new Point(0,3));
		waypoints.add(new Point(4,3));
		waypoints.add(new Point(4,7));
		waypoints.add(new Point(7,7));
		waypoints.add(new Point(7,2));
		waypoints.add(new Point(13,2));
		
		waveLoader = new WaveFactory();
		
		waveController = waveLoader.nextWaveController(jason, Level.copyWaypoints(waypoints));
		waypoints.poll();
	}

	@Override
	protected void tearDown() throws Exception {
		waveController = null;
	}

	public void testWaveControllerCreatedAllWaves() {
		// test if WaveFactory created 12 Waves
		assertEquals(12, waveController.getWaves().size());
	}

	public void testWaveAttributes() {
		for (int i = 0; i < 12; i++) {
			// test if every wave has the correct number and if the waves are in
			// order
			assertEquals(i, waveController.getWaves().peek().getWaveNumber());
			// test if wave has 12 enemies listed
			assertEquals(12, waveController.getWaves().poll().getEnemyCount());
		}
	}

	public void testNextWaveFunction() {
		for (int i = 0; i < 12; i++) {
			// test if nextWave function returns the next wave in order
			assertEquals(i, waveController.nextWave().getWaveNumber());
		}
	}

	public void testNextWaveController() {
		// test if nextWaveController throws next WaveController in order
		// (example last wavecontroller, last wave)
		int cnt = 0;
		do {
			waveController = waveLoader.nextWaveController(jason, Level.copyWaypoints(waypoints));
			cnt++;
		} while (cnt < 5);
		// test if attributes of enemies equals values of sem6 - wave 12
		Wave testWave = null;
		do {
			testWave = waveController.nextWave();
		} while (testWave.getWaveNumber() < 11);
		for (int i = 0; i < 12; i++) {
			Enemy testEnemy = testWave.getEnemies().get(i);
			assertEquals(29, testEnemy.getMaxHealth());
			assertEquals(29, testEnemy.getCurHealth());
			assertTrue(testWave.getEnemies().get(i).isAlive());
			assertEquals(100.0, testEnemy.getSpeed(), 0.001);
			assertEquals(1, testEnemy.getBounty());
			assertEquals(2, testEnemy.getPenalty());
		}
	}

	public void testEnemyAttributes() {
		// test if attributes of enemies equals values of sem1 - wave 1
		Wave testWave = waveController.getWaves().peek();
		for (int i = 0; i < 12; i++) {
			Enemy testEnemy = testWave.getEnemies().get(i);
			assertEquals(10, testEnemy.getMaxHealth());
			assertEquals(10, testEnemy.getCurHealth());
			assertTrue(testEnemy.isAlive());
			assertEquals(400.0, testEnemy.getSpeed(), 0.001);
			assertEquals(1, testEnemy.getBounty());
			assertEquals(2, testEnemy.getPenalty());
			assertEquals(waypoints, testEnemy.getWaypoints());
		}
	}
}