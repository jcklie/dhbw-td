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
	private final int OFFSET_TEXT_HEAD = 16;
	
	private CanvasImage canvasImage;
	private Canvas canvas;
	private TextFormat textFormat;
	
	private Image clockImage;
	private Image heartImage;
	private Image creditsImage;
	
	private GameState gameState;
	
	private ArrayList<Button> buttons;
	
	private boolean changed;
	
	private EMouseSelection mouseSelection;

	private enum EMouseSelection {
		NONE, MATH_TOWER, CODE_TOWER, SOCIAL_TOWER, THEOINF_TOWER, TECHINF_TOWER, WIWI_TOWER;
	}
	
	/**
	 * Constructor
	 * 
	 * @param state an object representing the game state to be visualized
	 */
	public HUD(GameState state) {
		
		gameState = state;
		
		changed = true;
		
		mouseSelection = EMouseSelection.NONE;
		
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
		
		TowerDefense.getMouse().addObserver(this);
		TowerDefense.getKeyboard().addObserver(this);
	}
	
	/**
	 * Creates the HUD buttons and registers their
	 * mouse and keyboard listeners.
	 */
	private void createButtons() {
		addMathButton();
		addCodeButton();
		addEconomicsButton();
		addTheoreticalComputerSciencesButton();
		addComputerEngineeringButton();
		addSocialButton();
		
		addPlayButton();
		addFastForwardButton();
		addMenuButton();
	}
	
	/**
	 * Adds the MathTower Button
	 */
	private void addMathButton() {		
		final Button mathTower = new Button(OFFSET_IMAGE_MATH*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "math.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("SELECT MATH_TOWER");
						mouseSelection = EMouseSelection.MATH_TOWER;
					}
				});
		TowerDefense.getMouse().addObserver(mathTower);
		buttons.add(mathTower);
	}
	
	/**
	 * Adds the CodingTower Button
	 */
	private void addCodeButton() {
		final Button codeTower = new Button(OFFSET_IMAGE_CODE*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "code.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("SELECT CODE_TOWER");
						mouseSelection = EMouseSelection.CODE_TOWER;
					}
				});
		TowerDefense.getMouse().addObserver(codeTower);
		buttons.add(codeTower);
	}
	
	/**
	 * Adds the EconomicsTower Button
	 */
	private void addEconomicsButton() {		
		final Button economicsTower = new Button(OFFSET_IMAGE_WIWI*TILE_SIZE, OFFSET_FOOT*TILE_SIZE, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_TOWERS + "wiwi.png", new ICallbackFunction() {
					
					@Override
					public void execute() {
						log().debug("Clicked WiwiTower");
					}
				});
		TowerDefense.getMouse().addObserver(economicsTower);
		buttons.add(economicsTower);
	}
	
	/**
	 * Adds the TheoInfTower Button
	 */
	private void addTheoreticalComputerSciencesButton() {
		final Button tcsTower = new Button(OFFSET_IMAGE_THEOINF * TILE_SIZE, OFFSET_FOOT * TILE_SIZE, TILE_SIZE,
				TILE_SIZE, TowerDefense.PATH_TOWERS + "theoinf.png", new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked theoinfTower");
					}
				});
		TowerDefense.getMouse().addObserver(tcsTower);
		buttons.add(tcsTower);
	}
	
	/**
	 * Adds the TechInfTower Button
	 */
	private void addComputerEngineeringButton() {
		final Button techinfTower = new Button(OFFSET_IMAGE_TECHINF * TILE_SIZE, OFFSET_FOOT * TILE_SIZE, TILE_SIZE,
				TILE_SIZE, TowerDefense.PATH_TOWERS + "techinf.png", new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked techinfTower");
					}
				});
		TowerDefense.getMouse().addObserver(techinfTower);
		buttons.add(techinfTower);
	}
	
	/**
	 * Adds the SocialTower Button
	 */
	private void addSocialButton() {
		final Button socialTower = new Button(OFFSET_IMAGE_SOCIAL * TILE_SIZE, OFFSET_FOOT * TILE_SIZE, TILE_SIZE,
				TILE_SIZE, TowerDefense.PATH_TOWERS + "social.png");
		socialTower.setCallback(new ICallbackFunction() {

			@Override
			public void execute() {
				log().debug("Clicked socialTower");
			}
		});
		TowerDefense.getMouse().addObserver(socialTower);
		buttons.add(socialTower);
	}
	
	/**
	 * Adds the Play/Pause Button
	 */
	private void addPlayButton() {
		final Button playPause = new Button(OFFSET_IMAGE_PLAYPAUSE * TILE_SIZE, OFFSET_FOOT * TILE_SIZE, TILE_SIZE,
				TILE_SIZE, TowerDefense.PATH_IMAGES + "pause.png");

		playPause.setCallback(new ICallbackFunction() {

			@Override
			public void execute() {

				if (!gameState.isPaused()) {
					gameState.pause();
					playPause.setImage(TowerDefense.PATH_IMAGES + "play.png");
				} else {
					gameState.play();
					playPause.setImage(TowerDefense.PATH_IMAGES + "pause.png");
				}

				changed = true;
			}
		});
		playPause.setKey(Key.P);
		TowerDefense.getMouse().addObserver(playPause);
		TowerDefense.getKeyboard().addObserver(playPause);
		buttons.add(playPause);
	}
	
	/**
	 * Adds the Fast Forward Button
	 */
	private void addFastForwardButton() {
		final Button fastForwardButton = new Button(OFFSET_IMAGE_FORWARD * TILE_SIZE, OFFSET_FOOT * TILE_SIZE,
				TILE_SIZE, TILE_SIZE, TowerDefense.PATH_IMAGES + "fast_forward.png");

		fastForwardButton.setVisible(false);

		fastForwardButton.setCallback(new ICallbackFunction() {

			@Override
			public void execute() {
				if (gameState.isFastForward()) {
					fastForwardButton.setVisible(false);
					gameState.fastForwardOff();
				} else {
					fastForwardButton.setVisible(true);
					gameState.fastForwardOn();
				}
				changed = true;
				log().debug("FastForwad is " + String.valueOf(gameState.isFastForward()));
			}
		});

		fastForwardButton.setKey(Key.F);
		TowerDefense.getMouse().addObserver(fastForwardButton);
		TowerDefense.getKeyboard().addObserver(fastForwardButton);
		buttons.add(fastForwardButton);
	}
	
	/**
	 * Adds the Menu Button
	 */
	private void addMenuButton() {
		final Button menuButton = new Button(OFFSET_IMAGE_COG * TILE_SIZE, OFFSET_HEAD, TILE_SIZE, TILE_SIZE,
				TowerDefense.PATH_IMAGES + "cog.bmp", new ICallbackFunction() {

					@Override
					public void execute() {
						if(mouseSelection == EMouseSelection.NONE) {
							log().debug("CLICKED MENU");
						}
					}
				});
		menuButton.setKey(Key.ESCAPE);
		TowerDefense.getMouse().addObserver(menuButton);
		TowerDefense.getKeyboard().addObserver(menuButton);
		buttons.add(menuButton);
	}
	
	/**
	 * 
	 * @return true, if the HUD has changed
	 */
	private boolean hasChanged() {
		if (changed) {
			changed = false;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Surface surf) {
		
		// check, if world has changed
		if(gameState.hasChanged() || hasChanged()) {

			surf.clear();			
			canvas.clear();
			
			// Draw HUD head
			canvas.drawImage(clockImage, OFFSET_IMAGE_CLOCK*TILE_SIZE, OFFSET_HEAD);
			canvas.drawImage(heartImage, OFFSET_IMAGE_HEART*TILE_SIZE, OFFSET_HEAD);
			canvas.drawImage(creditsImage, OFFSET_IMAGE_CREDITS*TILE_SIZE, OFFSET_HEAD);
			
			// Draw text labels
			
			String clockText = gameState.getLevelCount() + ". Semester - " +  gameState.getWaveCount()+ ". Woche";
			
			drawText(clockText, OFFSET_TEXT_CLOCK*TILE_SIZE, OFFSET_TEXT_HEAD);
			drawText(gameState.getLifepoints(), OFFSET_TEXT_HEART*TILE_SIZE, OFFSET_TEXT_HEAD);
			drawText(gameState.getCredits(), OFFSET_TEXT_CREDITS*TILE_SIZE, OFFSET_TEXT_HEAD);
			
			// Draw changes to the surface
			
			surf.drawImage(canvasImage, 0, 0);
			
			// draw the Buttons
			for(Button b : buttons) {
				b.draw(surf);
			}
		}		
	}
	
	/**
	 * Draws text on the HUD
	 * 
	 * @param o o.toString() will be drawn
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	private void drawText(Object o, int x, int y) {
		TextLayout clockText = graphics().layoutText(o.toString(), textFormat);
		canvas.fillText(clockText,x, y);
	}

	@Override
	public void alert(ButtonEvent e) {
		if(mouseSelection != EMouseSelection.NONE) {
			log().info("PLACED TOWER" + mouseSelection.toString());
		}
		else {
			log().info("SELECT PLACED TOWER");
		}
	}
	
	@Override
	public void alert(Event e) {
		if(mouseSelection != EMouseSelection.NONE && e.key() == Key.ESCAPE) {
			log().info("HUD DESELECT");
			mouseSelection = EMouseSelection.NONE;
		}
	}
}
