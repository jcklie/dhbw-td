/*  Copyright (C) 2013 by Martin Kiessling, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling - All
 */

package de.dhbw.td.core.waves;

import static playn.core.PlayN.json;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import playn.core.Json;
import playn.core.Json.Object;
import de.dhbw.td.core.enemies.AEnemy.EEnemyType;
import de.dhbw.td.core.enemies.Enemy;

public class SimpleWaveFactory implements IWaveController {

	@Override
	public WaveController loadWaveController(String jsonString) {
		return loadWaveController(json().parse(jsonString));
	}

	@Override
	public WaveController loadWaveController(Object parsedJson) {

		int[][][] sems = new int[6][6][3];
		for (int cnt = 0; cnt < 6; cnt++) {
			Json.Array sem = parsedJson.getArray("sem" + cnt + 1);

			for (int row = 0; row < 6; row++) {
				Json.Array gridRow = sem.getArray(row);

				for (int col = 0; col < 3; col++) {
					int val = gridRow.getInt(col);
					sems[cnt][row][col] = val;
				}
			}
		}
		Queue<Wave> waves = createWaves(sems);
		return new WaveController(waves);
	}

	private Queue<Wave> createWaves(int[][][] sems) {
		Queue<Wave> waves = new LinkedList<Wave>();
		for (int waveNumber = 0; waveNumber < 36; waveNumber++) {
			List<Enemy> enemies = new LinkedList<Enemy>();
			for (int enemyNumber = 0; enemyNumber < 12; enemyNumber++) {
				int maxHealth = sems[waveNumber / 6][waveNumber % 6][0];
				double speed = sems[waveNumber / 6][waveNumber % 6][1];
				int bounty = sems[waveNumber / 6][waveNumber % 6][2];
				EEnemyType enemyType = EEnemyType.Math;
				enemies.add(new Enemy(maxHealth, speed, bounty, enemyType));
			}
			Wave wave = new Wave(waveNumber, enemies);
		}
		return waves;
	}
}