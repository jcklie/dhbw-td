/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 */

package de.dhbw.td.test.mock;

import playn.core.Image;
import playn.core.Pattern;
import playn.core.gl.GL20;
import playn.core.util.Callback;

/**
 * Mocks out the image component of PlayN for easier unit testing
 * @author Jan-Christoph Klie
 *
 */
public class MockImage implements Image {

	@Override
	public float width() {
		return 0;
	}

	@Override
	public float height() {
		return 0;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void addCallback(Callback<? super Image> callback) {
		
	}

	@Override
	public Region subImage(float x, float y, float width, float height) {
		return null;
	}

	@Override
	public Pattern toPattern() {
		return null;
	}

	@Override
	public void getRgb(int startX, int startY, int width, int height, int[] rgbArray, int offset, int scanSize) {
		
	}

	@Override
	public Image transform(BitmapTransformer xform) {
		return null;
	}

	@Override
	public int ensureTexture(boolean repeatX, boolean repeatY) {
		return 0;
	}

	@Override
	public void clearTexture() {
		
	}

	@Override
	public void glTexImage2D(GL20 gl, int target, int level, int internalformat, int format, int type) {
		
	}

	@Override
	public void glTexSubImage2D(GL20 gl, int target, int level, int xOffset, int yOffset, int format, int type) {
		
	}

}
