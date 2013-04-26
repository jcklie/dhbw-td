package de.dhbw.td.core.fsm;

import static playn.core.PlayN.log;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.ui.EUserAction;
import de.dhbw.td.core.util.EFlavor;

/**
 * This class is used to hide all the nasty details related to execute the
 * user interactions.
 * @author Jan-Christoph Klie
 *
 */
public class Executor {
	
	private GameState gameState;
	private FiniteStateMachine<EUserAction, EFlavor> fsm;
	private int x;
	private int y;
	
	private static EnumSet<EUserAction> NEW_TOWER_ACTIONS = EnumSet.range(EUserAction.NEW_MATH_TOWER, EUserAction.NEW_SOCIAL_TOWER);
	private static Map<EUserAction, EFlavor> mappingActionToFlavor;
	
	static {
		mappingActionToFlavor = new HashMap<EUserAction, EFlavor>();
		mappingActionToFlavor.put(EUserAction.NEW_MATH_TOWER, EFlavor.MATH);
		mappingActionToFlavor.put(EUserAction.NEW_TECH_INF_TOWER, EFlavor.COMPUTER_ENGINEERING);
		mappingActionToFlavor.put(EUserAction.NEW_CODE_TOWER, EFlavor.PROGRAMMING);
		mappingActionToFlavor.put(EUserAction.NEW_THEO_INF_TOWER, EFlavor.THEORETICAL_COMPUTER_SCIENCE);
		mappingActionToFlavor.put(EUserAction.NEW_ECO_TOWER, EFlavor.ECONOMICS);
		mappingActionToFlavor.put(EUserAction.NEW_SOCIAL_TOWER, EFlavor.SOCIAL);
	}
	
	public Executor(GameState gameState) {
		this.gameState = gameState;
		EUserAction[] possibleStates = EUserAction.values();
		fsm = new FiniteStateMachine<EUserAction, EFlavor>(possibleStates, EUserAction.NONE);
		
		initFSM();
	}
	
	private void initFSM() {
		initTowerStates();
		fsm.addTransition(EUserAction.UPGRADE, EUserAction.NONE, upgradeTower);
		fsm.addTransition(EUserAction.SELL, EUserAction.NONE, sellTower);
	}
	
	/**
	 * We want to build a tower if we were in NEW_<FOO>_TOWER state
	 * and then move to NONE
	 */
	private void initTowerStates() {
		for(EUserAction source : NEW_TOWER_ACTIONS) {
			fsm.addTransition(source, EUserAction.NONE, buildTower);
		}
	}
	
	public void handleNewState(EUserAction newState, int x, int y) {
		this.x = x;
		this.y = y;

		if( wasItIntendedToBuildTower(newState)) {
			handleNewTower(newState);
		} else {
			handleSimpleState(newState);
		}
	}
	
	public void handleNewState(EUserAction newState) {
		handleSimpleState(newState);
	}
	
	/**
	 * We assume that the user wants to build a tower if he selected in the last 
	 * step a tower and then clicked on empty space
	 * @param newState The current action
	 * @return Whether we assume that a tower wants to be built
	 */
	private boolean wasItIntendedToBuildTower(EUserAction newState) {
		return NEW_TOWER_ACTIONS.contains(fsm.currentState()) && newState == EUserAction.NONE;
	}
	
	private void handleSimpleState(EUserAction newState) {
		log().debug("Handle simple");
		IAction<EFlavor> action = fsm.transit(newState);
		action.execute();
	}
	
	private void handleNewTower(EUserAction newState) {
		log().debug("Handle new tower");
		IAction<EFlavor> action = fsm.transit(newState);
		EFlavor flavor = newTowerToFlavor(fsm.lastState());
		log().debug("F: " + flavor);
		action.execute(flavor);
	}
	
	private IAction<EFlavor> buildTower = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			log().debug("I want to build a " + Arrays.toString(args));
			gameState.buildTower(args[0], x, y);
		}
	};
	
	private IAction<EFlavor> upgradeTower = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			log().debug("I want to upgrade a tower");
			gameState.upgradeTower(x, y);
		}
	};
	
	private IAction<EFlavor> sellTower = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			log().debug("I want to sell a tower");
			gameState.sellTower(x, y);
		}
	};
	
	private EFlavor newTowerToFlavor(EUserAction state) {
		log().debug("Mapping :" + state);
		return mappingActionToFlavor.get(state);
	}


}
