package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;
import playn.core.Image;
import playn.core.Key;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import de.dhbw.td.core.event.ICallbackFunction;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.event.IMouseObserver;

public class Button implements IDrawable, IMouseObserver, IKeyboardObserver {
	
	private final int x;
	private final int y;	
	private final int width;
	private final int height;	
	private boolean visible;	
	private Image image;	
	private ICallbackFunction callback;	
	private Key key;
	
	public Button(int x, int y, int width, int height, String imagePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = assets().getImageSync(imagePath);
		visible = true;
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
		if(isHit((int)e.x(), (int)e.y()) && callback != null) {
			callback.execute();
		}
	}
	
	@Override
	public void alert(Event e) {
		if(key == e.key() && callback != null) {
			callback.execute();
		}
	}
	
	@Override
	public void draw(Surface surf) {
		if(visible) {
			surf.drawImage(image, x, y, width, height);
		}
	}
	
	public void setImage(String imagePath) {
		this.image = assets().getImageSync(imagePath);
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setCallback(ICallbackFunction callback) {
		this.callback = callback;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
}
