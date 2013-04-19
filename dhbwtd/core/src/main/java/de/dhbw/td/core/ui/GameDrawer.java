package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.*;
import static de.dhbw.td.core.util.ResourceContainer.resources;

import java.util.List;

import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.enemies.HealthBar;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.level.ETileType;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.util.Point;

import playn.core.Image;
import playn.core.Surface;

public class GameDrawer implements IDrawable {

	private GameState gameState;

	public GameDrawer(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * Draws a tower onto the specified surface.
	 * 
	 * @param tower
	 *            the tower to draw
	 * @param surf
	 *            the surface to draw on
	 */
	private void drawTower(Tower tower, Surface surf) {
		// TODO draw Tower
	}

	/**
	 * Draws an enemy onto the specified surface
	 * 
	 * @param enemy
	 *            the enemy to draw
	 * @param surf
	 *            the surface to draw on
	 */
	private void drawEnemy(Enemy enemy, Surface surf) {
		Image img = null;
		switch (enemy.enemyType()) {
		case MATH:
			img = resources().IMAGE_MATH_ENEMY;
			break;
		case THEORETICAL_COMPUTER_SCIENCE:
			img = resources().IMAGE_THEOINF_ENEMY;
			break;
		case COMPUTER_ENGINEERING:
			img = resources().IMAGE_TECHINF_ENEMY;
			break;
		case ECONOMICS:
			img = resources().IMAGE_WIWI_ENEMY;
			break;
		case PROGRAMMING:
			img = resources().IMAGE_CODE_ENEMY;
			break;
		case SOCIAL:
			img = resources().IMAGE_SOCIAL_ENEMY;
			break;

		}
		Point p = enemy.position();
		surf.drawImage(img, p.x(), p.y());
		double relativeHealth = (double) enemy.curHealth()/(double)enemy.maxHealth();
		Image healthBarImage = HealthBar.getHealthStatus(relativeHealth);
		surf.drawImage(healthBarImage, enemy.position().x() + 7, enemy.position().y() + 2);
	}

	private void drawEnemies(List<Enemy> enemies, Surface surf) {
		for (Enemy e : enemies) {
			drawEnemy(e, surf);
		}
	}

	/**
	 * Draws a level onto the specified surface
	 * 
	 * @param level
	 *            the level to draw
	 * @param surf
	 *            the surface to draw on
	 */
	private void drawLevel(Level level, Surface surf) {
		ETileType map[][] = level.map();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				surf.drawImage(map[row][col].image(), (float) col * TILE_SIZE,
						(float) row * TILE_SIZE);
			}
		}
	}

	@Override
	public void draw(Surface surf) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param background
	 * @param sprites
	 */
	public void drawComponents(Surface background, Surface sprites) {
		/*
		 * log().debug("Drawing GameDrawer on ");
		 * background.drawImage(resources().IMAGE_GRID, 0, 0);
		 * background.setFillColor(Color.rgb(100, 10, 10));
		 * background.fillRect(0, 0, WIDTH, HEIGHT);
		 */
		drawLevel(gameState.level(), background);
		drawEnemies(gameState.enemies(), sprites);
	}
}
