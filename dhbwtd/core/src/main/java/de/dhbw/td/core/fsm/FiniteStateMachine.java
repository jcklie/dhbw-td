package de.dhbw.td.core.fsm;

import static playn.core.PlayN.log;

/**
 * 
 * @author Jan-Christoph Klie
 *
 * @param <E>
 * @param <R>
 */
public class FiniteStateMachine<E extends Enum<E>, R> {
	
	private IAction<R>[][] transitionTable;
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
	
	@SuppressWarnings("unchecked")
	private void initTransitionTable() {
		transitionTable = new IAction[N][N];
		
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < N; col++) {
				transitionTable[row][col] = IAction.NONE;
			}
		}
	}
	
	public IAction<R> getAction(E source, E target) {
		return transitionTable[source.ordinal()][target.ordinal()];
	}
	
	public void addTransition(E source, E target, IAction<R> action) {
		int sourceIndex = source.ordinal();
		int targetIndex = target.ordinal();
		transitionTable[sourceIndex][targetIndex] = action;
	}
	
	public boolean hasTransition(E source, E target) {
		IAction<R> action = getAction(source, target);
		return action != null && action != IAction.NONE;
	}
	
	public IAction<R> transit(E nextState) {
		IAction<R> actionOnTransit = getAction(currentState, nextState);
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
