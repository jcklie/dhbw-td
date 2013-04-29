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
	private EFlavor flavor;
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
		initInformationTextTransitions();
		fsm.addTransition(EUserAction.UPGRADE, EUserAction.NONE, upgradeTower);
		fsm.addTransition(EUserAction.SELL, EUserAction.NONE, sellTower);
		fsm.addTransition(EUserAction.NONE, EUserAction.SELL, updateInformationSell);
		fsm.addTransition(EUserAction.NONE, EUserAction.UPGRADE, updateInformationUpgrade);
		fsm.addTransition(EUserAction.SELL, EUserAction.UPGRADE, updateInformationUpgrade);
		fsm.addTransition(EUserAction.UPGRADE, EUserAction.SELL, updateInformationSell);
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
	
	private void initInformationTextTransitions() {
		for(EUserAction source : NEW_TOWER_ACTIONS) {
			fsm.addTransition(EUserAction.NONE, source, updateInformationText);
			fsm.addTransition(source, EUserAction.SELL, updateInformationSell);
			fsm.addTransition(source, EUserAction.UPGRADE, updateInformationUpgrade);
			fsm.addTransition(EUserAction.SELL, source, updateInformationText);
			fsm.addTransition(EUserAction.UPGRADE, source, updateInformationText);
			for(EUserAction target : NEW_TOWER_ACTIONS) {
				fsm.addTransition(source, target, updateInformationText);
			}
		}
	}
	

	public void handleNewState(EUserAction newState, int x, int y) {
		this.x = x;
		this.y = y;
		

		handleNewState(newState);
		
	}
	
	public void handleNewState(EUserAction newState) {
		if( NEW_TOWER_ACTIONS.contains(newState)) {
			flavor = newTowerToFlavor(newState);
		}
		
		IAction<EFlavor> action = fsm.transit(newState);
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
			gameState.upgradeTower(x, y);
		}
	};
	
	private IAction<EFlavor> sellTower = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			gameState.sellTower(x, y);
		}
	};
	
	private IAction<EFlavor> updateInformationText = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			gameState.setInformation("Build " + args[0].name());
		}
	};
	
	private IAction<EFlavor> updateInformationSell = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			gameState.setInformation("Sell");
		}
	};
	
	private IAction<EFlavor> updateInformationUpgrade = new IAction<EFlavor>() {
		public void execute(EFlavor... args) {
			gameState.setInformation("Upgrade");
		}
	};
	
	private EFlavor newTowerToFlavor(EUserAction state) {
		return mappingActionToFlavor.get(state);
	}


}
