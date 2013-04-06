/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
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

public class SimpleWaveFactory implements IWaveFactory {

	public final EEnemyType[] enemyTypeArray = EEnemyType.values();
	public int currentSemester = 0;

	@Override
	public WaveController nextWaveController(String jsonString) {
		return nextWaveController(json().parse(jsonString));
	}

	@Override
	public WaveController nextWaveController(Object parsedJson) {
		int[][] semester = new int[6][3];
		Json.Array semesterArr = parsedJson.getArray("sem" + (currentSemester + 1));

		for (int row = 0; row < 6; row++) {
			Json.Array gridRow = semesterArr.getArray(row);

			for (int col = 0; col < 3; col++) {
				int val = gridRow.getInt(col);
				semester[row][col] = val;
			}
		}
		currentSemester++;
		return new WaveController(createWaves(semester));
	}

	private Queue<Wave> createWaves(int[][] semesters) {
		Queue<Wave> waves = new LinkedList<Wave>();
		for (int waveNumber = 0; waveNumber < 12; waveNumber++) {
			List<Enemy> enemies = new LinkedList<Enemy>();
			for (int enemyNumber = 0; enemyNumber < 12; enemyNumber++) {
				int maxHealth = semesters[waveNumber % 6][0];
				double speed = semesters[waveNumber % 6][1];
				int bounty = semesters[waveNumber % 6][2];
				EEnemyType enemyType = enemyTypeArray[(int) (Math.random() * 5)];
				enemies.add(new Enemy(maxHealth, speed, bounty, enemyType));
			}
			Wave wave = new Wave(waveNumber, enemies);
			waves.add(wave);
		}
		return waves;
	}
}