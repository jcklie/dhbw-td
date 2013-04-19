package de.dhbw.td.core.game;

import static de.dhbw.td.core.util.GameConstants.INITIAL_CREDITS;
import static de.dhbw.td.core.util.GameConstants.INITIAL_LIFEPOINTS;
import static de.dhbw.td.core.util.GameConstants.NO_OF_LEVELZ;
import static de.dhbw.td.core.util.GameConstants.PATH_LEVELS;
import static de.dhbw.td.core.util.GameConstants.PATH_WAVES;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.tower.TowerFactory;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;

public class GameState implements IUpdateable {

	private int credits;
	private int lifepoints;

	private int levelCount;
	private int waveCount;

	private List<Enemy> enemies;
	private List<Tower> towers;

	private LevelFactory levelFactory;
	private Level currentLevel;

	private WaveFactory waveFactory;
	private WaveController currentWaveController;
	private Wave currentWave;

	private TowerFactory towerFactory;

	public GameState() {

		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;

		levelCount = 0;
		waveCount = 0;

		levelFactory = new LevelFactory();
		waveFactory = new WaveFactory();
		towerFactory = new TowerFactory();

		enemies = new LinkedList<Enemy>();
		towers = new LinkedList<Tower>();

		loadNextLevel();
	}

	/**
	 * Loads the next level, including the according
	 * wave controller and the first wave
	 */
	private void loadNextLevel() {
		if (levelCount == NO_OF_LEVELZ) {
			log().debug("GAME OVER");
		} else {
			nextLevel();
			nextWaveController();
			loadNextWave();
		}
	}

	/**
	 * Loads the next level
	 */
	private void nextLevel() {
		levelCount++;
		waveCount = 0;
		try {
			String pathToImage = PATH_LEVELS + "level" + levelCount + ".json";
			log().debug(pathToImage);
			String levelJSON = assets().getTextSync(pathToImage);
			currentLevel = levelFactory.loadLevel(levelJSON);
			// map = createMap(currentLevel);
		} catch (Exception e) {
			log().error(e.toString());
		}
	}

	/**
	 * Loads the WaveController for the next level.
	 */
	private void nextWaveController() {
		log().debug("loading next wavecontroller");
		try {
			String pathToText = PATH_WAVES + "waves" + levelCount + ".json";
			String waveJSON = assets().getTextSync(pathToText);
			currentWaveController = waveFactory.nextWaveController(waveJSON,
					currentLevel.waypoints());
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
		enemies = currentWave.enemies();
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.position().translate(-i * 2 * currentLevel.tilesize(),
					0);
		}
		for (int i = 0; i < towers.size(); i++) {
			Tower t = towers.get(i);
			t.setEnemies(enemies);
		}
	}

	/**
	 * Builds a new tower
	 */
	public void buildTower(EFlavor flavor, int x, int y) {
		Tower tower = towerFactory.constructTower(flavor, x, y);
		towers.add(tower);
	}

	/**
	 * Destroys a tower
	 */
	public boolean destroyTower() {
		return false;
	}

	/**
	 * Adds a given amount of credits
	 * 
	 * @param val
	 *            the amount of credits to add
	 */
	private void addCredits(int val) {
		credits = credits + val;
	}

	/**
	 * Removes a given amount of credits
	 * 
	 * @param val
	 *            the 	 of credits to remove
	 */
	private void removeCredits(int val) {
		credits = credits - val;
	}

	/**
	 * Removes a given amount of lifepoints
	 * 
	 * @param val
	 *            the amount of lifepoints to remove
	 */
	private void removeLifepoints(int val) {
		lifepoints = lifepoints - val;
	}

	public void reset() {
		levelCount = 0;
		waveCount = 0;
		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;
		enemies.clear();
		towers.clear();
		loadNextLevel();
	}

	@Override
	public void update(double delta) {
		updateEnemies(delta);
		if (enemies.isEmpty()) {
			if (currentWaveController.hasNextWave()) {
				loadNextWave();
			} else {
				loadNextLevel();
			}
		}
	}

	/**
	 * Updates the list of enemies
	 * 
	 * @param delta
	 */
	private void updateEnemies(double delta) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update(delta);
			if (!e.alive()) {
				enemies.remove(e);
				addCredits(e.bounty());
			}
		}
	}

	/**
	 * Updates all currently built towers
	 * 
	 * @param delta
	 */
	private void updateTowers(double delta) {
		for (int i = 0; i < towers.size(); i++) {
			Tower t = towers.get(i);
			t.update(delta);
		}
	}

	/**
	 * 
	 * @return list of currently alive enemies
	 */
	public List<Enemy> enemies() {
		return enemies;
	}

	/**
	 * 
	 * @return list of currently built towers
	 */
	public List<Tower> towers() {
		return towers;
	}
	
	public Level level() {
		return currentLevel;
	}
	
	/**
	 * 
	 * @return the current amount of lifepoints
	 */
	public int lifepoints() {
		return lifepoints;
	}
	
	/**
	 * 
	 * @return the current amount of credits
	 */
	public int credits() {
		return credits;
	}

	/**
	 * 
	 * @return the current wave count
	 */
	public int waveCount() {
		return waveCount;
	}

	/**
	 * 
	 * @return the current level count
	 */
	public int levelCount() {
		return levelCount;
	}
}
