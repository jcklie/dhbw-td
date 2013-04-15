/*  Copyright (C) 2013 by Jan-Christoph Klie, Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All
 *  
 */

package de.dhbw.td.core.game;

import static de.dhbw.td.core.util.ResourceContainer.resources;
import static playn.core.PlayN.log;

import java.util.LinkedList;
import java.util.List;

import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;
import playn.core.Surface;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.event.ICallbackFunction;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.event.IMouseObserver;

public class MainMenu implements IDrawable, IMouseObserver, IKeyboardObserver {
	
	private static final int TILE_SIZE = 64;
	
	private final int OFFSET_TOP_NEWGAME = 278;
	private final int OFFSET_TOP_HELP = 365;
	private final int OFFSET_TOP_CREDITS = 452;
	private final int OFFSET_TOP_END = 539;

	private final int OFFSET_LEFT = 5;	
	
	private GameState state;
	private List<Button> buttons;
	
	public MainMenu(GameState state) {
		this.state = state;
		buttons = new LinkedList<Button>();
		
		addNewGameButton();
		addCreditsButton();
		addHelpButton();
		addEndGameButton();
	}
	
	private void addNewGameButton() {
		final Button newGame = new Button(OFFSET_LEFT * TILE_SIZE, OFFSET_TOP_NEWGAME,
				TILE_SIZE * 4, TILE_SIZE, resources().IMAGE_MENU_NEW, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked NewGame");
					}
				});
		TowerDefense.getMouse().addObserver(newGame);
		buttons.add(newGame);
	}
	
	private void addHelpButton() {
		final Button help = new Button(OFFSET_LEFT * TILE_SIZE, OFFSET_TOP_HELP,
				TILE_SIZE * 4, TILE_SIZE, resources().IMAGE_MENU_HELP,
				new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked Help");
					}
				});
		TowerDefense.getMouse().addObserver(help);
		buttons.add(help);
	}

	private void addCreditsButton() {
		final Button creditsButton = new Button(OFFSET_LEFT * TILE_SIZE, OFFSET_TOP_CREDITS,
				TILE_SIZE * 4, TILE_SIZE, resources().IMAGE_MENU_CREDITS, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked CreditsButton");
					}
				});
		TowerDefense.getMouse().addObserver(creditsButton);
		buttons.add(creditsButton);
	}
	
	private void addEndGameButton() {
		final Button endGame = new Button(OFFSET_LEFT * TILE_SIZE, OFFSET_TOP_END,
				TILE_SIZE * 4, TILE_SIZE, resources().IMAGE_MENU_QUIT, new ICallbackFunction() {

					@Override
					public void execute() {
						log().debug("Clicked EndGame");
					}
				});
		TowerDefense.getMouse().addObserver(endGame);
		buttons.add(endGame);
	}

	@Override
	public void alert(Event e) {		
		
	}

	@Override
	public void alert(ButtonEvent e) {		
		
	}

	@Override
	public void draw(Surface surf) {
		surf.drawImage(resources().IMAGE_MENU_MAIN_BACKGROUND, 0, 0);
		for(Button b : buttons) {
			b.draw(surf);
		}		
	}

}
