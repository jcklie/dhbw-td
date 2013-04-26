package de.dhbw.td.core.ui;

import static de.dhbw.td.core.resources.EMenuImage.*;
import static de.dhbw.td.core.util.GameConstants.*;
import static playn.core.PlayN.log;
import java.util.ArrayList;
import java.util.List;

import playn.core.Keyboard.Event;
import playn.core.Surface;
import playn.core.Mouse.ButtonEvent;
import de.dhbw.td.core.ui.Button;
import de.dhbw.td.core.util.ICallback;

public class IngameMenu implements IDrawable, IUIEventListener {
	
	/*
	 * BUTTON COORDINATES
	 */
	private final int BTN_RESUME_Y = 104;
	private final int BTN_NEWGAME_Y = 191;
	private final int BTN_HELP_Y = 278;
	private final int BTN_CREDITS_Y = 365;
	private final int BTN_QUITGAME_Y = 452;
	
	private List<Button> buttons;
	
	private boolean showHelp;
	private boolean showCredits;
	private boolean drawn;
	
	/**
	 * Constructor for initialization
	 */
	public IngameMenu() {
		buttons = new ArrayList<Button>();
		createButtons();
		showHelp = false;
		showCredits = false;
		drawn = false;
	}
	
	/**
	 * Creates the menu's buttons.
	 */
	private void createButtons() {

		/* RESUME BUTTON */
		Button resumeButton = new Button.Builder(RESUME.image).
				y(BTN_RESUME_Y).build();
		resumeButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked resumeButton");
				return EUserAction.RESUME_GAME;
			}
		});
		buttons.add(resumeButton);

		/* NEW_GAME BUTTON */
		Button newGameButton = new Button.Builder(NEW.image).
				y(BTN_NEWGAME_Y).build();
		newGameButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked newGameButton");
				return EUserAction.NEW_GAME;
			}
		});
		buttons.add(newGameButton);
		
		/* END_GAME BUTTON */
		Button endGameButton = new Button.Builder(QUIT.image).
				y(BTN_QUITGAME_Y).build();
		endGameButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked EndGameButton");
				return EUserAction.QUIT_GAME;
			}
		});
		buttons.add(endGameButton);
		
		/* CREDITS BUTTON */
		Button creditsButton = new Button.Builder(BTN_CREDITS.image).
				y(BTN_CREDITS_Y).build();
		creditsButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked CreditsButton");
				showCredits(true);
				return EUserAction.NONE;
			}
		});
		buttons.add(creditsButton);
		
		/* HELP BUTTON */
		Button helpButton = new Button.Builder(BTN_HELP.image).
				y(BTN_HELP_Y).build();
		helpButton.setCallback(new ICallback<EUserAction>() {
			
			@Override
			public EUserAction execute() {
				log().debug("Clicked HelpButton");
				showHelp(true);
				return EUserAction.NONE;
			}
		});
		buttons.add(helpButton);
	}
	
	/**
	 * Enables or disables all the menu's buttons
	 * @param enabled <b>true</b> for enabling, false for disabling
	 */
	private void setButtonsEnabled(boolean enabled) {
		for (Button b : buttons) {
			b.setEnabled(enabled);
		}
	}

	/**
	 * Shows or hides the help screen.
	 * @param show
	 */
	private void showHelp(boolean show) {
		setButtonsEnabled(!show);
		showHelp = show;
	}

	/**
	 * Shows or hides the credits screen.
	 * @param show
	 */
	private void showCredits(boolean show) {
		setButtonsEnabled(!show);
		showCredits = show;
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
		case ESCAPE:
			if (showHelp) {
				showHelp(false);
			} else if (showCredits) {
				showCredits(false);
			}
			else {	
				return EUserAction.RESUME_GAME;
			}
			break;
		default:
			// We do not care about other key strokes
		}
		return EUserAction.NONE;
	}
	
	@Override
	public void draw(Surface surf) {
		surf.clear();
		//log().debug("Drawing IngameMenu on " + surf.toString());
		if (showHelp) {
			surf.drawImage(HELPSCREEN.image, 0, 0);
		} else if (showCredits) {
			surf.drawImage(CREDITS.image, 0, 0);
		} else {
			surf.drawImage(INGAME_BACKGROUND.image, 0, 0);
			for (Button b : buttons) {
				b.draw(surf);
			}
		}
	}
}
