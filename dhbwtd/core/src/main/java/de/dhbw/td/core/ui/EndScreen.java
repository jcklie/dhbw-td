package de.dhbw.td.core.ui;

import static de.dhbw.td.core.util.ResourceContainer.resources;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import de.dhbw.td.core.util.ICallback;

import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;

public class EndScreen implements IDrawable, IUIEventListener {
	
	private final int BTN_NEWGAME_X = 100;
	private final int BTN_NEWGAME_Y = 100;
	private final int BTN_QUITGAME_X = 200;
	private final int BTN_QUITGAME_Y = 200;
	private final int BTN_MAINMENU_X = 300;
	private final int BTN_MAINMENU_Y = 300;
	
	private List<Button> buttons;
	
	public EndScreen() {
		buttons = new LinkedList<Button>();
		createButtons();
	}
	
	/**
	 * Creates the buttons
	 */
	private void createButtons() {
		/* NEW_GAME BUTTON */
		Button newGameButton = new Button.Builder(resources().IMAGE_MENU_NEW).
				x(BTN_NEWGAME_X).y(BTN_NEWGAME_Y).build();
		newGameButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked newGameButton");
				return EUserAction.NEW_GAME;
			}
		});
		buttons.add(newGameButton);
		
		/* QUIT_GAME BUTTON */
		Button quitGameButton = new Button.Builder(resources().IMAGE_MENU_QUIT).
				x(BTN_QUITGAME_X).y(BTN_QUITGAME_Y).build();
		quitGameButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked quitGameButton");
				return EUserAction.QUIT_GAME;
			}
		});
		buttons.add(quitGameButton);
		
		/* MAIN_MENU BUTTON */
		Button mainMenuButton = new Button.Builder(resources().IMAGE_MENU_RESUME).
				x(BTN_MAINMENU_X).y(BTN_MAINMENU_Y).build();
		mainMenuButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked mainMenuButton");
				return EUserAction.MAIN_MENU;
			}
		});
		buttons.add(mainMenuButton);
	}
	
	@Override
	public void draw(Surface surf) {
		surf.clear();
		surf.drawImage(resources().IMAGE_MENU_MAIN_BACKGROUND, 0, 0);
		for(Button b : buttons) {
			b.draw(surf);
		}
	}

	@Override
	public EUserAction onClick(ButtonEvent event) {
		for (Button b : buttons) {
			if (b.isHit((int) event.x(), (int) event.y())) {
				return b.callback().execute();
			}
		}
		return EUserAction.NONE;
	}

	@Override
	public EUserAction onKey(Event event) {
		return EUserAction.NONE;
	}

}
