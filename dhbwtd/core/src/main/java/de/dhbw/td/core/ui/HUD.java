/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First, ugly and very basic version
 *  Benedict Holste - First fancy version, all other things
 */

package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.ResourceContainer.resources;
import static de.dhbw.td.core.util.GameConstants.*;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
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
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.game.GameState.EAction;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.Point;


/**
 * UI-related class for visualizing the current game state and implementing
 * the user interaction trough mouse and keyboard events.
 * 
 * @author Benedict Holste <benedict@bholste.net>
 * @author Jan-Christoph Klie <jcklie@de.ibm.com>
 *
 */
public class HUD implements IMouseObserver, IKeyboardObserver {
	
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

	private CanvasImage creditsCanvasImage;
	private CanvasImage semesterCanvasImage;
	private CanvasImage lifeCanvasImage;
	
	private Canvas creditsCanvas;
	private Canvas semesterCanvas;
	private Canvas lifeCanvas;
	
	private TextFormat textFormat;
	
	private GameState gameState;
	private IngameMenu menu;
	
	private ArrayList<Button> buttons;
	
	private boolean changed;
	
	/**
	 * Constructor
	 * 
	 * @param state an object representing the game state to be visualized
	 */
	public HUD(GameState state, IngameMenu menu) {
		
		gameState = state;
		this.menu = menu;
		
		changed = true;
		
		buttons = new ArrayList<Button>();
		createButtons();
		
		creditsCanvasImage = graphics().createImage( 3 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		creditsCanvas = creditsCanvasImage.canvas();
		
		semesterCanvasImage = graphics().createImage( 5 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		semesterCanvas = semesterCanvasImage.canvas();
		
		lifeCanvasImage = graphics().createImage( 3 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		lifeCanvas = lifeCanvasImage.canvas();
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, fontSize);
		textFormat = new TextFormat().withFont(miso);
		
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
		final Button mathTower = new Button.Builder(resources().IMAGE_MATH_TOWER)
			.x(OFFSET_IMAGE_MATH*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		mathTower.setCallback(new ICallbackFunction() {
			@Override
			public void execute() {
				log().debug("Clicked MathTower");
			}
		});
		TowerDefense.getMouse().addObserver(mathTower);
		buttons.add(mathTower);
	}
	
	/**
	 * Adds the CodingTower Button
	 */
	private void addCodeButton() {
		final Button codeTower = new Button.Builder(resources().IMAGE_CODE_TOWER)
			.x(OFFSET_IMAGE_CODE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		codeTower.setCallback(new ICallbackFunction() {
			@Override
			public void execute() {
				log().debug("Clicked CodeTower");
			}
		});
		TowerDefense.getMouse().addObserver(codeTower);
		buttons.add(codeTower);
	}
	
	/**
	 * Adds the EconomicsTower Button
	 */
	private void addEconomicsButton() {		
		final Button economicsTower = new Button.Builder(resources().IMAGE_WIWI_TOWER)
			.x(OFFSET_IMAGE_WIWI*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		economicsTower.setCallback(new ICallbackFunction() {
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
		final Button tcsTower = new Button.Builder(resources().IMAGE_THEOINF_TOWER)
			.x(OFFSET_IMAGE_THEOINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		tcsTower.setCallback(new ICallbackFunction() {
			@Override
			public void execute() {
				log().debug("Clicked TheoInfTower");
			}
		});
		TowerDefense.getMouse().addObserver(tcsTower);
		buttons.add(tcsTower);
	}
	
	/**
	 * Adds the TechInfTower Button
	 */
	private void addComputerEngineeringButton() {
		final Button techinfTower = new Button.Builder(resources().IMAGE_TECHINF_TOWER)
			.x(OFFSET_IMAGE_TECHINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		techinfTower.setCallback(new ICallbackFunction() {
			@Override
			public void execute() {
				log().debug("Clicked TechInfTower");
			}
		});
		TowerDefense.getMouse().addObserver(techinfTower);
		buttons.add(techinfTower);
	}
	
	/**
	 * Adds the SocialTower Button
	 */
	private void addSocialButton() {
		final Button socialTower = new Button.Builder(resources().IMAGE_SOCIAL_TOWER)
			.x(OFFSET_IMAGE_SOCIAL*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		socialTower.setCallback(new ICallbackFunction() {
			@Override
			public void execute() {
				log().debug("Clicked SocialTower");
			}
		});
		TowerDefense.getMouse().addObserver(socialTower);
		buttons.add(socialTower);
	}
	
	/**
	 * Adds the Play/Pause Button
	 */
	private void addPlayButton() {
		final Button playPause = new Button.Builder(resources().IMAGE_PAUSE)
			.x(OFFSET_IMAGE_PLAYPAUSE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		playPause.setCallback(new ICallbackFunction() {

					@Override
					public void execute() {
						
						gameState.setLastAction(EAction.PLAY_PAUSE);
		
						if (!gameState.isPaused()) {
							gameState.pause();
							playPause.setImage(resources().IMAGE_PLAY);
						} else {
							gameState.play();
							playPause.setImage(resources().IMAGE_PAUSE);
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
		final Button fastForwardButton = new Button.Builder(resources().IMAGE_FAST_FORWARD)
			.x(OFFSET_IMAGE_FORWARD*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();

		fastForwardButton.setVisible(false);

		fastForwardButton.setCallback(new ICallbackFunction() {

			@Override
			public void execute() {
				
				gameState.setLastAction(EAction.FAST_FORWARD);
				
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
		final Button menuButton = new Button.Builder(resources().IMAGE_COG)
				.x(OFFSET_IMAGE_COG*TILE_SIZE).y(OFFSET_HEAD*TILE_SIZE).build();
		menuButton.setCallback(new ICallbackFunction() {

					@Override
					public void execute() {

						if (!gameState.isPaused()) {
							gameState.pause();
							menu.setMenu(true);
						} else {
							gameState.play();
							menu.setClear(true);
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

	public void drawIcons(Surface surf) {
		if(gameState.hasChanged() || hasChanged()) {
			// check, if world has changed
			surf.clear();
	
			// Draw HUD head
			surf.drawImage(resources().IMAGE_CLOCK, OFFSET_IMAGE_CLOCK * TILE_SIZE, OFFSET_HEAD);
			surf.drawImage(resources().IMAGE_HEART, OFFSET_IMAGE_HEART * TILE_SIZE, OFFSET_HEAD);
			surf.drawImage(resources().IMAGE_KNOWLEDGE, OFFSET_IMAGE_CREDITS * TILE_SIZE, OFFSET_HEAD);
			
			// draw the Buttons
			for (Button b : buttons) {
				b.draw(surf);
			}
		}
	}
	
	public void drawCredit(Surface surf) {	
		creditsCanvas.clear();
		drawText(creditsCanvas, gameState.getCredits(), 0, 0);
		surf.drawImage(creditsCanvasImage, OFFSET_TEXT_CREDITS*TILE_SIZE, OFFSET_TEXT_HEAD);			
	}
	
	public void drawLifes(Surface surf) {	
		lifeCanvas.clear();
		drawText(lifeCanvas, gameState.getLifepoints(),0, 0);
		surf.drawImage(lifeCanvasImage, OFFSET_TEXT_HEART*TILE_SIZE, OFFSET_TEXT_HEAD);
	}
	
	public void drawSemester(Surface surf) {			
		semesterCanvas.clear();
		String clockText = gameState.getLevelCount() + ". Semester - " + gameState.getWaveCount() + ". Woche";			
		drawText(semesterCanvas, clockText, 0, 0);
		surf.drawImage(semesterCanvasImage, OFFSET_TEXT_CLOCK*TILE_SIZE, OFFSET_TEXT_HEAD);	
	}
	
	/**
	 * Draws text on the HUD
	 * 
	 * @param o o.toString() will be drawn
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	private void drawText(Canvas canvas, Object o, int x, int y) {
		TextLayout text = graphics().layoutText(o.toString(), textFormat);
		canvas.fillText(text,x, y);
	}

	@Override
	public void alert(ButtonEvent e) {
		log().debug(e.toString());
		log().info(String.valueOf(gameState.checkMap(e.x(), e.y())));

		gameState.addTower(EFlavor.MATH, new Point((int)e.x(), (int)e.y()));
	}
	
	@Override
	public void alert(Event e) {
		
		switch (gameState.getLastAction()) {
		
		case NEW_MATH_TOWER:
			
			break;
			
		case NEW_CODE_TOWER:
			break;
			
		case NEW_SOCIAL_TOWER:
			break;
			
		case NEW_TECHINF_TOWER:
			break;
			
		case NEW_THEOINF_TOWER:
			break;
			
		case NEW_WIWI_TOWER:
			break;

		default:
			break;
		}
		
	}
}
