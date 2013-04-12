/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First basic version
 *  Benedict Holste - The more advanced version
 */

package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;

import java.util.List;

import playn.core.Surface;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;

/*
 * TODO: Close this class as good as possible, so it encapsulates all setting
 * of attributes w/o distinct setter methods
 */
public class GameState implements IUpdateable {
	
	private final int INITIAL_CREDITS = 25;
	private final int INITIAL_LIFEPOINTS = 100;
	
	private final int FACTOR_DELTA_FF = 4;
	
	private int levelCount;
	private int waveCount;
	
	private int credits;
	private int lifepoints;
	
	private boolean fastForward = false;
	private boolean paused = false;
	private boolean changed = true;
	private boolean finished = false;
	
	private List<Enemy> enemies;
	
	private WaveFactory waveFactory;
	private WaveController currentWaveController;
	private Wave currentWave;
	
	private LevelFactory levelFactory;
	private Level currentLevel;

	public GameState() {
		
		levelFactory = new LevelFactory();
		waveFactory = new WaveFactory();
		
		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;
		
		levelCount = 0;
		waveCount = 0;
		
		nextLevel();
		nextWaveController();
		loadNextWave();
	}
	
	/**
	 * Loads the next level.
	 */
	private void nextLevel() {
		levelCount++;
		waveCount = 0;
		try {
			String pathToImage = String.format("%slevel%s.json", TowerDefense.PATH_LEVELS, levelCount);
			String levelJSON = assets().getTextSync(pathToImage);
			currentLevel = levelFactory.loadLevel(levelJSON);
			
		} catch (Exception e) {
			log().error(e.toString());
		}
	}
	
	/**
	 * Loads the WaveController for the next level.
	 */
	private void nextWaveController() {
		try {
			String pathToText = String.format("%swaves%s.json", TowerDefense.PATH_WAVES, levelCount);
			String waveJSON = assets().getTextSync(pathToText);
			currentWaveController = waveFactory.nextWaveController(waveJSON, currentLevel.waypoints());
		} catch (Exception e) {
			log().error(e.toString());
		}
	}
	
	/**
	 * Loads the next wave in current WaveController.
	 */
	private void loadNextWave() {
		waveCount++;
		currentWave = currentWaveController.nextWave();
		enemies = currentWave.getEnemies();
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.getCurrentPosition().translate(-i * currentLevel.tilesize, 0);
		}
		changed = true;
	}

	/**
	 * Checks whether game state has changed.
	 * 
	 * @return true, if game state has changed
	 */
	public boolean hasChanged() {
		if (changed) {
			changed = false;
			return true;
		}
		return false;
	}

	/**
	 * Draws all enemies.
	 * 
	 * @param surf The Surface to draw on
	 */
	public void drawEnemies(Surface surf) {
		
		surf.clear();
		
		for(Enemy e : enemies) {
			e.draw(surf);
		}
	}
	
	/**
	 * Draws the current level.
	 * 
	 * @param surf The Surface to draw on
	 */
	public void drawLevel(Surface surf) {
		currentLevel.draw(surf);
	}

	@Override
	public void update(double delta) {
		if (!finished) {

			// check for fast forward mode
			if (fastForward) {
				delta *= FACTOR_DELTA_FF;
			}
			
			updateAllEnemies(delta);
			
			// check, if there are enemies left
			if (enemies.isEmpty()) {
				if (currentWaveController.hasNextWave()) {
					loadNextWave();
				} else {
					loadNextLevel();
				}
			}
		}	
	}
	
	private void updateAllEnemies(double delta) {
		int i = 0;
		while(i < enemies.size()) {
			Enemy e = enemies.get(i);
			e.update(delta);

			if (!e.isAlive()) {
				enemies.remove(e);
			} else {
				i++;
			}
		}
	}
	
	private void loadNextLevel() {
		if (levelCount == 6) {
			log().debug("GAME OVER");
		} else {
			// load next level and waves
			nextLevel();
			nextWaveController();
			loadNextWave();
		}
	}
	
	/**
	 * Restarts the game.
	 */
	public void restart() {
		
	}
	
	/**
	 * Increment the credit count.
	 * 
	 * @param credits The amount of credits to add.
	 */
	private void addCredits(int credits) {
		this.credits += credits;
		changed = true;
	}
	
	/**
	 * Decreases the credit count.
	 * 
	 * @param credits The amount of credits to remove.
	 */
	private void removeCredits(int credits) {
		this.credits -= credits;
		changed = true;
	}
	
	/**
	 * Increases the lifepoint count.
	 * 
	 * @param lifepoints The amount of lifepoints to add
	 */
	private void addLifepoints(int lifepoints) {
		this.lifepoints += lifepoints;
		changed = true;
	}
	
	/**
	 * Decrease the lifepoint count
	 * 
	 * @param lifepoints The amount of lifepoints to remove
	 */
	private void removeLifepoints(int lifepoints) {
		this.lifepoints -= lifepoints;
	}
	
	/**
	 * 
	 * @return the current wave count
	 */
	public int getLevelCount() {
		return levelCount;
	}
	
	/**
	 * 
	 * @return the current wave count
	 */
	public int getWaveCount() {
		return waveCount;
	}
	
	/**
	 * 
	 * @return the current credit count
	 */
	public int getCredits() {
		return credits;
	}
	
	/**
	 * 
	 * @return the current lifepoint count
	 */
	public int getLifepoints() {
		return lifepoints;
	}
	
	/**
	 * 
	 * @return true, if game is paused
	 */
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Pauses the game.
	 */
	public void pause() {
		paused = true;
	}
	
	/**
	 * Resumes the game.
	 */
	public void play() {
		paused = false;
	}
	
	/**
	 * 
	 * @return true, if fast forward mode is enabled
	 */
	public boolean isFastForward() {
		return fastForward;
	}
	
	/**
	 * Enables fast forward mode.
	 */
	public void fastForwardOn() {
		fastForward = true;
	}
	
	/**
	 * Disables fast forward mode.
	 */
	public void fastForwardOff() {
		fastForward = false;
	}
	
	/**
	 * 
	 * @return the current level
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}
}