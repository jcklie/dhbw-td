/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Martin Kiessling, Tobias Roeding - All
 */

package de.dhbw.td.core.enemies;

import java.awt.Point;
import java.util.Queue;

import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.game.IDrawable;
import de.dhbw.td.core.game.IUpdateable;
import de.dhbw.td.core.util.EDirection;

/**
 * abstract class for an enemy
 * 
 * @author Martin Kiessling, Tobias Roeding
 * @version 1.0
 * 
 */
public abstract class AEnemy implements IDrawable, IUpdateable {
	protected final EHealthBarType[] healthBarTypeArray = EHealthBarType.values();
	protected final Image[] healthBarImages = new Image[11];
	protected int maxHealth;
	protected int curHealth;
	protected boolean alive;
	protected double speed;
	protected int bounty;
	protected int penalty;
	protected EEnemyType enemyType;
	protected Queue<Point> waypoints;
	protected Point currentPosition;
	protected Image enemyImage;
	protected Image healthBarImage;
	protected EDirection currentDirection;
	protected Point currentWaypoint;
	protected Queue<Point> fixedWaypoints;

	public enum EEnemyType {
		Math, TechInf, Code, TheoInf, Wiwi, Social;
	}

	public enum EHealthBarType {
		ZERO, TEN, TWENTY, THIRTY, FOURTY, FIFTY, SIXTY, SEVENTY, EIGHTY, NINETY, HUNDRED;
	}

	@Override
	public void draw(Surface surf) {
		if (isAlive()) {
			surf.drawImage(enemyImage, currentPosition.x, currentPosition.y);
			surf.drawImage(healthBarImage, currentPosition.x - 20, currentPosition.y - 5);
		}
	}

	@Override
	public void update(double delta) {
		if (isAlive()) {
			if (currentPosition.equals(currentWaypoint)) {
				takeDamage(1);
				Point newWaypoint = waypoints.poll();
				if (newWaypoint == null) {
					die();
					for (Point p : fixedWaypoints) {
						waypoints.add((Point) p.clone());
					}
					newWaypoint = waypoints.poll();
					currentPosition.setLocation(newWaypoint);
				}
				if (currentPosition.x < newWaypoint.x) {
					currentDirection = EDirection.RIGHT;
				} else if (currentPosition.x > newWaypoint.x) {
					currentDirection = EDirection.LEFT;
				} else if (currentPosition.y < newWaypoint.y) {
					currentDirection = EDirection.DOWN;
				} else if (currentPosition.y > newWaypoint.y) {
					currentDirection = EDirection.UP;
				}
				currentWaypoint = newWaypoint;
			}
			switch (currentDirection) {
			case DOWN:
				currentPosition.translate(0, (int) (speed * delta / 1000));
				if (currentPosition.y > currentWaypoint.y) {
					currentPosition.setLocation(currentWaypoint);
				}
				break;
			case LEFT:
				currentPosition.translate((int) (-speed * delta / 1000), 0);
				if (currentPosition.x < currentWaypoint.x) {
					currentPosition.setLocation(currentWaypoint);
				}
				break;
			case RIGHT:
				currentPosition.translate((int) (speed * delta / 1000), 0);
				if (currentPosition.x > currentWaypoint.x) {
					currentPosition.setLocation(currentWaypoint);
				}
				break;
			case UP:
				currentPosition.translate(0, (int) (-speed * delta / 1000));
				if (currentPosition.y < currentWaypoint.y) {
					currentPosition.setLocation(currentWaypoint);
				}
				break;
			default:
				break;
			}
		}
	}

	public void takeDamage(int damage) {
		curHealth -= damage;
		double percent = (double) curHealth / (double) maxHealth;
		if (curHealth <= 0) {
			die();
		} else if (percent < 0.10) {
			healthBarImage = healthBarImages[0];
		} else if (percent < 0.20) {
			healthBarImage = healthBarImages[1];
		} else if (percent < 0.30) {
			healthBarImage = healthBarImages[2];
		} else if (percent < 0.40) {
			healthBarImage = healthBarImages[3];
		} else if (percent < 0.50) {
			healthBarImage = healthBarImages[4];
		} else if (percent < 0.60) {
			healthBarImage = healthBarImages[5];
		} else if (percent < 0.70) {
			healthBarImage = healthBarImages[6];
		} else if (percent < 0.80) {
			healthBarImage = healthBarImages[7];
		} else if (percent < 0.90) {
			healthBarImage = healthBarImages[8];
		} else if (percent < 1.00) {
			healthBarImage = healthBarImages[9];
		}
	}

	private void die() {
		this.alive = false;
	}

	public enum EHealthBarImage {

		ZERO("0.png"), TEN("10.png"), TWENTY("20.png"), THIRTY("30.png"), FOURTY("40.png"), FIFTY("50.png"), SIXTY(
				"60.png"), SEVENTY("70.png"), EIGHTY("80.png"), NINETY("90.png"), HUNDRED("100.png");

		public final String resourceName;

		private static final String pathToHealthBars = "images";

		public static String getPathToImage(EHealthBarType healthBarType) {
			EHealthBarImage healthBarImage = createFromHealthStatus(healthBarType);
			return String.format("%s/%s", pathToHealthBars, healthBarImage.resourceName);
		}

		private static EHealthBarImage createFromHealthStatus(EHealthBarType healthBarType) {
			switch (healthBarType) {
			case ZERO:
				return ZERO;
			case TEN:
				return TEN;
			case TWENTY:
				return TWENTY;
			case THIRTY:
				return THIRTY;
			case FOURTY:
				return FOURTY;
			case FIFTY:
				return FIFTY;
			case SIXTY:
				return SIXTY;
			case SEVENTY:
				return SEVENTY;
			case EIGHTY:
				return EIGHTY;
			case NINETY:
				return NINETY;
			case HUNDRED:
				return HUNDRED;
			default:
				throw new IllegalArgumentException("No HealthBarImage with this type:" + healthBarType);
			}
		}

		EHealthBarImage(String resourceName) {
			this.resourceName = resourceName;
		}
	}

	/**
	 * 
	 * @return current position as Point
	 */
	public Point getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * 
	 * @return current Health as integer
	 */
	public int getCurHealth() {
		return curHealth;
	}

	/**
	 * 
	 * @return speed as double
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * 
	 * @return get maximum health as integer
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * 
	 * @return boolean if enemy is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * 
	 * @return get bounty of enemy as integer
	 */
	public int getBounty() {
		return bounty;
	}

	/**
	 * 
	 * @return get penalty of enemy as integer
	 */
	public int getPenalty() {
		return penalty;
	}

	/**
	 * 
	 * @return get enemy type as EEnemyType
	 */
	public EEnemyType getEnemyType() {
		return enemyType;
	}

	/**
	 * 
	 * @return get waypoint queue as Queue<Point>
	 */
	public Queue<Point> getWaypoints() {
		return waypoints;
	}
}
