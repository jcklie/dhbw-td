package de.dhbw.td.core.game;

import static playn.core.PlayN.*;
import de.dhbw.td.core.TowerDefense;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.util.Callback;

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
		
		assets().getImageSync(TowerDefense.PATH_IMAGES + "clock.png").addCallback(new Callback<Image>() {

			@Override
			public void onFailure(Throwable cause) {
				log().error(cause.getMessage());
				System.out.println("Error loading clock.png");
			}

			@Override
			public void onSuccess(Image result) {
				clockImage = result;
				System.out.println("Loaded clock.png");
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
