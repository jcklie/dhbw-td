package de.dhbw.td.core.tower;

import playn.core.Image;
import playn.core.Surface;
import pythagoras.d.Vector;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.Point;

public class Projectile implements IDrawable, IUpdateable {
	
	private final int damage;
	private final double speed;
	private final Enemy target;
	private final Image image;
	
	private boolean hit;
	
	private Point currentPosition;
	
	public Projectile(Point position, int damage, double speed, Enemy target, Image image) {
		this.currentPosition = new Point(position);
		this.damage = damage;
		this.speed = speed;
		this.target = target;
		this.image = image;
	}

	@Override
	public void update(double delta) {
		//Check if target is still alive
		if (!target.isAlive()) {
			hit = true;
			return;
		}
		
		//Calculates the move vector
		Vector vector = getVector();
		vector = vector.scale(speed / vector.length());
		vector.set(vector.x * delta / 1000, vector.y * delta / 1000);
		
		//Check if projectile will hit the target, otherwies move the projectile
		if (Math.abs(target.getCurrentPosition().getX() - currentPosition.getX()) <= Math.abs(vector.x) &&
				Math.abs(target.getCurrentPosition().getY() - currentPosition.getY()) <= Math.abs(vector.y)) {
			hit = true;
			target.takeDamage(damage);
		} else {
			currentPosition.translate((int) vector.x, (int) vector.y);
		}
	}

	/**
	 * Calculates vector between target and current position
	 * @return The calculated vector
	 */
	private Vector getVector() {
		return new Vector(target.getCurrentPosition().getX() - currentPosition.getX(), 
				target.getCurrentPosition().getY() - currentPosition.getY());
	}

	@Override
	public void draw(Surface surf) {
		if (!hit) {
			surf.drawImage(image, currentPosition.getX(), currentPosition.getY());
		}
	}
	
	/**
	 * Returns if projectile has hit a tower
	 * @return True if target was hit, otherwise false
	 */
	public boolean hasHit() {
		return hit;
	}

}
