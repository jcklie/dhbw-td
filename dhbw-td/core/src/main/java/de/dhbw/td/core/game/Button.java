package de.dhbw.td.core.game;

import de.dhbw.td.core.event.ICallbackFunction;
import de.dhbw.td.core.event.IMouseObserver;
import playn.core.Image;
import playn.core.Surface;
import playn.core.Mouse.ButtonEvent;
import static playn.core.PlayN.log;
import static playn.core.PlayN.assets;

public class Button implements IDrawable, IMouseObserver{
	
	int x;
	int y;
	
	int width;
	int height;
	
	Image image;
	
	ICallbackFunction callback;
	
	public Button(int x, int y, int width, int height, String imagePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = assets().getImageSync(imagePath);
	}
	
	public Button(int x, int y, int width, int height, String imagePath, ICallbackFunction callback) {
		this(x, y, width, height, imagePath);
		this.callback = callback;
	}
	
	private boolean isHit(int x, int y) {
		if((this.x < x && x < this.x + width) && (this.y < y && y < y + height)) {
			log().debug(this.toString() + " HIT");
			return true;
		}
		return false;
	}
	
	@Override
	public void alert(ButtonEvent e) {
		if(isHit((int)e.x(), (int)e.y())) {
			callback.execute();
		}
	}
	
	@Override
	public void draw(Surface surf) {
		surf.drawImage(image, x, y, width, height);
	}
}
