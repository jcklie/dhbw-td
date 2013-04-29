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
import java.util.Random;

import playn.core.Json;
import playn.core.Json.Object;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.util.EFlavor;


/**
 * This class creates new Waves for each semester on demand.
 * 
 * @author Martin Kiessling, Tobias Roeding
 * 
 */
public class WaveControllerFactory {

	private static final int NUMBER_OF_WAVES = 12;
	private static final int NUMBER_OF_ATTRIBUTES = 3;
	private static final int UB_ENEMY_TYPES = 6;
	private static final EFlavor[] enemyTypeArray = EFlavor.values();
	
	private static final Random r = new Random();
	
	private int currentSemester = 0;
	private Point[] waypoints;
	private int enemyCount;
		
	/**
	 * Returns upcoming wave controller and deletes it from the wave controller
	 * 
	 * @param jsonString location of the waves.json as String
	 * @param waypoints Queue of waypoints for current level
	 * @return next WaveController
	 * @see WaveController
	 */
	public WaveController constructWaveController(String jsonString, Point[] waypointArray) {
		return constructWaveController(json().parse(jsonString), waypointArray);
	}

	public WaveController constructWaveController(Object parsedJson, Point[] waypointArray) {
		this.waypoints = waypointArray;
		
		int[][] semester = new int[NUMBER_OF_WAVES][];

		this.enemyCount = parsedJson.getInt("enemyCount");
		Json.Array semesterArr = parsedJson.getArray("waves");

		for (int row = 0; row < NUMBER_OF_WAVES; row++) {
			Json.Array gridRow = semesterArr.getArray(row);
			
			semester[row] = parseEnemyStats(gridRow);
		}
		
		currentSemester++;
		
		Queue<Wave> waves = createWaves(semester);
		waves.add(createEndboss(parsedJson.getArray("endboss")));
		
		return new WaveController(waves);
	}

	/**
	 * Method creates all waves per WaveController and returns Wave-Queue.
	 * 
	 * @param semesters array containing [wave][attributes]
	 * @return Queue with waves per semester
	 * 
	 */
	private Queue<Wave> createWaves(int[][] semesters) {
		Queue<Wave> waves = new LinkedList<Wave>();
		for (int waveNumber = 0; waveNumber < NUMBER_OF_WAVES; waveNumber++) {
			List<Enemy> enemies = new LinkedList<Enemy>();
			for (int enemyNumber = 0; enemyNumber < enemyCount; enemyNumber++) {	
				enemies.add(createEnemy(semesters[waveNumber]));
			}
			Wave wave = new Wave(waveNumber, enemies);
			waves.add(wave);
		}
		return waves;
	}
	
	/**
	 * Creates the wave of the endboss with the given stats
	 * @param json The json array
	 * @return
	 */
	private Wave createEndboss(Json.Array json) {
		List<Enemy> enemy = new LinkedList<Enemy>();		
		enemy.add(createEnemy(parseEnemyStats(json)));
		return new Wave(42, enemy);
	}
	
	/**
	 * Converts the json array to an int array with 3 fields 
	 * which holds the stats of an enemy
	 * @param json The json array
	 * @return The array containing the parsed stats
	 */
	private int[] parseEnemyStats(Json.Array json) {
		int[] stats = new int[NUMBER_OF_ATTRIBUTES];
		
		for (int i = 0; i < NUMBER_OF_ATTRIBUTES; i++) {
			stats[i] = json.getInt(i);
		}
		
		return stats;
	}

	/**
	 * Creates an enemy with the given stats
	 * @param stats Array must contain of 3 field.<br>
	 * 		<b></code>maxHealth = stats[0]</code></b><br>
	 * 		<b></code>speed = stats[1]</code></b><br>
	 * 		<b></code>bounty = stats[2]</code></b>
	 * @return The created enemy with a random {@link EFlavor}
	 */
	private Enemy createEnemy(int[] stats) {
		int next = r.nextInt(UB_ENEMY_TYPES);
		EFlavor enemyType = enemyTypeArray[next];
		
		return new Enemy(stats[0], stats[1], stats[2], enemyType, waypoints);
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
	 * @return enemyCount as integer
	 */
	public int getEnemyCount() {
		return enemyCount;
	}
}