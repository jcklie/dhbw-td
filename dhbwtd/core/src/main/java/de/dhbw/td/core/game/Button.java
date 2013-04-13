package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
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
	private boolean enabled;
	
	private Image image;	
	private ICallbackFunction callback;	
	private Key key;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param imagePath
	 */
	public Button(int x, int y, int width, int height, Image image) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
		visible = true;
		enabled = true;
	}
	
	public Button(int x, int y, int width, int height, Image image, ICallbackFunction callback) {
		this(x, y, width, height, image);
		this.callback = callback;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isHit(int x, int y) {
		if((this.x < x && x < this.x + width) && (this.y < y && y < this.y + height)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void alert(ButtonEvent e) {
		if(enabled && isHit((int)e.x(), (int)e.y()) && callback != null) {
			callback.execute();
		}
	}
	
	@Override
	public void alert(Event e) {
		if(enabled && key == e.key() && callback != null) {
			callback.execute();
		}
	}
	
	@Override
	public void draw(Surface surf) {
		if(visible) {
			surf.drawImage(image, x, y, width, height);
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
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
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
}
