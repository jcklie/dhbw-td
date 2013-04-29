package de.dhbw.td.test.cases.fsm;

import junit.framework.TestCase;
import de.dhbw.td.core.fsm.FiniteStateMachine;
import de.dhbw.td.core.fsm.IAction;

public class FiniteStateMachineTest extends TestCase {
	
	/*
	 * I here use some fsm examples from 
	 * www.cs.princeton.edu/courses/archive/spr06/cos116/FSM_Tutorial.pdf
	 */
	
	FiniteStateMachine<EElevatorState, String> elevatorFSM;
	Elevator elevator;
	
	@Override
	protected void setUp() throws Exception {
		elevator = new Elevator();
		elevatorFSM = createElevatorFsm();		
	}
	
	protected void tearDown() throws Exception {
		elevatorFSM = null;
		elevator = null;
	}
	
	private FiniteStateMachine<EElevatorState, String> createElevatorFsm() {
		FiniteStateMachine<EElevatorState, String> fsm = new  FiniteStateMachine<EElevatorState, String>(EElevatorState.values(), EElevatorState.GROUND);
		fsm.addTransition(EElevatorState.GROUND, EElevatorState.FIRST, elevator.UP);
		fsm.addTransition(EElevatorState.FIRST, EElevatorState.GROUND, elevator.DOWN);
		
		return fsm;		
	}

	public void testElevatorFsmHasTransitions() {
		assertTrue(elevatorFSM.hasTransition(EElevatorState.GROUND, EElevatorState.FIRST));
		assertTrue(elevatorFSM.hasTransition(EElevatorState.FIRST, EElevatorState.GROUND));
	}
	
	public void testTransitions() {
		IAction<String> action;
		
		// Default initialization
		assertEquals(EElevatorState.GROUND, elevatorFSM.currentState());
		assertEquals(ELightState.OFF, elevator.green());
		assertEquals(ELightState.ON, elevator.red());
		
		// We are in GROUND and press DOWN, nothing should happen		
		action = elevatorFSM.transit(EElevatorState.GROUND);
		action.execute("Ground");
		
		assertEquals(EElevatorState.GROUND, elevatorFSM.currentState());
		assertEquals(ELightState.OFF, elevator.green());
		assertEquals(ELightState.ON, elevator.red());
		
		// We are in GROUND and press UP
		action = elevatorFSM.transit(EElevatorState.FIRST);
		action.execute("FIRST");
		
		assertEquals(EElevatorState.FIRST, elevatorFSM.currentState());
		assertEquals(ELightState.ON, elevator.green());
		assertEquals(ELightState.OFF, elevator.red());
		
		// We are in FIRST and press UP
		action = elevatorFSM.transit(EElevatorState.FIRST);
		action.execute("FIRST");
		
		assertEquals(EElevatorState.FIRST, elevatorFSM.currentState());
		assertEquals(ELightState.ON, elevator.green());
		assertEquals(ELightState.OFF, elevator.red());
		
		// We are in FIRST and press DOWN
		action = elevatorFSM.transit(EElevatorState.GROUND);
		action.execute("Ground");
		
		assertEquals(EElevatorState.GROUND, elevatorFSM.currentState());
		assertEquals(ELightState.OFF, elevator.green());
		assertEquals(ELightState.ON, elevator.red());		
	}
	


}
