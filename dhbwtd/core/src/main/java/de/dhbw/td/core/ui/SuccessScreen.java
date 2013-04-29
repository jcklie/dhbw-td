package de.dhbw.td.core.ui;

import static de.dhbw.td.core.resources.EMenuImage.SUCCESS;
import static de.dhbw.td.core.resources.EMenuImage.NEW;
import static de.dhbw.td.core.resources.EMenuImage.QUIT;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import playn.core.Image;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import de.dhbw.td.core.resources.EMenuImage;
import de.dhbw.td.core.util.ICallback;

public class SuccessScreen implements IDrawable, IUIEventListener {
	
	private final int BTN_NEWGAME_X = 600;
	private final int BTN_NEWGAME_Y = 300;
	
	private final int BTN_MAINMENU_X = 600;
	private final int BTN_MAINMENU_Y = 400;
	
	private final int BTN_QUITGAME_X = 600;
	private final int BTN_QUITGAME_Y = 500;
	
	private List<Button> buttons;
	private Image image;
	
	public SuccessScreen() {
		buttons = new LinkedList<Button>();
		image = SUCCESS.image;
		createButtons();

	}
	
	/**
	 * Creates the buttons
	 */
	private void createButtons() {
		/* NEW_GAME BUTTON */
		Button newGameButton = new Button.Builder(NEW.image).
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
		Button quitGameButton = new Button.Builder(QUIT.image).
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
		Button mainMenuButton = new Button.Builder(EMenuImage.MAIN_MENU.image).
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
		surf.drawImage(image, 0, 0);
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
		switch (event.key()) {

		case B:
			return EUserAction.QUIT_GAME;			
		case N:
			return EUserAction.NEW_GAME;	
		case M:
			return EUserAction.MAIN_MENU;
		default:
			return EUserAction.NONE;
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

}
