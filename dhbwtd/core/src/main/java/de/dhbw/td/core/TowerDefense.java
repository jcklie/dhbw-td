package de.dhbw.td.core;

import static de.dhbw.td.core.util.GameConstants.FACTOR_DELTA_FF;
import static de.dhbw.td.core.util.GameConstants.HEIGHT;
import static de.dhbw.td.core.util.GameConstants.WIDTH;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.log;
import static playn.core.PlayN.mouse;
import playn.core.Events;
import playn.core.Game;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import playn.core.SurfaceLayer;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.ui.EUIState;
import de.dhbw.td.core.ui.EUserAction;
import de.dhbw.td.core.ui.EndScreen;
import de.dhbw.td.core.ui.GameDrawer;
import de.dhbw.td.core.ui.HUD;
import de.dhbw.td.core.ui.IUIEventListener;
import de.dhbw.td.core.ui.IngameMenu;
import de.dhbw.td.core.ui.MainMenu;

public class TowerDefense implements Game, Keyboard.Listener, Mouse.Listener {

	/*
	 * States
	 */
	private EUIState currentUIState;
	private GameState gameState;	

	/*
	 * UI COMPONENTS
	 */
	private MainMenu mainMenu;
	private HUD hud;
	private IngameMenu ingameMenu;
	private EndScreen endScreen;
	private GameDrawer gameDrawer;

	/*
	 * LAYERS
	 */
	private SurfaceLayer BACKGROUND_LAYER;
	private SurfaceLayer SPRITE_LAYER;

	private boolean paused;
	private boolean fastForward;

	@Override
	public void init() {

		paused = true;
		fastForward = false;

		// create layers
		BACKGROUND_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(BACKGROUND_LAYER);

		SPRITE_LAYER = graphics().createSurfaceLayer(WIDTH, HEIGHT);
		graphics().rootLayer().add(SPRITE_LAYER);
		
		gameState = new GameState();

		// initialize UI Components
		mainMenu = new MainMenu();
		hud = new HUD(gameState);
		ingameMenu = new IngameMenu();
		endScreen = new EndScreen();

		gameDrawer = new GameDrawer(gameState);

		// set Mouse and Keyboard Listener
		mouse().setListener(this);
		keyboard().setListener(this);

		// set UIState
		currentUIState = EUIState.MAIN_MENU;
	}

	@Override
	public void update(float delta) {
		if (!paused) {
			if (fastForward) {
				gameState.update(delta * FACTOR_DELTA_FF);
			} else {
				gameState.update(delta);
			}
		}
	}

	@Override
	public int updateRate() {
		return 24;
	}

	@Override
	public void paint(float alpha) {
		switch (currentUIState) {
		case MAIN_MENU:
			mainMenu.draw(BACKGROUND_LAYER.surface());
			break;

		case INGAME_MENU:
			//SPRITE_LAYER.surface().clear();
			ingameMenu.draw(SPRITE_LAYER.surface());
			break;

		case GAME:
			clearLayers();
			gameDrawer.drawComponents(BACKGROUND_LAYER.surface(), SPRITE_LAYER.surface());
			hud.draw(BACKGROUND_LAYER.surface());
			break;

		case END_SCREEN:
			endScreen.draw(BACKGROUND_LAYER.surface());
			break;
		}
	}

	/**
	 * Dispatches a Mouse or Keyboard event to the current active UI component
	 * and receives the action response. The response will be the new state of this
	 * class.
	 * 
	 * @param event The {@code Keyboard.Event} or {@code Mouse.ButtonEvent} event to dispatch
	 */
	private void dispatchEvent(Events.Input event) {

		EUserAction action = EUserAction.NONE;

		switch (currentUIState) {
	
			case MAIN_MENU:
				action = dispatchToComponent(mainMenu, event);
				break;
	
			case INGAME_MENU:
				action = dispatchToComponent(ingameMenu, event);
				break;
	
			case GAME:
				action = dispatchToComponent(hud, event);
				break;
	
			case END_SCREEN:
				action = dispatchToComponent(endScreen, event);
				break;
		}

		log().debug("returned Action " + action);

		handleAction(action);
	}

	/**
	 * Handles the specified global event.
	 * 
	 * @param action the action to be handled
	 */
	private void handleAction(EUserAction action) {
		switch (action) {
			case RESUME_GAME:
				currentUIState = EUIState.GAME;
				paused = false;
				break;
			case NEW_GAME:
				currentUIState = EUIState.GAME;
				gameState.reset();
				fastForward = false;
				paused = false;
				break;
			case QUIT_GAME:
				System.exit(0);
				break;
			case MAIN_MENU:
				currentUIState = EUIState.MAIN_MENU;
				break;
			case INAGAME_MENU:
				currentUIState = EUIState.INGAME_MENU;
				paused = true;
				break;
			case NONE:
				break;
		default:
			; // The event was no UI change event			
		}
	}

	/**
	 * Dispatches an event to a specified IUIListener.
	 * 
	 * @param comp the IUIListener component to dispatch to
	 * @param event the event to dispatch
	 */
	private EUserAction dispatchToComponent(IUIEventListener comp, 	Events.Input event) {
		/*
		 * Use instanceof instead of another switch-case to get rid of code
		 * duplication. DRY!
		 */
		if (event instanceof ButtonEvent) {
			return comp.onClick((ButtonEvent) event);
		} else if (event instanceof Event) {
			return comp.onKey((Event) event);
		}
		return EUserAction.NONE;
	}

	private void clearLayers() {
		BACKGROUND_LAYER.surface().clear();
		SPRITE_LAYER.surface().clear();
	}

	@Override
	public void onMouseDown(ButtonEvent event) {
		dispatchEvent(event);
	}

	@Override
	public void onMouseUp(ButtonEvent event) {/* NOOP! */
	}

	@Override
	public void onMouseMove(MotionEvent event) {/* NOOP! */
	}

	@Override
	public void onMouseWheelScroll(WheelEvent event) {/* NOOP! */
	}

	@Override
	/**
	 * We check if the event is an global event, i. e. will be handled
	 * on the Tower Defense or an UI event which is then dispatched
	 * to the component currently active.
	 */
	public void onKeyDown(Event event) {
		if (currentUIState == EUIState.GAME) {
			switch (event.key()) {
			case P:
				if(!paused) {
					paused = true;
				} else {
					paused = false;
				}
				return;

			case F:
				if (!fastForward) {
					fastForward = true;
				} else {
					fastForward = false;
				}
				return;
			default:
				; // We are not interested in other key strokes
			
			}
		}
		dispatchEvent(event);
	}

	@Override
	public void onKeyTyped(TypedEvent event) {/* NOOP! */
	}

	@Override
	public void onKeyUp(Event event) {/* NOOP! */
	}
}
