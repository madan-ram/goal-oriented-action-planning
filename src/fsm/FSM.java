package fsm;

import java.util.ArrayList;
import java.util.Stack;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import goap.GoapAgent;
import god.DataProvider;

public class FSM {
	
	public ArrayList<FSMState> FSMList = new ArrayList<FSMState>();
	private Stack<FSMState> stateStack = new Stack<FSMState>();
	
	public void addToFSM(FSMState state) {
		if (state != null)
			FSMList.add(state);
	}
	
	public void start() throws GameActionException {
		while(true) {
			//play the push state
			if(stateStack.peek() != null) {
				stateStack.peek().play();
			}
		}
	}
	
	public void pushState(FSMState state) {
		if(state != null) {
			//push state change
			stateStack.push(state);
		}
		
	}
	
	public void popState() {
		stateStack.pop();
	}
}
