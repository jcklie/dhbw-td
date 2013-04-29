package de.dhbw.td.core.fsm;

import java.util.EnumSet;

import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.resources.EInformationText;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.ui.EUserAction;
import de.dhbw.td.core.util.EFlavor;
import de.dhbw.td.core.util.GameConstants;

/**
 * This class is used to hide all the nasty details related to execute the
 * user interactions.
 * @author Jan-Christoph Klie, Lucas Berg, Benedict Holste
 *
 */
public class Executor {
	
	private GameState gameState;
	private FiniteStateMachine<EUserAction, EFlavor> fsm;
	private EFlavor flavor;
	private int x;
	private int y;
	
	private static EnumSet<EUserAction> NEW_TOWER_ACTIONS = EnumSet.range(EUserAction.NEW_MATH_TOWER, EUserAction.NEW_SOCIAL_TOWER);

	
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
		fsm.addTransition(EUserAction.NONE, EUserAction.NONE, setInformationAboutTower);
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
		public void execute(EFlavor flavor) {
			gameState.buildTower(flavor, x, y);
			gameState.clearInformation();
		}
	};
	
	private IAction<EFlavor> upgradeTower = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			gameState.upgradeTower(x, y);
		}
	};
	
	private IAction<EFlavor> sellTower = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			gameState.sellTower(x, y);
		}
	};
	
	private IAction<EFlavor> updateInformationText = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			String informationText = EInformationText.getInformationText(flavor, gameState.levelCount());
			gameState.setInformation("Baue:  " + informationText);
		}
	};
	
	private IAction<EFlavor> updateInformationSell = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			gameState.setInformation("Verkaufe");
		}
	};
	
	private IAction<EFlavor> updateInformationUpgrade = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			gameState.setInformation("Verbessere");
		}
	};
	
	private IAction<EFlavor> setInformationAboutTower = new IAction<EFlavor>() {
		public void execute(EFlavor flavor) {
			Tower t = gameState.getTower(x, y);
			if( t ==  null) {
				gameState.clearInformation();
			} else {
				String infoText = EInformationText.getInformationText(t.flavor(), t.level() + 1);
				gameState.setInformation(String.format("%s - %d", infoText, t.upgradeCost()));
			}
		}
	};
	
	private EFlavor newTowerToFlavor(EUserAction state) {
		return  GameConstants.mapActionToFlavor(state);
	}


}
