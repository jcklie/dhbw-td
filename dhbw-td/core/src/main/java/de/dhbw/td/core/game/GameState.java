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

public class GameState implements IDrawable, IUpdateable {

	private final int INITIAL_CREDITS = 1000;
	private final int INITIAL_LIFEPOINTS = 100;
	
	private static final int SPAWN_DELAY = 1000; // ms
	
	private int levelCount;
	private int waveCount;
	
	private int credits;
	private int lifepoints;
	
	private boolean paused = false;
	private boolean changed = true;
	
	private LinkedList<Enemy> allEnemies;
	private List<Enemy> enemies;
	private Time timer;
	public boolean allEnemiesDead = false;

	public GameState() {
		
		levelCount = 0;
		waveCount = 0;
		
		credits = INITIAL_CREDITS;
		lifepoints = INITIAL_LIFEPOINTS;
		
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
				addCredits(10);
				if(enemies.size() == 0){
					allEnemiesDead = true;
				}
			} else {
				enemies.get(i).update(delta);
			}
		}

	}
	
	private void addCredits(int credits) {
		this.credits += credits;
		changed = true;
	}
	
	private void removeCredits(int credits) {
		this.credits -= credits;
		changed = true;
	}
	
	private void addLifepoints(int lifepoints) {
		this.lifepoints += lifepoints;
		changed = true;
	}
	
	private void removeLifepoints(int lifepoints) {
		this.lifepoints -= lifepoints;
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
	
	public int getLifepoints() {
		return lifepoints;
	}
}
