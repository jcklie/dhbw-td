package de.dhbw.td.core.resources;

import static de.dhbw.td.core.util.GameConstants.PATH_PROJETILES;
import playn.core.Image;

public enum ETowerImage {
	
	PROJECTILE("generic.png");
	
	public Image image;

	ETowerImage(String pathToImage) {
		image = ResourceLoader.getImage(PATH_PROJETILES + pathToImage);
	}

}
