package de.dhbw.td.core.tower;

import playn.core.Image;
import playn.core.Surface;
import pythagoras.d.Vector;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.ui.IDrawable;
import de.dhbw.td.core.util.EFlavor;


public class Projectile implements IDrawable, IUpdateable {
	
	private final int damage;
	private final double speed;
	private final Enemy target;
	private final Image image;
	private final EFlavor flavor;
	
	private final float hwidth;
	private final float hheight;
	
	private boolean hit;
	
	private Point currentPosition;
	
	public Projectile(Point position, int damage, EFlavor flavor, double speed, Enemy target, Image image) {
		this.currentPosition = new Point(position);
		this.damage = damage;
		this.flavor = flavor;
		this.speed = speed;
		this.target = target;
		this.image = image;
		this.hwidth = image.width() / 2;
		this.hheight = image.height() / 2;
	}

	@Override
	public void update(double delta) {
		//Check if target is still alive
		if (!target.alive()) {
			hit = true;
			return;
		}
		
		//Calculates the move vector
		Vector vector = vector();
		vector = vector.scale(speed / vector.length());
		vector.set(vector.x * delta / 1000, vector.y * delta / 1000);
		
		//Check if projectile will hit the target, otherwies move the projectile
		if (enemyWasHit(vector)) {
			hit = true;
			target.takeDamage(calcDamage(damage));
		} else {
			currentPosition.translate((int) vector.x, (int) vector.y);
		}
	}
	
	private int calcDamage(int damage) {
		return flavor == target.enemyType() ? 2 * damage : damage;
	}
	
	private boolean enemyWasHit(Vector v) {
		return Math.abs(target.center().x() - currentPosition.x()) <= Math.abs(v.x) &&
				Math.abs(target.center().y() - currentPosition.y()) <= Math.abs(v.y);
	}

	/**
	 * Calculates vector between target and current position
	 * @return The calculated vector
	 */
	private Vector vector() {
		return new Vector(target.center().x() - currentPosition.x(), 
				target.center().y() - currentPosition.y());
	}

	@Override
	public void draw(Surface surf) {
		if (!hit) {
			surf.save();
			surf.translate(currentPosition.x() + hwidth, currentPosition.y() + hwidth);
			surf.rotate((float) (vector().angle() + Math.PI / 2));
			surf.drawImage(image, -hwidth, -hheight);
			surf.restore();
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
