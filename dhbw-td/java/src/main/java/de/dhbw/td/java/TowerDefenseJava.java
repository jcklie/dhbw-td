/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;
import playn.java.JavaPlatform.Config;
import de.dhbw.td.core.TowerDefense;

public class TowerDefenseJava {

	public static void main(String[] args) {
		Config config = new Config();
	    config.width = 896;
	    config.height = 640;
	    
	    JavaPlatform platform = JavaPlatform.register(config);
		platform.assets().setPathPrefix("de/dhbw/td/resources");
		platform.setTitle("DHBW Tower Defense");		
		platform.graphics().registerFont("Miso", "fonts/miso.otf");
		PlayN.run(new TowerDefense());
	}
}
