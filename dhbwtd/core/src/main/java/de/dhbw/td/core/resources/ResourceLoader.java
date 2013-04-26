/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Benedict Holste - Idea and first implementation
 *  Jan-Christoph Klie - Changed singleton to single enum + extension + refactor
 *  
 */

package de.dhbw.td.core.resources;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.json;
import playn.core.Image;
import playn.core.Json;
import playn.core.Sound;
import playn.core.json.JsonParserException;
import playn.core.util.Callback;

public class ResourceLoader {
	
	private ResourceLoader() {
		throw new RuntimeException("I shall not be initialized");
	}
	
	/**
	 * Loads and parses an JSON resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading or parsing the JSON resource
	 */
	public static Json.Object getJSON(String path) {
		
		Json.Object json;
		
		try {
			json = json().parse(assets().getTextSync(path));
		} catch (JsonParserException jpe) {
			// TODO: handle exception
			throw new RuntimeException("Could not parse JSON at " + path + ". " + jpe.getMessage());
		} 
		catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("Could not load JSON at " + path + ". " + e.getMessage());
		}
		return json;
	}
	
	/**
	 * Loads an Image resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading the image resource
	 */
	public static Image getImage(String path) {
		Image img = assets().getImageSync(path);
		img.addCallback(new Callback<Image>(){
			@Override
			public void onSuccess(Image result) {/* NOOP */}
			@Override
			public void onFailure(Throwable cause) {
				throw new RuntimeException("Could not load image resource at " + cause.getMessage());
			}
		});
		return img;
	}
	
	/**
	 * Loads a Sound resource
	 * 
	 * @param path relative file system path to the requested resource
	 * @return
	 * @throws RuntimeException if there is an error loading the sound resource
	 */
	public static Sound getSound(String path) {
		Sound s = assets().getSound(path);
		s.addCallback(new Callback<Sound>(){
			@Override
			public void onSuccess(Sound result) {/* NOOP */}
			
			@Override
			public void onFailure(Throwable cause) {
				throw new RuntimeException("Could not load sound resource at " + cause.getMessage());
			}
		});
		return s;
	}

}
