package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import playn.core.Image;

public class HealthBar {
	
	private static final Image[] HEALTHBAR_IMAGES;	
	private static final int HEALTH_STATE_COUNT = 11;
	
	static {
		HEALTHBAR_IMAGES = new Image[HEALTH_STATE_COUNT];
		
		for (EHealthBarState e : EHealthBarState.values()) {
			String pathToImage = e.getPathToImage();
			HEALTHBAR_IMAGES[e.ordinal()] = assets().getImageSync(pathToImage);
		}		
	}			
	
	private HealthBar() {

	}
	
	public enum EHealthBarState {

		ZERO("0.png"), 
		TEN("10.png"), 
		TWENTY("20.png"), 
		THIRTY("30.png"), 
		FOURTY("40.png"), 
		FIFTY("50.png"), 
		SIXTY("60.png"), 
		SEVENTY("70.png"), 
		EIGHTY("80.png"), 
		NINETY("90.png"),
		HUNDRED("100.png");
		
		private static final String pathToHealthBars = "images/";

		public final String resourceName;
		
		EHealthBarState(String resourceName) {
			this.resourceName = resourceName;
		}		
		
		private String getPathToImage() {
			return pathToHealthBars + resourceName;
		}
	}
	
	/**
	 * 
	 * @param relativeHealth currentHealth/maxHealth, a double in [0, 1]
	 * @return
	 */
	public static Image getHealthStatus(double relativeHealth) {
		int index = (int) Math.round(relativeHealth * 10);
		return HEALTHBAR_IMAGES[index];
	}

}
