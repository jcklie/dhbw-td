/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.java;

import java.awt.Image;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;

import playn.core.PlayN;
import playn.java.JavaPlatform;
import playn.java.JavaPlatform.Config;
import de.dhbw.td.core.TowerDefense;

public class TowerDefenseJava {

	public static void main(String[] args) throws IOException {
		Config config = new Config();
	    config.width = 896;
	    config.height = 640;	    
	    
	    JavaPlatform platform = JavaPlatform.register(config);
		platform.assets().setPathPrefix("de/dhbw/td/resources");
		platform.setTitle("DHBW Tower Defense");		
		platform.graphics().registerFont("Miso", "fonts/miso.otf");
		
		TowerDefense towerDefense = new TowerDefense();
		
		ByteBuffer[] list = new ByteBuffer[2];
		list[0] = createBuffer( getImage("/icons/DHTD_32.bmp") );
		list[1] = createBuffer( getImage("/icons/DHTD_32.bmp") );
		Display.setIcon(list);
		
		PlayN.run(towerDefense);
		
	}
	
	/**
	 * I do not handle the exception since I cannot recover from it
	 * @param pathToImage
	 * @return
	 */
	private static Image getImage(String pathToImage) {
		
		try { 
			System.out.println(pathToImage);
			InputStream stream = TowerDefenseJava.class.getResourceAsStream(pathToImage);
			return ImageIO.read(stream);
		} catch (IOException e) { throw new RuntimeException("Cannot find image: " + pathToImage); }
	}
	
	private static ByteBuffer createBuffer(Image img) {
		int height = (int) img.getHeight(null);
		int width = (int) img.getWidth(null);
		int len = width * height;
		ByteBuffer temp = ByteBuffer.allocateDirect(len << 2);

		temp.order(ByteOrder.LITTLE_ENDIAN);

		int[] pixels = new int[len];

		PixelGrabber pg = new PixelGrabber( img, 0, 0, width, height, pixels, 0, width);

		try {
			pg.grabPixels();
		} catch (InterruptedException e) {

		}

		for (int i = 0; i < len; i++) {
			int pos = i << 2;
			int texel = pixels[i];
			if (texel != 0) {
				texel |= 0xff000000;
			}
			temp.putInt(pos, texel);
		}

		return temp;
	}
}
