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
import de.dhbw.td.core.waves.IWaveController;
import de.dhbw.td.core.waves.SimpleWaveFactory;
import de.dhbw.td.core.waves.WaveController;

public class WaveControllerTest extends TestCase {

	private IWaveController waveLoader;
	private WaveController waveController;

	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/waves.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		Json.Object jason = platform.json().parse(FileUtil.readFile(f));

		waveLoader = new SimpleWaveFactory();
		waveController = waveLoader.loadWaveController(jason);
	}

	@Override
	protected void tearDown() throws Exception {
		waveController = null;
	}

	public void testWaveControllerCreatedAllWaves() {
		// test if WaveFactory created 36 Waves
		assertEquals(36, waveController.waves.size());
	}

	public void testWaveAttributes() {
		for (int i = 0; i < 36; i++) {
			// test if every wave has the correct number and if the waves are in
			// order
			assertEquals(i, waveController.waves.peek().waveNumber);
			// test if every wave contains 12 enemies
			assertEquals(12, waveController.waves.poll().enemyCount);
		}
	}

	public void testNextWaveFunction() {
		for (int i = 0; i < 36; i++) {
			// test if nextWave function returns the next wave in order
			assertEquals(i, waveController.nextWave().waveNumber);
		}
	}

	public void testEnemyAttributes() {
		// test if attributes of first 12 enemies equals values of sem1 - wave 1
		for (int i = 0; i < 12; i++) {
			assertEquals(10, waveController.waves.peek().enemies.get(i).maxHealth);
			assertEquals(10, waveController.waves.peek().enemies.get(i).curHealth);
			assertEquals(true, waveController.waves.peek().enemies.get(i).alive);
			assertEquals(10.0, waveController.waves.peek().enemies.get(i).speed);
			assertEquals(1, waveController.waves.peek().enemies.get(i).bounty);
			assertEquals(2, waveController.waves.peek().enemies.get(i).penalty);
		}
		do {
			waveController.nextWave();
		} while (waveController.currentWave.waveNumber != 34);
		// test if attributes of first 12 enemies equals values of sem6 - wave 6
		for (int i = 0; i < 12; i++) {
			assertEquals(70, waveController.waves.peek().enemies.get(i).maxHealth);
			assertEquals(70, waveController.waves.peek().enemies.get(i).curHealth);
			assertEquals(true, waveController.waves.peek().enemies.get(i).alive);
			assertEquals(10.0, waveController.waves.peek().enemies.get(i).speed);
			assertEquals(6, waveController.waves.peek().enemies.get(i).bounty);
			assertEquals(12, waveController.waves.peek().enemies.get(i).penalty);
		}
	}
}