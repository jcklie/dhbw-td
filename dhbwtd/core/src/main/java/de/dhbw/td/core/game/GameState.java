package de.dhbw.td.core.game;

import static de.dhbw.td.core.util.GameConstants.*;
import static de.dhbw.td.core.util.GameConstants.INITIAL_CREDITS;
import static de.dhbw.td.core.util.GameConstants.INITIAL_LIFEPOINTS;
import static de.dhbw.td.core.util.GameConstants.NO_OF_LEVELZ;
import static de.dhbw.td.core.util.GameConstants.ROWS;
import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import static de.dhbw.td.core.util.GameConstants.WIDTH;
import static de.dhbw.td.core.util.GameConstants.toTile;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import playn.core.Json;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.level.ETileType;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.LevelFactory;
import de.dhbw.td.core.resources.ELevelText;
import de.dhbw.td.core.resources.EWaveText;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.tower.TowerFactory;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.waves.Wave;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveControllerFactory;

public class GameState implements IUpdateable {

	private int credits;
	private int lifepoints;

	private int levelNumber;
	private int waveCount;

	private List<Enemy> enemies;
	private List<Tower> towers;

	private LevelFactory levelFactory;
	private Level currentLevel;

	private WaveControllerFactory waveFactory;
	private WaveController currentWaveController;
	private Wave currentWave;

	private TowerFactory towerFactory;
	
	private boolean[][] plat;
	
	private EGameStatus status;

	public GameState() {
		status = EGameStatus.RUNNING;

		levelFactory = new LevelFactory();
		waveFactory = new WaveControllerFactory();
		towerFactory = new TowerFactory();

		enemies = new LinkedList<Enemy>();
		towers = new LinkedList<Tower>();
		
		reset();
	}
	
	/**
	 * I want to let the enemies spawn off the screen
	 * and let them go one tile after the end, therefore
	 * I adjust the waypoint level I get from the Level object
	 * and put two offscreen waypoints at the beginning
	 * respecitve to the end
	 * @param waypointArray
	 * @return
	 */
	private Point[] beautifyWaypoints(Point[] waypointArray ) {
		int noOfWaypoints = waypointArray.length;
		Point firstWayPoint = waypointArray[0];
		Point lastWayPoint = waypointArray[ noOfWaypoints - 1];
		
		Point[] beautifiedWaypoints = new Point[noOfWaypoints + 2];
		System.arraycopy(waypointArray, 0, beautifiedWaypoints, 1, noOfWaypoints);
		
		beautifiedWaypoints[0] = new Point(-TILE_SIZE, firstWayPoint.y  );
		beautifiedWaypoints[beautifiedWaypoints.length - 1] = new Point(TILE_SIZE + WIDTH, lastWayPoint.y  );
		return beautifiedWaypoints;
	}
	
	/**
	 * Loads the next level, including the according
	 * wave controller and the first wave
	 */
	private void loadNextLevel() {
		if (levelNumber == NO_OF_LEVELZ) {
			log().debug("GAME OVER");
		} else {	
			currentLevel = getNextLevelFromFactory();
			currentWaveController = getNextWaveControllerFromFactory();
			currentWave = getNextWave();
			copyEnemiesFromWave(currentWave);
			plat = createMap(currentLevel);
			
			towers = new LinkedList<Tower>();
		}
	}
	
	private void copyEnemiesFromWave(Wave wave) {
		for(Enemy e : wave.enemies()) {
			enemies.add( new  Enemy(e));
		}
	}

	/**
	 * Loads the next level - increments levelNumber and resets waveCount
	 */
	private Level getNextLevelFromFactory() {
		levelNumber++;
		waveCount = 0;
		
		log().debug("Level " + levelNumber);
		Json.Object levelJson = ELevelText.getLevelJson(levelNumber);
		return  levelFactory.constructLevel(levelJson);
	}

	/**
	 * Loads the WaveController for the next level.
	 */
	private WaveController getNextWaveControllerFromFactory() {
		Json.Object waveControllerJson = EWaveText.getWaveControllerJson(levelNumber);
		Point[] beautifiedWayPoints = beautifyWaypoints(currentLevel.waypoints());
		return  waveFactory.constructWaveController(waveControllerJson, beautifiedWayPoints );
	}


	/**
	 * Prepares the next wave - increments waveCount
	 * @return The next wave of the wave controller
	 */
	private Wave getNextWave() {
		log().debug("Wave " + waveCount);
		waveCount++;
		return currentWaveController.nextWave();
	}

	/**
	 * Creates the plat - the plan storing which tiles on the map are
	 * buildable.
	 * 
	 * @param lvl The level to create the plat from
	 * @return A boolean array which tells which tiles are buildable (true) and which
	 *         are occupied (false)
	 */
	private boolean[][] createMap(Level lvl) {	
		boolean[][] m = new boolean[ROWS][COLS];
			  
		// We start in the second row, since the first one is for hud elements
		for(int row = 1; row < ROWS; row++) {
			// We end in the last but one row, since the last one is for hud elements
			for(int col = 0; col < COLS - 1; col++) {
				if(currentLevel.map()[row][col] == ETileType.GRID) {
					m[row][col] = true;
				}
			}
		}
		
		return m;
	}
	

	/**
	 * Builds a tower on the given coordinates. Does not adjust credit score.
	 * @param flavor The type of tower to build, e.g. coding tower
	 * @param pixelx The x coordinate in pixel
	 * @param pixely The y coordinate in pixel
	 */
	public void buildTower(EFlavor flavor, int pixelx, int pixely) {
		int tilex = toTile(pixelx);
		int tiley = toTile(pixely);
		if(tileIsBuildable(tilex, tiley)) {
			Tower t = towerFactory.constructTower(flavor, tilex, tiley);
			int cost = t.price();
			
			if( hasSufficientFunds(cost)) {
				t.setEnemies(enemies);
				towers.add(t);				
				setCellToOccupied(tilex, tiley);
				spendCredits(cost);
			}
		}
	}
	
	private boolean hasSufficientFunds(int costs) {
		return credits >= costs;
	}
	
	public void upgradeTower(int pixelx, int pixely) {
		Tower t = getTower(pixelx, pixely);

		if (t == null) {
			return;
		}

		int price = t.upgradeCost();
		if (hasSufficientFunds(price)) {
			spendCredits(price);
			t.upgrade();
		}
	}
	
	public void sellTower(int pixelx, int pixely) {
		Tower t = getTower(pixelx, pixely);
		
		if (t != null) {
			int sellPrice = (int) (t.price() * RETURN_PERCENTAGE);
			towers.remove(t);
			setCellToVacant(toTile(pixelx), toTile(pixely));
			addCredits(sellPrice);
		}
	}
	
	private Tower getTower(int pixelx, int pixely) {
		int tilex = toTile(pixelx);
		int tiley = toTile(pixely);
		
		for (Tower t : towers) {
			Point position = t.position();
			if (toTile(position.x) == tilex && toTile(position.y) == tiley) {
				return t;
			}
		}
		return null;
	}
	
	private boolean tileIsBuildable(int tilex, int tiley) {
		return plat[tiley][tilex];
	} 
	
	private void setCellToOccupied(int tilex, int tiley) {
		plat[tiley][tilex] = false;
	}
	
	private void setCellToVacant(int tilex, int tiley) {
		plat[tiley][tilex] = true;
	}

	public void reset() {
		levelNumber = 0;
		waveCount = 0;
		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;
		enemies.clear();
		towers.clear();
		
		status = EGameStatus.RUNNING;

		levelFactory = new LevelFactory();
		waveFactory = new WaveControllerFactory();
		towerFactory = new TowerFactory();

		loadNextLevel();
	}

	@Override
	public void update(double delta) {
		updateEnemies(delta);
		updateTowers(delta);
		
		if( lifepoints == 0 ) {
			status = EGameStatus.LOST;
		}
		
		if (enemies.isEmpty()) {
			if (currentWaveController.hasNextWave()) {
				currentWave = getNextWave();
				copyEnemiesFromWave(currentWave);
			} else if(hasNextLevel() ) {
				loadNextLevel();
			} else {
				status = EGameStatus.WON;
			}
		}
	}
	
	private boolean hasNextLevel() {
		return levelNumber < NO_OF_LEVELZ;
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
			
			if( e.hasReachedEnd()) {
				removeLifepoints(e.penalty());
				e.breachAcknowledged();
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
	 * Adds a given amount of credits. Val has to be greater than zero
	 * 
	 * @param val the amount of credits to add
	 */
	private void addCredits(int val) {
		assert val > 0;
		credits = credits + val;
	}

	/**
	 * Removes a given amount of credits.  Val has to be smaller than  zero
	 * 
	 * @param val the of credits to remove
	 */
	public void spendCredits(int val) {
		assert val < 0;
		credits = credits - val;
	}

	/**
	 * Removes a given amount of lifepoints. Lifepoints
	 * can only be reduced to a minimum of zero.
	 * 
	 * @param amount the amount of lifepoints to remove
	 */
	public void removeLifepoints(int amount) {
		lifepoints = Math.max(lifepoints - amount, 0);
	}

	public List<Enemy> enemies() { return enemies;	}
	public List<Tower> towers() { return towers; }	
	public Level level() { return currentLevel; }
	public int lifepoints() { return lifepoints; }
	public int credits() {	return credits;	}
	public int waveCount() { return waveCount; }
	public int levelCount() { return levelNumber;	}
	public EGameStatus status() { return status; }
}
