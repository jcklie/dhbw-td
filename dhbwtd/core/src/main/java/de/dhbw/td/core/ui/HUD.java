/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - First idea, adding canvas support + refactor
 *  Martin Kiessling - Info text 
 *  Benedict Holste -  All the rest which was bigger part
 */

package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.COLS;
import static de.dhbw.td.core.util.GameConstants.FONTSIZE;
import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import static de.dhbw.td.core.resources.EHudImage.*;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import playn.core.TextFormat;
import playn.core.TextLayout;
import de.dhbw.td.core.fsm.Executor;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.util.ICallback;

/**
 * The HUD is resposible for drawing the icons, the tower
 * menu and the info text
 * UI code is very ugly
 *
 */
public class HUD implements IDrawable, IUIEventListener {
	
	private final int BTN_MENU_X = (COLS-1)*TILE_SIZE;
	private final int BTN_MENU_Y = 0;
	
	private final int OFFSET_HEAD = 0;
	private final int OFFSET_FOOT = 9;
	
	/*
	 * OFFSET values for HEAD images
	 */
	private final int OFFSET_IMAGE_CLOCK 		= 0;
	private final int OFFSET_IMAGE_HEART 		= 7;
	private final int OFFSET_IMAGE_KNOWLEDGE 	= 9;
	
	
	/*
	 * OFFSET values for FOOT images
	 */
	private final int OFFSET_BTN_MATH 	= 8;
	private final int OFFSET_BTN_CODE 	= 9;
	private final int OFFSET_BTN_WIWI 	= 10;
	private final int OFFSET_BTN_SOCIAL = 11;
	private final int OFFSET_BTN_TECHINF = 12;
	private final int OFFSET_BTN_THEOINF 	= 13;
	
	private final int OFFSET_BTN_SELL = 5;
	private final int OFFSET_BTN_UPGRADE = 6;
	
	private final int OFFSET_TEXT_CREDITS = 10;
	private final int OFFSET_TEXT_CLOCK = 1;
	private final int OFFSET_TEXT_HEART = 8;
	private final int OFFSET_TEXT_HEAD = 16;
	private final int OFFSET_TEXT_FOOT = 592;
	private final int OFFSET_TEXT_INFORMATION = 16;
	
	private CanvasImage creditsCanvasImage;
	private CanvasImage semesterCanvasImage;
	private CanvasImage lifeCanvasImage;
	private CanvasImage informationCanvasImage;
	
	private Canvas creditsCanvas;
	private Canvas semesterCanvas;
	private Canvas lifeCanvas;
	private Canvas informationCanvas;
	
	private TextFormat textFormat;
	private TextFormat smallerTextFormat;
	
	private List<Button> buttons;
	
	private GameState gameState;
	private Executor executor;
	
	/**
	 * Creates a HUD with a given game state to display
	 * @param gameState
	 */
	public HUD(GameState gameState) {
		
		this.gameState = gameState;
		executor = new Executor(gameState);
		
		buttons = new LinkedList<Button>();
		createButtons();
		
		creditsCanvasImage = graphics().createImage( 3 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		creditsCanvas = creditsCanvasImage.canvas();
		
		semesterCanvasImage = graphics().createImage( 5 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		semesterCanvas = semesterCanvasImage.canvas();
		
		lifeCanvasImage = graphics().createImage( 3 * TILE_SIZE, TILE_SIZE );	// TODO: Confirm size
		lifeCanvas = lifeCanvasImage.canvas();
		
		informationCanvasImage = graphics().createImage( 5 * TILE_SIZE, TILE_SIZE ); // TODO: Confirm size
		informationCanvas = informationCanvasImage.canvas();		
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, FONTSIZE);
		textFormat = new TextFormat().withFont(miso);
		
		miso = graphics().createFont("Miso", Font.Style.PLAIN, 20);
		smallerTextFormat = new TextFormat().withFont(miso);
	}
	
	/**
	 * Creates the buttons
	 */
	private void createButtons() {
		addSellButton();
		addUpgradeButton();
		
		addMathButton();
		addCodeButton();
		addEconomicsButton();
		addTheoreticalComputerSciencesButton();
		addComputerEngineeringButton();
		addSocialButton();
		addMenuButton();
	}
	
	/**
	 * Adds the sell Button
	 */
	private void addSellButton() {
		Button sellButton = new Button.Builder(SELL.image).
				x(OFFSET_BTN_SELL*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		sellButton.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked Sell");
				return EUserAction.SELL;
			}
		});
		buttons.add(sellButton);
	}
	
	/**
	 * Adds the upgrade Button
	 */
	private void addUpgradeButton() {
		Button upgradeButton = new Button.Builder(UPGRADE.image).
				x(OFFSET_BTN_UPGRADE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		upgradeButton.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked Upgrade");
				return EUserAction.UPGRADE;
			}
		});
		buttons.add(upgradeButton);
	}
	
	/**
	 * Adds the MathTower Button
	 */
	private void addMathButton() {		
		final Button mathTower = new Button.Builder(MATH_TOWER.image)
			.x(OFFSET_BTN_MATH*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		mathTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked MathTower");
				return EUserAction.NEW_MATH_TOWER;
			}
		});
		buttons.add(mathTower);
	}
	
	/**
	 * Adds the CodingTower Button
	 */
	private void addCodeButton() {
		final Button codeTower = new Button.Builder(CODE_TOWER.image)
			.x(OFFSET_BTN_CODE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		codeTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked CodeTower");
				return EUserAction.NEW_CODE_TOWER;
			}
		});
		buttons.add(codeTower);
	}
	
	/**
	 * Adds the EconomicsTower Button
	 */
	private void addEconomicsButton() {		
		final Button economicsTower = new Button.Builder(WIWI_TOWER.image)
			.x(OFFSET_BTN_WIWI*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		economicsTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked WiwiTower");
				return EUserAction.NEW_ECO_TOWER;
			}
		});
		buttons.add(economicsTower);
	}
	
	/**
	 * Adds the TheoInfTower Button
	 */
	private void addTheoreticalComputerSciencesButton() {
		final Button tcsTower = new Button.Builder(THEOINF_TOWER.image)
			.x(OFFSET_BTN_THEOINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		tcsTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked TheoInfTower");
				return EUserAction.NEW_THEO_INF_TOWER;
			}
		});
		buttons.add(tcsTower);
	}
	
	/**
	 * Adds the TechInfTower Button
	 */
	private void addComputerEngineeringButton() {
		final Button techinfTower = new Button.Builder(TECHINF_TOWER.image)
			.x(OFFSET_BTN_TECHINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		techinfTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked TechInfTower");
				return EUserAction.NEW_TECH_INF_TOWER;
			}
		});
		buttons.add(techinfTower);
	}
	
	/**
	 * Adds the SocialTower Button
	 */
	private void addSocialButton() {
		final Button socialTower = new Button.Builder(SOCIAL_TOWER.image)
			.x(OFFSET_BTN_SOCIAL*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		socialTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked SocialTower");
				return EUserAction.NEW_SOCIAL_TOWER;
			}
		});
		buttons.add(socialTower);
	}
	
	/**
	 * Adds the Menu Button
	 */
	private void addMenuButton() {
		Button menuButton = new Button.Builder(SETTINGS.image).
				x(BTN_MENU_X).y(BTN_MENU_Y).build();
		menuButton.setCallback(new ICallback<EUserAction>() {

			@Override
			public EUserAction execute() {
				log().debug("Pressed Menu Button");
				return EUserAction.INGAME_MENU;
			}
		});
		buttons.add(menuButton);
	}
	
	@Override
	public void draw(Surface surf) {
		drawIcons(surf);
		drawSemester(surf);
		drawLifes(surf);
		drawCredit(surf);
		drawInformation(surf);
		for(Button b : buttons) {
			b.draw(surf);
		}
	}
	
	/*
	 * Helper methods to draw on the canvases
	 */
	
	private void drawCredit(Surface surf) {	
		creditsCanvas.clear();
		drawText(creditsCanvas, gameState.credits(), 0, 0);
		surf.drawImage(creditsCanvasImage, OFFSET_TEXT_CREDITS*TILE_SIZE, OFFSET_TEXT_HEAD);			
	}
	
	private void drawLifes(Surface surf) {	
		lifeCanvas.clear();
		drawText(lifeCanvas, gameState.lifepoints(),0, 0);
		surf.drawImage(lifeCanvasImage, OFFSET_TEXT_HEART*TILE_SIZE, OFFSET_TEXT_HEAD);
	}
	
	private void drawSemester(Surface surf) {			
		semesterCanvas.clear();
		
		String clockText = gameState.levelCount() + ". Semester - ";
		if (gameState.isEndboss()) {
			clockText += "Klausuren";
		} else {
			clockText += gameState.waveCount() + ". Woche";			
		}
		
		drawText(semesterCanvas, clockText, 0, 0);
		surf.drawImage(semesterCanvasImage, OFFSET_TEXT_CLOCK*TILE_SIZE, OFFSET_TEXT_HEAD);	
	}
	
	private void drawInformation(Surface surf){
		informationCanvas.clear();
		String text = gameState.information();
		if( text.contains("\n")) {
			drawText(informationCanvas, smallerTextFormat, gameState.information(), 0, 0);
		} else {
			drawText(informationCanvas, gameState.information(), 0, 0);
		}
		surf.drawImage(informationCanvasImage, OFFSET_TEXT_INFORMATION, OFFSET_TEXT_FOOT);
	}
	
	private void drawIcons(Surface surf) {
		surf.drawImage(CLOCK.image, OFFSET_IMAGE_CLOCK*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
		surf.drawImage(HEART.image, OFFSET_IMAGE_HEART*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
		surf.drawImage(KNOWLEDGE.image, OFFSET_IMAGE_KNOWLEDGE*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
	}
	
	/**
	 * Draws toString of object on the given canvas
	 * 
	 * @param o o.toString() will be drawn
	 * @param x the x-coordinate of the upper left corner of the textbox to be drawn
	 * @param y the y-coordinate of the upper left corner of the textbox to be drawn
	 */
	private void drawText(Canvas canvas, Object o, int x, int y) {
		drawText(canvas, textFormat, o, x, y);
	}
	
	/**
	 * Draws toString of object on the given canvas
	 * 
	 * @param o o.toString() will be drawn
	 * @param format the TextFormat to write the text with
	 * @param x the x-coordinate of the upper left corner of the textbox to be drawn
	 * @param y the y-coordinate of the upper left corner of the textbox to be drawn
	 */
	private void drawText(Canvas canvas, TextFormat format, Object o, int x, int y) {
		TextLayout text = graphics().layoutText(o.toString(), format);
		canvas.fillText(text,x, y);
	}

	@Override
	public EUserAction onClick(ButtonEvent event) {
		EUserAction nextAction = EUserAction.NONE;
		int x = (int) event.x();
		int y = (int) event.y();
		
		for (Button b : buttons) {
			if (b.isHit(x, y)) {
				nextAction = b.callback().execute();
				break;
			}
		}
		
		log().debug("HUD recieved: " + nextAction);
		handleAction(nextAction, x, y);
		return nextAction;
	}
	
	/**
	 * If an event is dispatched to the mouse to the HUD,
	 * the executor is told to handle it with the given
	 * coordinates on the frame.
	 * 
	 * @param nextAction The action which was dispatched to the HUD
	 * @param x The xcoordinate of the click
	 * @param y The ycoordinate of the click
	 */
	private void handleAction(EUserAction nextAction, int x, int y) {
		executor.handleNewState(nextAction, x, y);
	}

	@Override
	public EUserAction onKey(Event event) {
		switch(event.key()) {
		case ESCAPE: return EUserAction.INGAME_MENU;
		case K1:
			executor.handleNewState(EUserAction.NEW_MATH_TOWER);
			return EUserAction.NEW_MATH_TOWER;
		case K2:
			executor.handleNewState(EUserAction.NEW_CODE_TOWER);
			return EUserAction.NEW_CODE_TOWER;
		case K3:
			executor.handleNewState(EUserAction.NEW_ECO_TOWER);
			return EUserAction.NEW_ECO_TOWER;
		case K4:
			executor.handleNewState(EUserAction.NEW_SOCIAL_TOWER);
			return EUserAction.NEW_SOCIAL_TOWER;
		case K5:
			executor.handleNewState(EUserAction.NEW_TECH_INF_TOWER);
			return EUserAction.NEW_TECH_INF_TOWER;
		case K6:
			executor.handleNewState(EUserAction.NEW_THEO_INF_TOWER);
			return EUserAction.NEW_THEO_INF_TOWER;
		case Q:
			executor.handleNewState(EUserAction.SELL);
			return EUserAction.SELL;
		case W:
			executor.handleNewState(EUserAction.UPGRADE);
			return EUserAction.UPGRADE;
		default:
			return EUserAction.NONE;
		}
		
	}
}
