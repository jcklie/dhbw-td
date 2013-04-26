package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_IMAGES;
import playn.core.Image;

public enum EHealthBarImage {	
	
	IMAGE_HEALTHBAR_0("0.png"),
	IMAGE_HEALTHBAR_10("10.png"),
	IMAGE_HEALTHBAR_20("20.png"),
	IMAGE_HEALTHBAR_30("30.png"),
	IMAGE_HEALTHBAR_40("40.png"),
	IMAGE_HEALTHBAR_50("50.png"),
	IMAGE_HEALTHBAR_60("60.png"),
	IMAGE_HEALTHBAR_70("70.png"),
	IMAGE_HEALTHBAR_80("80.png"),
	IMAGE_HEALTHBAR_90("90.png"),
	IMAGE_HEALTHBAR_100("100.png");
	
	public Image image;
	
	EHealthBarImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_IMAGES + pathToImage);
	}

}
