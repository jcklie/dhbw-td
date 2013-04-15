/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First basic version + refactor
 *  Benedict Holste - The more advanced version
 */

package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;
import static de.dhbw.td.core.util.GameConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import playn.core.Surface;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.tower.TowerFactory;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.ETileType;
import de.dhbw.td.core.util.Point;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;

/*
 * TODO: Close this class as good as possible, so it encapsulates all setting
 * of attributes w/o distinct setter methods
 */
public class GameState implements IUpdateable {
	
	public enum EAction {
		NONE, NEW_MATH_TOWER, NEW_CODE_TOWER, NEW_SOCIAL_TOWER,
		NEW_THEOINF_TOWER, NEW_TECHINF_TOWER, NEW_WIWI_TOWER,
		SELECTED_TOWER, MENU, PLAY_PAUSE, FAST_FORWARD;
	}
	
	private final int INITIAL_CREDITS = 25;
	private final int INITIAL_LIFEPOINTS = 100;
	
	private final double FACTOR_DELTA_FF = 1000;
	
	private int levelCount;
	private int waveCount;
	
	private int credits;
	private int lifepoints;
	
	private boolean fastForward = false;
	private boolean paused = false;
	private boolean changed = true;
	private boolean finished = false;
	private boolean newLevel = false;
	
	private List<Tower> towers;
	private List<Enemy> enemies;
	
	private WaveFactory waveFactory;
	private WaveController waveController;
	private Wave currentWave;
	
	private LevelFactory levelFactory;
	private Level currentLevel;
	
	private TowerFactory towerFactory; 
	
	private EAction lastAction;
	
	private boolean[][] map;

	/**
	 * Constructor
	 */
	public GameState() {
		
		levelFactory = new LevelFactory();
		waveFactory = new WaveFactory();
		towerFactory = new TowerFactory();
		
		towers = new ArrayList<Tower>();
		
		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;
		
		lastAction = EAction.NONE;
		
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
			String pathToImage = TowerDefense.PATH_LEVELS + "level" + levelCount + ".json";
			String levelJSON = assets().getTextSync(pathToImage);
			currentLevel = levelFactory.loadLevel(levelJSON);
			map = createMap(currentLevel);
		} catch (Exception e) {
			log().error(e.toString());
		}
		changed = true;
		newLevel = true;
	}
	
	/**
	 * Loads the WaveController for the next level.
	 */
	private void nextWaveController() {
		try {
			String pathToText = TowerDefense.PATH_WAVES + "waves" + levelCount + ".json";
			String waveJSON = assets().getTextSync(pathToText);
			waveController = waveFactory.nextWaveController(waveJSON, currentLevel.waypoints());
		} catch (Exception e) {
			log().error(e.toString());
		}
	}
	
	/**
	 * Loads the next wave in current WaveController.
	 */
	private void loadNextWave() {
		waveCount++;
		currentWave = waveController.nextWave();
		enemies = currentWave.getEnemies();
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.getCurrentPosition().translate(-i * currentLevel.tilesize(), 0);
		}
		for(int i = 0; i < towers.size(); i++) {
			Tower t = towers.get(i);
			t.setEnemies(enemies);
		}
		changed = true;
	}
	
	private boolean[][] createMap(Level lvl) {
		
		log().debug(lvl.rows() + ", " + lvl.cols());
		
		boolean[][] m = new boolean[lvl.rows()][lvl.cols()];
		for( int i = 0; i < m.length; i++ )
			   Arrays.fill( m[i], true );
		
		for(int row = 0; row < lvl.rows(); row++) {
			for(int col = 0; col < lvl.cols(); col++) {
				if(lvl.map()[row][col] != ETileType.GRID) {
					m[row][col] = false;
				}
				System.out.print(String.valueOf(m[row][col]) + " \t");
			}
			System.out.println();
		}
		
		return m;
	}
	
	public boolean checkMap(float x, float y) {
		int col = (int)Math.floor(x/currentLevel.tilesize());
		int row = (int)Math.floor(y/currentLevel.tilesize());
		log().debug("checking " + row + ", " + col);
		return map[row][col];
	}
	
	public int toTile(int pos) {
		return (int)Math.floor(pos/64);
	}
	
	/**
	 * 
	 * @param flavour
	 * @param position
	 */
	public void addTower(EFlavor flavor, Point position) {
		if(checkMap(position.getX(), position.getY())) {
			Tower t = towerFactory.getTower(flavor, new Point(toTile(position.getX())*64, toTile(position.getY())*64));
			t.setEnemies(enemies);
			towers.add(t);
			log().debug(String.format("%s, %s", toTile(position.getY()), toTile(position.getX())));
			map[toTile(position.getY())][toTile(position.getX())] = false;
		}
	}

	/**
	 * Checks whether game state has changed.
	 * 
	 * @return true, if game state has changed
	 */
	public boolean hasChanged() {
		return changed;
	}
	
	/**
	 * Used to indicate to the game state that
	 * with the change has been dealt and
	 * can now be seen as not changed
	 */
	public void changeProcessed() {
		changed = false;
	}
	
	public boolean hasNewLevel() {
		if(newLevel) {
			newLevel = false;
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
		if(newLevel) {
			surf.clear();
			log().debug("Clear TILE_LAYER");
		}	
		currentLevel.draw(surf);
		log().debug("Draw TILE_LAYER");
		newLevel = false;
	}
	
	/**
	 * Draws all placed towers.
	 * 
	 * @param surf The surface to draw on
	 */
	public void drawTowers(Surface surf) {
		surf.clear();
		for(Tower t : towers) {
			t.draw(surf);
		}
	}

	@Override
	public void update(double delta) {
		if (!finished) {

			// check for fast forward mode
			if (fastForward) {
				delta *= FACTOR_DELTA_FF;
			}
			
			updateTowers(delta);
			updateAllEnemies(delta);
			
			// check, if there are enemies left
			if (enemies.isEmpty()) {
				if (waveController.hasNextWave()) {
					loadNextWave();
				} else {
					loadNextLevel();
				}
			}
		}	
	}
	
	private void updateTowers(double delta) {
		for(int i = 0; i < towers.size(); i++) {
			Tower t = towers.get(i);
			t.update(delta);
		}
	}
	
	private void updateAllEnemies(double delta) {
		int i = 0;
		while(i < enemies.size()) {
			Enemy e = enemies.get(i);
			e.update(delta);

			if (!e.isAlive()) {
				enemies.remove(e);
				addCredits(e.getBounty());
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
	 * @return <b>true</b>, if fast forward mode is enabled
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
	
	/**
	 * Sets the last action performed.
	 * 
	 * @param action The action to be set as last action
	 */
	public void setLastAction(EAction action) {
		this.lastAction = action;
	}
	
	/**
	 * 
	 * @return the last action performed
	 */
	public EAction getLastAction() {
		return lastAction;
	}
}