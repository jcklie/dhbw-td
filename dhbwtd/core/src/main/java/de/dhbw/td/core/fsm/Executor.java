package de.dhbw.td.core.fsm;

import static de.dhbw.td.core.util.GameConstants.NEW_TOWER_ACTIONS;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.resources.EInformationText;
import de.dhbw.td.core.tower.Tower;
import de.dhbw.td.core.tower.TowerStats;
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
	private TowerStats stats = new TowerStats();
	private EFlavor flavor;
	private int x;
	private int y;
	
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
		
		IAction action = fsm.transit(newState);
		action.execute();
	}
	
	private IAction buildTower = new IAction() {
		public void execute() {
			gameState.buildTower(flavor, x, y);
			gameState.clearInformation();
		}
	};
	
	private IAction upgradeTower = new IAction() {
		public void execute() {
			gameState.upgradeTower(x, y);
		}
	};
	
	private IAction sellTower = new IAction() {
		public void execute() {
			gameState.sellTower(x, y);
		}
	};
	
	private IAction updateInformationText = new IAction() {
		public void execute() {
			String informationText = EInformationText.getInformationText(flavor, gameState.levelCount());
			gameState.setInformation("Baue:  " + informationText);
		}
	};
	
	private IAction updateInformationSell = new IAction() {
		public void execute() {
			gameState.setInformation("Verkaufe");
		}
	};
	
	private IAction updateInformationUpgrade = new IAction() {
		public void execute() {
			gameState.setInformation("Verbessere");
		}
	};
	
	private IAction setInformationAboutTower = new IAction() {
		public void execute() {
			Tower t = gameState.getTower(x, y);
			System.out.println(t);
			if( t ==  null) {
				gameState.clearInformation();
			} else {
				flavor = t.flavor();
				String toolTip = buildToolTip(flavor, t.level() + 1); // + 1 since i want information about upgrade
				gameState.setInformation(toolTip);
			}
		}
	};
	
	private EFlavor newTowerToFlavor(EUserAction state) {
		return  GameConstants.mapActionToFlavor(state);
	}
	
	private String buildToolTip(EFlavor flavor, int level) {
		String towerDesc = EInformationText.getInformationText(flavor, gameState.levelCount());
		int dmg = stats.getDamage(flavor, level);
		int range = stats.getRange(flavor, level);
		int price = stats.getPrice(flavor, level);
		int cadenza = stats.getCadenza(flavor);
		
		return String.format("%s\n DMG:%d R:%d C:%d U:%d", towerDesc, dmg, range, cadenza, price > 0 ? price : "X" );
	}


}
