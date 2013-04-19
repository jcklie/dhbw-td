package de.dhbw.td.core.ui;

import playn.core.Keyboard.Event;
import playn.core.Mouse.ButtonEvent;

public interface IUIEventListener {
	public EUserAction onClick(ButtonEvent event);
	public EUserAction onKey(Event event);
}
