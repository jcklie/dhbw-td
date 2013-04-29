package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_ENEMIES;
import playn.core.Image;
import de.dhbw.td.core.util.EFlavor;

public enum EEnemyImage {
	
	MATH_ENEMY("math.png"),
	CODE_ENEMY("code.png"),
	SOCIAL_ENEMY("social.png"),
	TECHINF_ENEMY("techinf.png"),
	THEOINF_ENEMY("theoinf.png"),
	WIWI_ENEMY("wiwi.png"),
	ENDBOSS("endboss.png");
	
	private Image image;
	
	public static Image getEnemyImage(EFlavor enemyType) {
		switch (enemyType) {
			case MATH: 	return MATH_ENEMY.image;
			case THEORETICAL_COMPUTER_SCIENCE: return THEOINF_ENEMY.image;
			case COMPUTER_ENGINEERING: return TECHINF_ENEMY.image;
			case ECONOMICS: return WIWI_ENEMY.image;
			case PROGRAMMING: return CODE_ENEMY.image;
			case SOCIAL: return SOCIAL_ENEMY.image;
			case ENDBOSS: return ENDBOSS.image;
			default: throw new RuntimeException("I should not be thrown!");
		}
	}
	
	EEnemyImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_ENEMIES + pathToImage);
	}


}
