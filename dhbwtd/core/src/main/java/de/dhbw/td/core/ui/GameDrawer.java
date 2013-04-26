package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;

import java.util.List;

import playn.core.Image;
import playn.core.Surface;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.enemies.HealthBar;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.level.ETileType;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.resources.EEnemyImage;
import de.dhbw.td.core.resources.EHudImage;
import de.dhbw.td.core.resources.ETowerImage;
import de.dhbw.td.core.tower.Projectile;
import de.dhbw.td.core.tower.Tower;

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
		Image img = ETowerImage.getTowerImage(tower.flavor(), tower.getLevel() + 1);

		surf.drawImage(img, tower.x(), tower.y());
		
		for (Projectile projectile : tower.projectiles()) {
			projectile.draw(surf);
		}
	}
	


	/**
	 * Draws an enemy onto the specified surface
	 * 
	 * @param enemy the enemy to draw
	 * @param surf the surface to draw on
	 */
	private void drawEnemy(Enemy enemy, Surface surf) {
		Image img = EEnemyImage.getEnemyImage(enemy.enemyType());
		Point p = enemy.position();
		surf.drawImage(img, p.x(), p.y());
		double relativeHealth = (double) enemy.curHealth()/(double)enemy.maxHealth();
		Image healthBarImage = HealthBar.getHealthStatus(relativeHealth);
		surf.drawImage(healthBarImage, enemy.position().x() + 7, enemy.position().y() + 2);
	}

	private void drawEnemies(List<Enemy> enemies, Surface surf) {
		for (Enemy e : enemies) {
			if(e.alive())
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
