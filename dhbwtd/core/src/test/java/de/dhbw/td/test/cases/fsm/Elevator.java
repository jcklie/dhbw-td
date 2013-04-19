package de.dhbw.td.test.cases.fsm;

import de.dhbw.td.core.fsm.IAction;

public class Elevator {
	private ELightState green;
	private ELightState red;
	
	public final IAction<String> UP = new IAction<String>() {
		
		@Override
		public void execute(String ... args) {
			red = ELightState.OFF;	
			green = ELightState.ON;	
		}
	};
	
	public final IAction<String> DOWN = new IAction<String>() {		
		@Override
		public void execute(String ... args) {
			red = ELightState.ON;	
			green = ELightState.OFF;
		}
	};
	
	public Elevator() {
		red = ELightState.ON;
		green = ELightState.OFF;
	}
	
	public ELightState green() {
		return green;
	}

	public ELightState red() {
		return red;
	}
}
