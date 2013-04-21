package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import static de.dhbw.td.core.util.ResourceContainer.resources;

import java.util.List;

import playn.core.Image;
import playn.core.Surface;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.enemies.HealthBar;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.level.ETileType;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.tower.Projectile;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.util.EFlavor;

public class GameDrawer implements IDrawable {

	private GameState gameState;

	public GameDrawer(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * Draws a tower onto the specified surface.
	 * 
	 * @param tower the tower to draw
	 * @param surf the surface to draw on
	 */
	private void drawTower(Tower tower, Surface surf) {
		Image img = getTowerImage(tower.flavor());

		surf.drawImage(img, tower.x(), tower.y());
		
		for (Projectile projectile : tower.projectiles()) {
			projectile.draw(surf);
		}
	}
	
	private Image getTowerImage(EFlavor towerType) {
		switch (towerType) {
		case MATH: 	return resources().IMAGE_MATH_TOWER;
		case THEORETICAL_COMPUTER_SCIENCE: return resources().IMAGE_THEOINF_ENEMY;
		case COMPUTER_ENGINEERING: return resources().IMAGE_TECHINF_ENEMY;
		case ECONOMICS: return resources().IMAGE_WIWI_ENEMY;
		case PROGRAMMING: return resources().IMAGE_CODE_ENEMY;
		case SOCIAL: return resources().IMAGE_SOCIAL_ENEMY;
		default: throw new RuntimeException("I should not be thrown!");
	}
	}

	/**
	 * Draws an enemy onto the specified surface
	 * 
	 * @param enemy the enemy to draw
	 * @param surf the surface to draw on
	 */
	private void drawEnemy(Enemy enemy, Surface surf) {
		Image img = resources().getEnemyImage(enemy.enemyType());
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
	
	private void drawTowers(List<Tower> towers, Surface surf) {
		for(Tower t : towers) {
			drawTower(t, surf);
		}
	}

	/**
	 * Draws a level onto the specified surface
	 * 
	 * @param level the level to draw
	 * @param surf the surface to draw on
	 */
	private void drawLevel(Level level, Surface surf) {
		ETileType map[][] = level.map();
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length; col++) {
				surf.drawImage(map[row][col].image(), col * TILE_SIZE, row * TILE_SIZE);
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
		drawTowers(gameState.towers(), sprites);
	}
}
