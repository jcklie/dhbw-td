package de.dhbw.td.test.cases.fsm;

import de.dhbw.td.core.fsm.IAction;

public class Elevator {
	private ELightState green;
	private ELightState red;
	
	public final IAction UP = new IAction() {
		
		@Override
		public void execute() {
			red = ELightState.OFF;	
			green = ELightState.ON;	
		}
	};
	
	public final IAction DOWN = new IAction() {		
		@Override
		public void execute() {
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
