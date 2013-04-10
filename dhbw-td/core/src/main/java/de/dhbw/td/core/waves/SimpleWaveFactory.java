/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.waves;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import playn.core.Image;
import playn.core.Json;
import playn.core.Json.Object;
import de.dhbw.td.core.enemies.AEnemy.EEnemyType;
import de.dhbw.td.core.enemies.Enemy;

/**
 * This class creates new Waves for each semester on demand.
 * 
 * @author Martin Kiessling, Tobias Roeding
 * @version 1.0
 * 
 */
public class SimpleWaveFactory implements IWaveFactory {

	private final EEnemyType[] enemyTypeArray = EEnemyType.values();
	private final Image[] enemyImages = new Image[6];
	private static final int NUMBER_OF_WAVES = 12;
	private static final int NUMBER_OF_ATTRIBUTES = 3;
	private static final int UB_ENEMY_TYPES = 5;
	private int currentSemester = 0;
	private Queue<Point> waypoints;
	private int enemyCount;

	private enum EEnemyImage {

		MATH("math.png"), CODE("code.png"), TECHINF("techinf.png"), THEOINF("theoinf.png"), WIWI("wiwi.png"), SOCIAL(
				"social.png");

		public final String resourceName;

		private static final String pathToEnemies = "enemies";

		public static String getPathToImage(EEnemyType enemyType) {
			EEnemyImage enemyImage = createFromEnemyType(enemyType);
			return String.format("%s/%s", pathToEnemies, enemyImage.resourceName);
		}

		private static EEnemyImage createFromEnemyType(EEnemyType enemyType) {
			switch (enemyType) {
			case Math:
				return MATH;
			case Wiwi:
				return WIWI;
			case Social:
				return SOCIAL;
			case TechInf:
				return TECHINF;
			case TheoInf:
				return THEOINF;
			case Code:
				return CODE;
			default:
				throw new IllegalArgumentException("No EEnemyType with this type:" + enemyType);
			}
		}

		EEnemyImage(String resourceName) {
			this.resourceName = resourceName;
		}
	}

	@Override
	/**
	 * Returns upcoming wave controller and deletes it from the wave controller
	 * 
	 * @param jsonString location of the waves.json as String
	 * @param waypoints Queue of waypoints for current level
	 * @return next WaveController
	 * @see WaveController
	 */
	public WaveController nextWaveController(String jsonString, Queue<Point> waypoints) {
		return nextWaveController(json().parse(jsonString), waypoints);
	}

	@Override
	public WaveController nextWaveController(Object parsedJson, Queue<Point> waypoints) {
		int[][] semester = new int[NUMBER_OF_WAVES][NUMBER_OF_ATTRIBUTES];
		for (EEnemyType e : enemyTypeArray) {
			String pathToImage = EEnemyImage.getPathToImage(e);
			enemyImages[e.ordinal()] = assets().getImageSync(pathToImage);
		}
		this.waypoints = waypoints;
		this.enemyCount = parsedJson.getInt("enemyCount" + (currentSemester + 1));
		Json.Array semesterArr = parsedJson.getArray("sem" + (currentSemester + 1));

		for (int row = 0; row < NUMBER_OF_WAVES; row++) {
			Json.Array gridRow = semesterArr.getArray(row);

			for (int col = 0; col < NUMBER_OF_ATTRIBUTES; col++) {
				int val = gridRow.getInt(col);
				semester[row][col] = val;
			}
		}
		currentSemester++;
		return new WaveController(createWaves(semester));
	}

	/**
	 * Method creates all waves per WaveController and returns Wave-Queue.
	 * 
	 * @param semesters
	 *            array containing [wave][attributes]
	 * @return Queue with waves per semester
	 * 
	 */
	private Queue<Wave> createWaves(int[][] semesters) {
		Random r = new Random();
		Queue<Wave> waves = new LinkedList<Wave>();
		for (int waveNumber = 0; waveNumber < NUMBER_OF_WAVES; waveNumber++) {
			List<Enemy> enemies = new LinkedList<Enemy>();
			for (int enemyNumber = 0; enemyNumber < enemyCount; enemyNumber++) {
				int maxHealth = semesters[waveNumber][0];
				double speed = semesters[waveNumber][1];
				int bounty = semesters[waveNumber][2];
				int next = r.nextInt(UB_ENEMY_TYPES);
				EEnemyType enemyType = enemyTypeArray[next];
				Image enemyImage = enemyImages[next];
				enemies.add(new Enemy(maxHealth, speed, bounty, enemyType, new LinkedList<Point>(waypoints), enemyImage));
			}
			Wave wave = new Wave(waveNumber, enemies);
			waves.add(wave);
		}
		return waves;
	}

	/**
	 * 
	 * @return currentSemester as integer
	 */
	public int getCurrentSemester() {
		return currentSemester;
	}

	/**
	 * 
	 * @return waypoints as Queue<Point>
	 */
	public Queue<Point> getWaypoints() {
		return waypoints;
	}

	/**
	 * 
	 * @return enemyCount as integer
	 */
	public int getEnemyCount() {
		return enemyCount;
	}
}