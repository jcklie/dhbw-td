package de.dhbw.td.core.game;

public class GameState {
	
	private boolean changed = true;
	
	public boolean hasChanged() {
		if(changed) {
			changed = false;
			return true;
		}
		return false;
	}

}
