/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.test.cases;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveControllerFactory;
import de.dhbw.td.test.util.FileUtil;

public class WaveControllerTest extends TestCase {

	private WaveControllerFactory waveLoader;
	private WaveController waveController;
	private Json.Object jason;
	private Point[] waypoints;

	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/wave1.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		jason = platform.json().parse(FileUtil.readFile(f));

		waypoints = createWaypoints();

		waveLoader = new WaveControllerFactory();
		waypoints = Level.copyWaypoints(waypoints);
		waveController = waveLoader.constructWaveController(jason, waypoints);
	}
	
	private Point[] createWaypoints() {
		Point[] wp = {new Point(0, 3), new Point(4, 3), new Point(4, 7), new Point(7, 7), new Point(7, 2), new Point(13, 2) };
		return wp;
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
			assertEquals(i, waveController.getWaves().peek().waveNumber());
			// test if wave has 12 enemies listed
			assertEquals(12, waveController.getWaves().poll().enemyCount());
		}
	}

	public void testNextWaveFunction() {
		for (int i = 0; i < 12; i++) {
			// test if nextWave function returns the next wave in order
			assertEquals(i, waveController.nextWave().waveNumber());
		}
	}

	public void testNextWaveController() {
		// test if nextWaveController throws next WaveController in order
		// (example last wavecontroller, last wave)
		int cnt = 0;
		do {
			waveController = waveLoader.constructWaveController(jason, Level.copyWaypoints(waypoints));
			cnt++;
		} while (cnt < 5);
		// test if attributes of enemies equals values of sem6 - wave 12
		Wave testWave = null;
		do {
			testWave = waveController.nextWave();
		} while (testWave.waveNumber() < 11);
		for (int i = 0; i < 12; i++) {
			Enemy testEnemy = testWave.enemies().get(i);
			assertEquals(29, testEnemy.maxHealth());
			assertEquals(29, testEnemy.curHealth());
			assertTrue(testWave.enemies().get(i).alive());
			assertEquals(100.0, testEnemy.speed(), 0.001);
			assertEquals(1, testEnemy.bounty());
			assertEquals(2, testEnemy.penalty());
		}
	}

	public void testEnemyAttributes() {
		// test if attributes of enemies equals values of sem1 - wave 1
		Wave testWave = waveController.getWaves().peek();
		for (int i = 0; i < 12; i++) {
			Enemy testEnemy = testWave.enemies().get(i);
			assertEquals(10, testEnemy.maxHealth());
			assertEquals(10, testEnemy.curHealth());
			assertTrue(testEnemy.alive());
			assertEquals(400.0, testEnemy.speed(), 0.001);
			assertEquals(1, testEnemy.bounty());
			assertEquals(2, testEnemy.penalty());

		}
	}
}