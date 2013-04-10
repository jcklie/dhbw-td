package de.dhbw.td.core.game;

import static playn.core.PlayN.*;
import de.dhbw.td.core.TowerDefense;
import playn.core.Assets;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.Pointer;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.Pointer.Event;
import playn.core.util.Callback;

/**
 * 
 * @author Benedict Holste <benedict@bholste.net>
 *
 */
public class HUD implements IDrawable {
	
	private float fontSize = 32f;
	
	private int TILE_SIZE = 64;
	
	private int OFFSET_HEAD = 0;
	private int OFFSET_FOOT = 9;
	
	private int OFFSET_IMAGE_CLOCK 	= 0;
	private int OFFSET_IMAGE_HEART 	= 7;
	private int OFFSET_IMAGE_CREDITS 	= 10;
	private int OFFSET_IMAGE_COG	 	= 13;
	
	private int OFFSET_IMAGE_MATH 	= 8;
	private int OFFSET_IMAGE_CODE 	= 9;
	private int OFFSET_IMAGE_WIWI 	= 10;
	private int OFFSET_IMAGE_SOCIAL = 11;
	private int OFFSET_IMAGE_TECHINF = 12;
	private int OFFSET_IMAGE_THEOINF 	= 13;
	
	private int OFFSET_TEXT_CREDITS = 11;
	private int OFFSET_TEXT_CLOCK = 1;
	private int OFFSET_TEXT_HEART = 8;
	
	
	private CanvasImage canvasImage;
	private Canvas canvas;
	private TextFormat textFormat;
	
	private Image clockImage;
	private Image heartImage;
	private Image cogImage;
	private Image creditsImage;
	
	private Image mathTowerImage;
	private Image codeTowerImage;
	private Image socialTowerImage;
	private Image wiwiTowerImage;
	private Image techinfTowerImage;
	private Image theoinfTowerImage;
	
	private GameState stateOfTheWorld;
	
	/**
	 * 
	 * @param state
	 */
	public HUD(GameState state ) {
		
		stateOfTheWorld = state;
		
		canvasImage = graphics().createImage( graphics().width(), graphics().height() );
		canvas = canvasImage.canvas();
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, fontSize);
		textFormat = new TextFormat().withFont(miso);
		
		// Image loading party
		clockImage = assets().getImageSync(TowerDefense.PATH_IMAGES + "clock.bmp");
		heartImage = assets().getImageSync(TowerDefense.PATH_IMAGES + "heart.bmp");
		cogImage = assets().getImageSync(TowerDefense.PATH_IMAGES + "cog.bmp");
		creditsImage= assets().getImageSync(TowerDefense.PATH_IMAGES + "credits.bmp");
		
		// load tower images
		mathTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "math.png");
		codeTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "code.png");
		wiwiTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "wiwi.png");
		socialTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "social.png");
		techinfTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "techinf.png");
		theoinfTowerImage = assets().getImageSync(TowerDefense.PATH_TOWERS + "theoinf.png");
		
	}

	@Override
	public void draw(Surface surf) {
		
		// check, if world has changed
		if(stateOfTheWorld.hasChanged()) {
			
			// clear the surface
			surf.clear();
			
			// draw HUD head
			canvas.drawImage(clockImage, OFFSET_IMAGE_CLOCK*TILE_SIZE, 0);
			canvas.drawImage(heartImage, OFFSET_IMAGE_HEART*TILE_SIZE, 0);
			canvas.drawImage(creditsImage, OFFSET_IMAGE_CREDITS*TILE_SIZE, 0);
			canvas.drawImage(cogImage, OFFSET_IMAGE_COG*TILE_SIZE, 0);
			
			TextLayout clockText = graphics().layoutText("1. Semester", textFormat);
			canvas.fillText(clockText, OFFSET_TEXT_CLOCK*TILE_SIZE, 16);
			
			TextLayout healthText = graphics().layoutText("100", textFormat);
			canvas.fillText(healthText, OFFSET_TEXT_HEART*TILE_SIZE, 16);
			
			// draw HUD foot
			canvas.drawImage(mathTowerImage, OFFSET_IMAGE_MATH*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			canvas.drawImage(codeTowerImage, OFFSET_IMAGE_CODE*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			canvas.drawImage(wiwiTowerImage, OFFSET_IMAGE_WIWI*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			canvas.drawImage(socialTowerImage, OFFSET_IMAGE_SOCIAL*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			canvas.drawImage(techinfTowerImage, OFFSET_IMAGE_TECHINF*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			canvas.drawImage(theoinfTowerImage, OFFSET_IMAGE_THEOINF*TILE_SIZE, OFFSET_FOOT*TILE_SIZE);
			
			// draw the canvas onto the surface
			surf.drawImage(canvasImage, 0, 0);
		}		
	}

}
