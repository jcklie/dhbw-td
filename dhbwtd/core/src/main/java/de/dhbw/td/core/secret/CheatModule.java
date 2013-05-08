/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  
 */
package de.dhbw.td.core.secret;

import static playn.core.PlayN.assets;

import java.lang.reflect.Field;
import java.util.List;

import playn.core.Image;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;
import playn.core.Sound;
import de.dhbw.td.core.game.EGameStatus;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.ui.Button;
import de.dhbw.td.core.ui.HUD;

/**
 * Secret cheats are secret
 */
public class CheatModule implements Keyboard.Listener{
		
	private static final int MAX_BUFFER_SIZE = 30;
	
	private StringBuffer buffer;
	private GameState state;
	private HUD hud;
	
	private Image king;

	public CheatModule(GameState state, HUD hud) {		
		this.state = state;
		this.hud = hud;
		
		buffer = new StringBuffer(MAX_BUFFER_SIZE);
		
		king = assets().getImageSync("tower/king.png");
		
		
	}
	
	private boolean in(String s) {
		return buffer.toString().contains(s);
	}
	
	private void setAttribute(Object target, String fieldName, Object value) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void handleCheatz() throws Exception {
		if( in("BALLERBURGFTW")) {			
			handleBallerBurg();
		} else if (in("GRUSEL")) {
			playSound("sound/intro_xD");
		} else if (in("SOUNDDEMO")) {
			playSound("sound/fancy_riff3");
		} else if( in("ALLYOURCAREBELONGTOUS")){
			handleAllYourCareBelongToUs();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void handleBallerBurg() throws Exception {
		playSound("sound/win_sequenze");
		
		Field buttons = hud.getClass().getDeclaredField("buttons");	
		buttons.setAccessible(true);
		List<Button> buttonList = (List<Button>) buttons.get(hud);
		
		Button b = buttonList.get(1);
		Field image = b.getClass().getDeclaredField("image");
		image.setAccessible(true);
		image.set(b, king);
	}
	
	private void handleAllYourCareBelongToUs() {
		setAttribute(state, "status", EGameStatus.WON);
		clear();
	}
	
	private void playSound( String name ) {
		Sound mySound = assets().getSound(name);
		mySound.play();
		mySound.setLooping(true);
		clear();
	}

	@Override
	public void onKeyDown(Event e) {
		if(e.key().toString().length() > 1) {
			return;
		}
		
		buffer.append(e.key());
		
		if( buffer.length() > MAX_BUFFER_SIZE) {
			buffer.deleteCharAt(0);
		}
		
		try {
			handleCheatz();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	private void clear() {
		buffer.delete(0, buffer.length());
	}

	public void onKeyTyped(TypedEvent event) {}
	public void onKeyUp(Event event) {}

}
