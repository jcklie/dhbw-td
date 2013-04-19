/*  Copyright (C) 2013 by Martin Kiessling, Tobias Roeding Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.backup;

import static playn.core.PlayN.assets;
import playn.core.Image;

public class HealthBar {
	
	private static final Image[] HEALTHBAR_IMAGES;	
	private static final int HEALTH_STATE_COUNT = 10;
	
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
		int index = (int) Math.min(relativeHealth * 10, HEALTH_STATE_COUNT - 1);
		return HEALTHBAR_IMAGES[index];
	}

}
