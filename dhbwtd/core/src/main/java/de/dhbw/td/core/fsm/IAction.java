package de.dhbw.td.core.fsm;


public interface IAction {
	
	IAction NONE = new IAction() {

		@Override
		public void execute() {
			return;	
		}
		
	};
	
	void execute();

}
