package de.dhbw.td.core.secret;

import static playn.core.PlayN.assets;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import playn.core.Image;
import playn.core.Keyboard.Event;
import playn.core.Sound;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.game.Button;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.game.HUD;
import de.dhbw.td.core.util.ResourceContainer;


public class CheatModule implements IKeyboardObserver {
		
	private static final int MAX_BUFFER_SIZE = 25;
	
	private StringBuffer buffer;
	private GameState state;
	private HUD hud;
	
	private Image king;

	public CheatModule(GameState state, HUD hud) {		
		this.state = state;
		this.hud = hud;
		
		buffer = new StringBuffer(MAX_BUFFER_SIZE);
		TowerDefense.getKeyboard().addObserver(this);
		
		king = assets().getImageSync("tower/king.png");
	}

	@Override
	public void alert(Event e) {		
		if(e.key().toString().length() > 1) {
			return;
		}
		
		buffer.append(e.key());
		
		if( buffer.length() > MAX_BUFFER_SIZE) {
			buffer.deleteCharAt(0);
		}
		
		System.out.println(buffer);
		
		try {
			handleCheatz();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private boolean in(String s) {
		return buffer.toString().contains(s);
	}
	
	private void handleCheatz() throws Exception {
		if( in("BALLERBURGFTW")) {			
			handleBallerBurg();
		} else if (in("GRUSEL")) {
			playSound("sound/intro_xD");
		} else if (in("SOUNDDEMO")) {
			playSound("sound/fancy_riff3");
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
	
	private void playSound( String name ) {
		Sound mySound = assets().getSound(name);
		mySound.play();
		mySound.setLooping(true);
		buffer.delete(0, buffer.length());
	}

}