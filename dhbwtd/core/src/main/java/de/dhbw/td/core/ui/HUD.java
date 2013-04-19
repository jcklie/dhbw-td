package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.GameConstants.COLS;
import static de.dhbw.td.core.util.GameConstants.FONTSIZE;
import static de.dhbw.td.core.util.GameConstants.TILE_SIZE;
import static de.dhbw.td.core.util.ResourceContainer.resources;
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
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.util.ICallback;

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
	private final int OFFSET_IMAGE_COG	 		= 13;
	
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
	
	private CanvasImage creditsCanvasImage;
	private CanvasImage semesterCanvasImage;
	private CanvasImage lifeCanvasImage;
	
	private Canvas creditsCanvas;
	private Canvas semesterCanvas;
	private Canvas lifeCanvas;
	
	private TextFormat textFormat;
	
	private List<Button> buttons;
	
	private GameState gameState;
	private Executor executor;
	
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
		
		Font miso = graphics().createFont("Miso", Font.Style.PLAIN, FONTSIZE);
		textFormat = new TextFormat().withFont(miso);
	}
	
	/**
	 * Creates the buttons
	 */
	private void createButtons() {
		
		addMathButton();
		addCodeButton();
		addEconomicsButton();
		addTheoreticalComputerSciencesButton();
		addComputerEngineeringButton();
		addSocialButton();
		
		//addPlayButton();
		//addFastForwardButton();
		addMenuButton();
		
		Button sellButton = new Button.Builder(resources().IMAGE_SELL).
				x(OFFSET_BTN_SELL*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		sellButton.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked Sell");
				return EUserAction.NONE;
			}
		});
		buttons.add(sellButton);
		
		Button upgradeButton = new Button.Builder(resources().IMAGE_UPGRADE).
				x(OFFSET_BTN_UPGRADE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		upgradeButton.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked Upgrade");
				return EUserAction.NONE;
			}
		});
		buttons.add(upgradeButton);
	}
	
	/**
	 * Adds the MathTower Button
	 */
	private void addMathButton() {		
		final Button mathTower = new Button.Builder(resources().IMAGE_MATH_TOWER)
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
		final Button codeTower = new Button.Builder(resources().IMAGE_CODE_TOWER)
			.x(OFFSET_BTN_CODE*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		codeTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked CodeTower");
				return EUserAction.NONE;
			}
		});
		buttons.add(codeTower);
	}
	
	/**
	 * Adds the EconomicsTower Button
	 */
	private void addEconomicsButton() {		
		final Button economicsTower = new Button.Builder(resources().IMAGE_WIWI_TOWER)
			.x(OFFSET_BTN_WIWI*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		economicsTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked WiwiTower");
				return EUserAction.NONE;
			}
		});
		buttons.add(economicsTower);
	}
	
	/**
	 * Adds the TheoInfTower Button
	 */
	private void addTheoreticalComputerSciencesButton() {
		final Button tcsTower = new Button.Builder(resources().IMAGE_THEOINF_TOWER)
			.x(OFFSET_BTN_THEOINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		tcsTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked TheoInfTower");
				return EUserAction.NONE;
			}
		});
		buttons.add(tcsTower);
	}
	
	/**
	 * Adds the TechInfTower Button
	 */
	private void addComputerEngineeringButton() {
		final Button techinfTower = new Button.Builder(resources().IMAGE_TECHINF_TOWER)
			.x(OFFSET_BTN_TECHINF*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		techinfTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked TechInfTower");
				return EUserAction.NONE;
			}
		});
		buttons.add(techinfTower);
	}
	
	/**
	 * Adds the SocialTower Button
	 */
	private void addSocialButton() {
		final Button socialTower = new Button.Builder(resources().IMAGE_SOCIAL_TOWER)
			.x(OFFSET_BTN_SOCIAL*TILE_SIZE).y(OFFSET_FOOT*TILE_SIZE).build();
		socialTower.setCallback(new ICallback<EUserAction>() {
			@Override
			public EUserAction execute() {
				log().debug("Clicked SocialTower");
				return EUserAction.NONE;
			}
		});
		buttons.add(socialTower);
	}
	
	/**
	 * Adds the Menu Button
	 */
	private void addMenuButton() {
		Button menuButton = new Button.Builder(resources().IMAGE_SETTINGS).
				x(BTN_MENU_X).y(BTN_MENU_Y).build();
		menuButton.setCallback(new ICallback<EUserAction>() {

			@Override
			public EUserAction execute() {
				log().debug("Pressed Menu Button");
				return EUserAction.INAGAME_MENU;
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
		for(Button b : buttons) {
			b.draw(surf);
		}
	}
	
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
		String clockText = gameState.levelCount() + ". Semester - " + gameState.waveCount() + ". Woche";			
		drawText(semesterCanvas, clockText, 0, 0);
		surf.drawImage(semesterCanvasImage, OFFSET_TEXT_CLOCK*TILE_SIZE, OFFSET_TEXT_HEAD);	
	}
	
	private void drawIcons(Surface surf) {
		surf.drawImage(resources().IMAGE_CLOCK, OFFSET_IMAGE_CLOCK*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
		surf.drawImage(resources().IMAGE_HEART, OFFSET_IMAGE_HEART*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
		surf.drawImage(resources().IMAGE_KNOWLEDGE, OFFSET_IMAGE_KNOWLEDGE*TILE_SIZE, OFFSET_HEAD*TILE_SIZE);
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
	
	private void handleAction(EUserAction nextAction, int x, int y) {
		executor.handleNewState(nextAction, x, y);
	}

	@Override
	public EUserAction onKey(Event event) {
		switch(event.key()) {
		case ESCAPE:
			return EUserAction.INAGAME_MENU;
		}
		return EUserAction.NONE;
	}
}
