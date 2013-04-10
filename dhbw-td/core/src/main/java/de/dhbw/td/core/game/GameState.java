package de.dhbw.td.core.game;

import java.util.LinkedList;
import java.util.List;

import playn.core.Surface;
import playn.core.util.Callback;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.util.Time;

public class GameState implements IDrawable, IUpdateable {

	private boolean changed = true;
	private LinkedList<Enemy> allEnemies;
	private List<Enemy> enemies;
	private Time timer;
	private static final int SPAWN_DELAY = 1000; // ms

	public GameState() {
		timer = new Time(SPAWN_DELAY, new Callback<String>() {

			@Override
			public void onSuccess(String result) {
				if (!allEnemies.isEmpty()) {
					enemies.add(allEnemies.poll());
				}
				timer.start();
			}

			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub

			}
		});
	}

	public boolean hasChanged() {
		if (changed) {
			changed = false;
			return true;
		}
		return false;
	}

	public void newWave(final List<Enemy> newEnemies) {
		allEnemies = (LinkedList<Enemy>) newEnemies;
		enemies = new LinkedList<Enemy>();
		timer.start();
	}

	@Override
	public void draw(Surface surf) {
		surf.clear();
		for (Enemy e : enemies) {
			e.draw(surf);
		}
	}

	@Override
	public void update(double delta) {
		timer.update(delta);
		for (Enemy e : enemies) {
			e.update(delta);
		}

	}
}
