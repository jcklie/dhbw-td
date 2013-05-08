/*  Copyright (C) 2013. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie - All 
 */

package de.dhbw.td.core.fsm;

/**
 * 
 *
 * @param <E> The enum which defines the states of the FSM
 */
public class FiniteStateMachine<E extends Enum<E>> {
	
	private IAction[][] transitionTable;
	private E currentState;
	private E lastState;
	
	private final int N; // NUMBER_OF_STATES
	
	public FiniteStateMachine(E[] states) {
		this(states, states[0]);
	}
	
	public FiniteStateMachine(E[] states, E startingState) {
		N = states.length;
		
		initTransitionTable();	
		currentState = startingState;
		lastState = startingState;
	}
	
	private void initTransitionTable() {
		transitionTable = new IAction[N][N];
		
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				transitionTable[row][col] = IAction.NONE;
			}
		}
	}
	
	public IAction getAction(E source, E target) {
		return transitionTable[source.ordinal()][target.ordinal()];
	}
	
	public void addTransition(E source, E target, IAction action) {
		int sourceIndex = source.ordinal();
		int targetIndex = target.ordinal();
		transitionTable[sourceIndex][targetIndex] = action;
	}
	
	public boolean hasTransition(E source, E target) {
		IAction action = getAction(source, target);
		return action != null && action != IAction.NONE;
	}
	
	public IAction transit(E nextState) {
		IAction actionOnTransit = getAction(currentState, nextState);
		lastState = currentState;
		currentState = nextState;
		
		return actionOnTransit;
	}
	
	public E currentState() {
		return currentState;
	}
	
	public E lastState() {
		return lastState;
	}

}
