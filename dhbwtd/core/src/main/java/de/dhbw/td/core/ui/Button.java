package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.HEIGHT;
import static de.dhbw.td.core.util.GameConstants.WIDTH;
import playn.core.Image;
import playn.core.Surface;
import de.dhbw.td.core.util.ICallback;

public class Button implements IDrawable {
	
	private int x;
	private int y;	
	private int width;
	private int height;
	
	private boolean visible;
	private boolean enabled;
	
	private Image image;
	
	private ICallback<EUserAction> callback;
	
	public static class Builder {
		
		// Reqired parameters
		private final Image image;
		
		// Optional parameters
		private int x;
		private int y;
		private int width;
		private int height;
		private boolean visible;
		private boolean enabled;
		private ICallback<EUserAction> callback;
		
		public Builder(Image image) {
			this.y = HEIGHT/2-(int)image.height()/2;
			this.x = WIDTH/2-(int)image.width()/2;
			this.image = image;
			this.width = (int)image.width();
			this.height = (int)image.height();
			this.visible = true;
			this.enabled = true;
			this.callback = new ICallback<EUserAction>() {
				
				@Override
				public EUserAction execute() {
					return EUserAction.NONE;
				}
			};
		}
		
		public Builder x(int val) {
			x = val; return this;
		}
		
		public Builder y(int val) {
			y = val; return this;
		}
		
		public Builder width(int val) {
			width = val; return this;
		}
		
		public Builder height(int val) {
			height = val; return this;
		}
		
		public Builder visible(boolean val) {
			visible = val; return this;
		}
		
		public Builder enabled(boolean val) {
			enabled = val; return this;
		}
		
		public Button build() {
			return new Button(this);
		}
	}
	
	private Button(Builder builder) {
		x = builder.x;
		y = builder.y;
		width = builder.width;
		height = builder.height;
		image = builder.image;
		enabled = builder.enabled;
		visible = builder.visible;
		callback = builder.callback;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isHit(int x, int y) {
		if(enabled && (this.x < x && x < this.x + width) && (this.y < y && y < this.y + height)) {
			return true;
		}
		return false;
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
	
	public void setCallback(ICallback<EUserAction> callback) {
		this.callback = callback;
	}
	
	public ICallback<EUserAction> callback() {
		return callback;
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
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void enable() {
		enabled = true;
	}
	
	public void disable() {
		enabled = false;
	}
}

