package de.dhbw.td.core.enemies;


public class Enemy extends AEnemy {
	public Enemy(int maxHealth,  double speed,
			int bounty, EEnemyType enemyType) {
		this.maxHealth = maxHealth;
		this.curHealth = maxHealth;
		this.alive = true;
		this.speed = speed;
		this.bounty = bounty;
		this.penalty = bounty * 2;
		this.enemyType = enemyType;
	}
}
