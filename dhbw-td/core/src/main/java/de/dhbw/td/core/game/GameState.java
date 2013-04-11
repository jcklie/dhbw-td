/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First basic version
 */

package de.dhbw.td.core.game;

import java.util.LinkedList;
import java.util.List;

import playn.core.Surface;
import playn.core.util.Callback;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.util.Time;

/*
 * TODO: Close this class as good as possible, so it encapsulates all setting
 * of attributes w/o distinct setter methods
 */
public class GameState implements IDrawable, IUpdateable {

	private int levelCount;
	private int waveCount;
	
	private int credits;
	private int lifepoints;
	
	private boolean changed = true;
	private LinkedList<Enemy> allEnemies;
	private List<Enemy> enemies;
	private Time timer;
	private static final int SPAWN_DELAY = 1000; // ms
	public boolean allEnemiesDead = false;

	public GameState() {
		
		levelCount = 0;
		waveCount = 0;
		
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
		for(int i = 0; i< enemies.size(); i++){
			if(!enemies.get(i).isAlive()){
				enemies.remove(i);
				if(enemies.size() == 0){
					allEnemiesDead = true;
				}
			} else {
				enemies.get(i).update(delta);
			}
		}

	}
	
	public int getLevelCount() {
		return levelCount;
	}
	
	public int getWaveCount() {
		return waveCount;
	}
	
	public void setLevelCount(int levelCount) {
		this.levelCount = levelCount;
		changed = true;
	}
	
	public void incLevelCount() {
		levelCount++;
		changed = true;
	}
	
	public void setWaveCount(int waveCount) {
		this.waveCount = waveCount;
		changed = true;
	}
	
	public void incWaveCount() {
		waveCount++;
		changed = true;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
		changed = true;
	}
	
	public int getLifepoints() {
		return lifepoints;
	}
	
	public void setLifepoints(int lifepoints) {
		this.lifepoints = lifepoints;
	}
}
