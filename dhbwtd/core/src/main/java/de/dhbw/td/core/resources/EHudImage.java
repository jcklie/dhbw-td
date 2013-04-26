package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_IMAGES;
import de.dhbw.td.core.util.EFlavor;
import playn.core.Image;

public enum EHudImage {

	CLOCK("clock.png"),
	PLAY("play.png"),
	PAUSE("pause.png"),
	FAST_FORWARD("fast_forward.png"),
	SETTINGS("settings.png"),
	HEART("heart.png"),
	KNOWLEDGE("knowledge.png"),
	SELL("sell.png"),
	UPGRADE("upgrade.png"),
	MATH_TOWER("math.png"),
	CODE_TOWER("code.png"),
	SOCIAL_TOWER("social.png"),
	TECHINF_TOWER("techinf.png"),
	THEOINF_TOWER("theoinf.png"),
	WIWI_TOWER("wiwi.png");
	
	public Image image;
	
	public static Image getTowerImage(EFlavor towerType) {
		switch (towerType) {
			case MATH: 	return MATH_TOWER.image;
			case THEORETICAL_COMPUTER_SCIENCE: return THEOINF_TOWER.image;
			case COMPUTER_ENGINEERING: return TECHINF_TOWER.image;
			case ECONOMICS: return WIWI_TOWER.image;
			case PROGRAMMING: return CODE_TOWER.image;
			case SOCIAL: return SOCIAL_TOWER.image;
			default: throw new RuntimeException("I should not be thrown!");
		}
	}
	
	EHudImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_IMAGES + pathToImage);
	}


}
