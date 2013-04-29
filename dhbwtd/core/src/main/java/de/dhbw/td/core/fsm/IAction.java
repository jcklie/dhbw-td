package de.dhbw.td.core.fsm;


public interface IAction<T> {
	
	@SuppressWarnings("rawtypes")
	IAction NONE = new IAction() {

		@Override
		public void execute(Object arg) {
			return;	
		}
		
	};
	
	void execute(T arg);

}
