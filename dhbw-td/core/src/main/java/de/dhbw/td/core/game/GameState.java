package de.dhbw.td.core.game;

import java.util.List;

import playn.core.Surface;
import de.dhbw.td.core.enemies.Enemy;

public class GameState implements IDrawable {

	private boolean changed = true;
	private List<Enemy> enemies;

	public GameState() {
	}

	public boolean hasChanged() {
		if (changed) {
			changed = false;
			return true;
		}
		return false;
	}

	public void newWave(List<Enemy> enemies) {
		this.enemies = enemies;
	}

	@Override
	public void draw(Surface surf) {
		for (Enemy e : enemies) {
			e.draw(surf);
		}
	}

}
