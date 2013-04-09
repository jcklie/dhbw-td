package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.util.Callback;
import de.dhbw.td.core.TowerDefense;

public class HUD implements IDrawable {
	
	private float fontSize = 24f;
	
	private CanvasImage canvasImage;
	private Canvas canvas;
	private TextFormat textFormat;
	
	private Image clockImage;
	
	private GameState stateOfTheWorld;
	
	public HUD(GameState state ) {
		stateOfTheWorld = state;
		
		canvasImage = graphics().createImage( graphics().width(), graphics().height() );
		canvas = canvasImage.canvas();
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, fontSize);
		textFormat = new TextFormat().withFont(miso);
		
		// Image loading party
		
		assets().getImage(TowerDefense.PATH_IMAGES + "clock.png").addCallback(new Callback<Image>() {

			@Override
			public void onFailure(Throwable cause) {
				log().error(cause.getMessage());				
			}

			@Override
			public void onSuccess(Image result) {
				clockImage = result;				
			}
			
		});
	}

	@Override
	public void draw(Surface surf) {
		if(stateOfTheWorld.hasChanged()) {
			surf.clear();
			canvas.drawImage(clockImage, 4, 4);
			TextLayout textLayout = graphics().layoutText("1/12", textFormat);
			canvas.fillText(textLayout, 68, 4);
			surf.drawImage(canvasImage, 0, 0);
		}		
	}

}
