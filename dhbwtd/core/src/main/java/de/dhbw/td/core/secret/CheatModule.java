package de.dhbw.td.core.secret;

import static playn.core.PlayN.assets;
import playn.core.Keyboard.Event;
import playn.core.Sound;
import de.dhbw.td.core.TowerDefense;
import de.dhbw.td.core.event.IKeyboardObserver;
import de.dhbw.td.core.game.GameState;


public class CheatModule implements IKeyboardObserver {
		
	private static final int MAX_BUFFER_SIZE = 25;
	
	private StringBuffer buffer;
	private GameState state;

	public CheatModule(GameState state) {		
		this.state = state;
		
		buffer = new StringBuffer(MAX_BUFFER_SIZE);
		TowerDefense.getKeyboard().addObserver(this);
	}

	@Override
	public void alert(Event e) {
		System.out.println(buffer);
		
		if(e.key().toString().length() > 1) {
			return;
		}
		
		buffer.append(e.key());
		
		if( buffer.length() > MAX_BUFFER_SIZE) {
			buffer.deleteCharAt(0);
		}		
		
		handleCheatz();
	}
	
	private boolean in(String s) {
		return buffer.toString().contains(s);
	}
	
	private void handleCheatz() {
		if( in("BALLERBURGFTW")) {			
			Sound mySound = assets().getSound("sound/intro_xD");
			mySound.play();
			buffer.delete(0, buffer.length());
		} else if (in("SOUNDDEMO")) {
			Sound mySound = assets().getSound("sound/fancy_riff3");
			mySound.play();
		}
	}

}
