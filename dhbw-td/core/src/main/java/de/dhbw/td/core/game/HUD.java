/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First, ugly and very basic version
 *  Benedict Holste - First fancy version, all other things
 */

package de.dhbw.td.core.game;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Image;
import playn.core.Key;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.event.ICallbackFunction;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.event.IMouseObserver;

/**
 * UI-related class for visualizing the current game state and implementing
 * the user interaction trough mouse and keyboard events.
 * 
 * @author Benedict Holste <benedict@bholste.net>
 * @author Jan-Christoph Klie <jcklie@de.ibm.com>
 *
 */
public class HUD implements IDrawable, IMouseObserver, IKeyboardObserver {
	
	private static final float fontSize = 32f;
	
	private static final int TILE_SIZE = 64;
	
	private final int OFFSET_HEAD = 0;
	private final int OFFSET_FOOT = 9;
	
	private final int OFFSET_IMAGE_CLOCK 	= 0;
	private final int OFFSET_IMAGE_HEART 	= 7;
	private final int OFFSET_IMAGE_CREDITS 	= 9;
	private final int OFFSET_IMAGE_COG	 	= 13;
	private final int OFFSET_IMAGE_PLAYPAUSE = 0;
	private final int OFFSET_IMAGE_FORWARD	= 1;
	
	private final int OFFSET_IMAGE_MATH 	= 8;
	private final int OFFSET_IMAGE_CODE 	= 9;
	private final int OFFSET_IMAGE_WIWI 	= 10;
	private final int OFFSET_IMAGE_SOCIAL = 11;
	private final int OFFSET_IMAGE_TECHINF = 12;
	private final int OFFSET_IMAGE_THEOINF 	= 13;
	
	private final int OFFSET_TEXT_CREDITS = 10;
	private final int OFFSET_TEXT_CLOCK = 1;
	private final int OFFSET_TEXT_HEART = 8;
	
	private CanvasImage canvasImage;
	private Canvas canvas;
	private TextFormat textFormat;
	
	private Image clockImage;
	private Image heartImage;
	private Image creditsImage;
	
	private GameState stateOfTheWorld;
	
	private ArrayList<Button> buttons;
	
	private boolean changed;
	
	/**
	 * Constructor
	 * 
	 * @param state an object representing the game state to be visualized
	 */
	public HUD(GameState state) {
		
		stateOfTheWorld = state;
		
		changed = true;
		
		buttons = new ArrayList<Button>();
		createButtons();
		
		canvasImage = graphics().createImage( graphics().width(), graphics().height() );
		canvas = canvasImage.canvas();
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, fontSize);
		textFormat = new TextFormat().withFont(miso);
		
		// load HUD images
		clockImage = assets().getImageSync(TowerDefense.PATH_IMAGES + "clock.bmp");
		heartImage = assets().getImageSync(TowerDefense.PATH_IMAGES + "heart.bmp");
		creditsImage= assets().getImageSync(TowerDefense.PATH_IMAGES + "credits.bmp");	
	}
	
	/**
	 * Creates the HUD buttons and registers their
	 * mouse and keyboard listeners.
	 */
	private void createButtons() {
		
		final Button cog = new Button(OFFSET_IMAGE_COG*TILE_SIZE, OFFSET_HEAD, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_IMAGES + "cog.bmp", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked Settings");
					}
				});
		cog.setKey(Key.ESCAPE);
		TowerDefense.getMouse().addObserver(cog);
		TowerDefense.getKeyboard().addObserver(cog);
		buttons.add(cog);
		
		final Button mathTower = new Button(OFFSET_IMAGE_MATH*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "math.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked MathTower");
					}
				});
		TowerDefense.getMouse().addObserver(mathTower);
		buttons.add(mathTower);
		
		final Button codeTower = new Button(OFFSET_IMAGE_CODE*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "code.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked CodeTower");
					}
				});
		TowerDefense.getMouse().addObserver(codeTower);
		buttons.add(codeTower);
		
		final Button wiwiTower = new Button(OFFSET_IMAGE_WIWI*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "wiwi.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked WiwiTower");
					}
				});
		TowerDefense.getMouse().addObserver(wiwiTower);
		buttons.add(wiwiTower);
		
		final Button theoinfTower = new Button(OFFSET_IMAGE_THEOINF*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "theoinf.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked theoinfTower");
					}
				});
		TowerDefense.getMouse().addObserver(theoinfTower);
		buttons.add(theoinfTower);
		
		final Button techinfTower = new Button(OFFSET_IMAGE_TECHINF*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "techinf.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked techinfTower");
					}
				});
		TowerDefense.getMouse().addObserver(techinfTower);
		buttons.add(techinfTower);
		
		final Button socialTower = new Button(OFFSET_IMAGE_SOCIAL*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "social.png");
		socialTower.setCallback(new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked socialTower");
					}
				});
		TowerDefense.getMouse().addObserver(socialTower);
		buttons.add(socialTower);
		
		final Button playPause = new Button(OFFSET_IMAGE_PLAYPAUSE*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_IMAGES + "pause.png");
		
		playPause.setCallback(new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked playPause");
						
						if(!stateOfTheWorld.isPaused()) {
							stateOfTheWorld.pause();
							 playPause.setImage(TowerDefense.PATH_IMAGES + "play.png");
						}
						else {
							stateOfTheWorld.play();
							playPause.setImage(TowerDefense.PATH_IMAGES + "pause.png");
						}
						log().debug("Paused is " + String.valueOf(stateOfTheWorld.isPaused()));
						changed = true;
					}
				});
		playPause.setKey(Key.P);
		TowerDefense.getMouse().addObserver(playPause);
		TowerDefense.getKeyboard().addObserver(playPause);
		buttons.add(playPause);
		
		final Button fastForward = new Button(OFFSET_IMAGE_FORWARD*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_IMAGES + "fast_forward.png");
		
		fastForward.setVisible(false);
		
		fastForward.setCallback(new ICallbackFunction() {
					
					@Override
					public void execute() {
						if(stateOfTheWorld.isFastForward()) {
							fastForward.setVisible(false);
							stateOfTheWorld.fastForwardOff();
						}
						else {
							fastForward.setVisible(true);
							stateOfTheWorld.fastForwardOn();
						}
						changed = true;
						log().debug("FastForwad is " + String.valueOf(stateOfTheWorld.isFastForward()));
					}
				});
		
		fastForward.setKey(Key.F);
		TowerDefense.getMouse().addObserver(fastForward);
		TowerDefense.getKeyboard().addObserver(fastForward);
		buttons.add(fastForward);
	}
	
	/**
	 * 
	 * @return true, if the HUD has changed
	 */
	private boolean hasChanged() {
		if(changed) {
			changed = false;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Surface surf) {
		
		// check, if world has changed
		if(stateOfTheWorld.hasChanged() || hasChanged()) {
			
			log().debug("DRAW");
			
			// clear the surface
			surf.clear();
			
			canvas.clear();
			
			// draw HUD head
			canvas.drawImage(clockImage, OFFSET_IMAGE_CLOCK*TILE_SIZE, OFFSET_HEAD);
			canvas.drawImage(heartImage, OFFSET_IMAGE_HEART*TILE_SIZE, OFFSET_HEAD);
			canvas.drawImage(creditsImage, OFFSET_IMAGE_CREDITS*TILE_SIZE, OFFSET_HEAD);
			
			TextLayout clockText = graphics().layoutText(String.format("%s. Semester - %s. Woche", stateOfTheWorld.getLevelCount(),
					stateOfTheWorld.getWaveCount()), textFormat);
			canvas.fillText(clockText, OFFSET_TEXT_CLOCK*TILE_SIZE, 16);
			
			TextLayout healthText = graphics().layoutText(String.valueOf(stateOfTheWorld.getLifepoints()), textFormat);
			canvas.fillText(healthText, OFFSET_TEXT_HEART*TILE_SIZE, 16);
			
			TextLayout creditsText = graphics().layoutText(String.valueOf(stateOfTheWorld.getCredits()), textFormat);
			canvas.fillText(creditsText, OFFSET_TEXT_CREDITS*TILE_SIZE, 16);
			
			// draw the canvas onto the surface
			surf.drawImage(canvasImage, 0, 0);
			
			// draw the buttons
			for(Button b : buttons) {
				b.draw(surf);
			}
		}		
	}

	@Override
	public void alert(ButtonEvent e) {
		// TODO Auto-generated method stub
		log().info(this.toString() + " " + e.toString());
	}
	
	@Override
	public void alert(Event e) {
		// TODO Auto-generated method stub
		log().info(this.toString() + " " + e.toString());
	}
}
