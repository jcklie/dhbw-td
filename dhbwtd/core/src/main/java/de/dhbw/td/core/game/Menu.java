/*  Copyright (C) 2013 by Tobias Roeding, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Tobias Roeding - all
 */

package de.dhbw.td.core.game;

import static de.dhbw.td.core.util.ResourceContainer.resources;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.event.ICallbackFunction;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.event.IMouseObserver;


public class Menu implements IDrawable, IMouseObserver, IKeyboardObserver {

	private static final int TILE_SIZE = 64;

	private final int OFFSET_HEAD = 0;
	private final int OFFSET_FOOT = 9;

	private final int OFFSET_LEFT = 5;

	private CanvasImage canvasImage;
	private Canvas canvas;

	private ArrayList<Button> buttons;

	private GameState stateOftheWorld;

	private boolean menu = false;
	private boolean credits = false;
	private boolean help = false;
	private boolean clear = false;

	public Menu(GameState stateOftheWorld) {

		this.stateOftheWorld = stateOftheWorld;

		buttons = new ArrayList<Button>();
		createButtons();

		canvasImage = graphics().createImage(graphics().width(), graphics().height());
		canvas = canvasImage.canvas();

	}

	private void createButtons() {
		addResumeGameButton();
		addNewGameButton();
		addEndGameButton();
		addCreditsButton();
		addHelpButton();
	}

	public void setMenu(boolean value) {
		menu = value;
	}

	public void setCredits(boolean value) {
		credits = value;
	}

	public void setHelp(boolean value) {
		help = value;
	}

	public void setClear(boolean value) {
		clear = value;
	}

	private void addResumeGameButton() {
		final Button resumeGame = new Button(OFFSET_LEFT * TILE_SIZE, 104,
				TILE_SIZE * 4, TILE_SIZE, resources().MENU_RESUME, new ICallbackFunction() {

					@Override
					public void execute() {
						if (stateOftheWorld.isPaused()) {
							stateOftheWorld.play();
							clear = true;
						}
					}
				});
		TowerDefense.getMouse().addObserver(resumeGame);
		buttons.add(resumeGame);
	}

	private void addNewGameButton() {
		final Button newGame = new Button(OFFSET_LEFT * TILE_SIZE, 191,
				TILE_SIZE * 4, TILE_SIZE, resources().MENU_NEW, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked NewGame");
					}
				});
		TowerDefense.getMouse().addObserver(newGame);
		buttons.add(newGame);
	}

	private void addEndGameButton() {
		final Button endGame = new Button(OFFSET_LEFT * TILE_SIZE, 278,
				TILE_SIZE * 4, TILE_SIZE, resources().MENU_QUIT, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked EndGame");
					}
				});
		TowerDefense.getMouse().addObserver(endGame);
		buttons.add(endGame);
	}

	private void addCreditsButton() {
		final Button creditsButton = new Button(OFFSET_LEFT * TILE_SIZE, 365,
				TILE_SIZE * 4, TILE_SIZE, resources().MENU_CREDITS, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked CreditsButton");
						credits = true;
					}
				});
		TowerDefense.getMouse().addObserver(creditsButton);
		buttons.add(creditsButton);
	}

	private void addHelpButton() {
		final Button help = new Button(OFFSET_LEFT * TILE_SIZE, 452,
				TILE_SIZE * 4, TILE_SIZE, resources().MENU_HELP,
				new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked Help");
					}
				});
		TowerDefense.getMouse().addObserver(help);
		buttons.add(help);
	}

	private boolean menuChanged() {
		if (menu) {
			menu = false;
			return true;
		}
		return false;
	}

	private boolean creditsChanged() {
		if (credits) {
			credits = false;
			return true;
		}
		return false;
	}

	private boolean helpChanged() {
		if (help) {
			help = false;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Surface surf) {
		if (menuChanged()) {
			log().debug("Menu");
			surf.clear();
			canvas.clear();

			canvas.drawImage(resources().MENU_BACKGROUND, 0, 0);
			surf.drawImage(canvasImage, 0, 0);

			for (Button b : buttons) {
				b.draw(surf);
			}
		} else if (creditsChanged()) {
			surf.clear();
			canvas.clear();

			canvas.drawImage(resources().MENU_BACKGROUND, 0, 0);
			canvas.drawImage(resources().MENU_CREDITS, 0, 0);
		} else if (helpChanged()) {
			surf.clear();
			canvas.clear();

			canvas.drawImage(resources().MENU_QUIT, 0, 0);
		} else if (clear) {
			surf.clear();
			canvas.clear();
			clear = false;
		}

	}

	@Override
	public void alert(ButtonEvent e) {
		log().info(this.toString() + " " + e.toString());
	}

	@Override
	public void alert(Event e) {
		log().info(this.toString() + " " + e.toString());
	}
}