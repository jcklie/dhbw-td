/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.core;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.waves.IWaveFactory;
import de.dhbw.td.core.waves.SimpleWaveFactory;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;

public class WaveControllerTest extends TestCase {

	private IWaveFactory waveLoader;
	private WaveController waveController;
	private Json.Object jason;

	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/waves.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		jason = platform.json().parse(FileUtil.readFile(f));

		waveLoader = new SimpleWaveFactory();
		waveController = waveLoader.nextWaveController(jason);
	}

	@Override
	protected void tearDown() throws Exception {
		waveController = null;
	}

	public void testWaveControllerCreatedAllWaves() {
		// test if WaveFactory created 12 Waves
		assertEquals(12, waveController.waves.size());
	}

	public void testWaveAttributes() {
		for (int i = 0; i < 12; i++) {
			// test if every wave has the correct number and if the waves are in
			// order
			assertEquals(i, waveController.waves.poll().waveNumber);
		}
	}

	public void testNextWaveFunction() {
		for (int i = 0; i < 12; i++) {
			// test if nextWave function returns the next wave in order
			assertEquals(i, waveController.nextWave().waveNumber);
		}
	}

	public void testNextWaveController() {
		// test if nextWaveController throws next WaveController in order
		// (example last wavecontroller, last wave)
		int cnt = 0;
		do {
			waveController = waveLoader.nextWaveController(jason);
			cnt++;
		} while (cnt < 5);
		// test if attributes of enemies equals values of sem6 - wave 6
		Wave testWave = waveController.waves.peek();
		do {
			testWave = waveController.nextWave();
		} while (testWave.waveNumber < 11);
		for (int i = 0; i < 9; i++) {
			Enemy testEnemy = testWave.enemies.get(i);
			assertEquals(70, testEnemy.maxHealth);
			assertEquals(70, testEnemy.curHealth);
			assertTrue(testWave.enemies.get(i).alive);
			assertEquals(10.0, testEnemy.speed, 0.001);
			assertEquals(6, testEnemy.bounty);
			assertEquals(12, testEnemy.penalty);
		}
	}

	public void testEnemyAttributes() {
		// test if attributes of first 9 enemies equals values of sem1 - wave 1
		Wave testWave = waveController.waves.peek();
		for (int i = 0; i < 9; i++) {
			Enemy testEnemy = testWave.enemies.get(i);
			assertEquals(10, testEnemy.maxHealth);
			assertEquals(10, testEnemy.curHealth);
			assertTrue(testEnemy.alive);
			assertEquals(10.0, testEnemy.speed, 0.001);
			assertEquals(1, testEnemy.bounty);
			assertEquals(2, testEnemy.penalty);
		}
	}
}