package fsm;

import java.util.ArrayList;
import java.util.Stack;

import battlecode.common.Clock;
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
		boolean returnToClock = false;
		while(true) {
			returnToClock = false;
			if(stateStack.peek() != GoapAgent.idleState && stateStack.peek() != null) {
				returnToClock = true;
			}
			//play the push state
			if(stateStack.peek() != null) {
				stateStack.peek().play();
			} else {
				System.out.println("state stack is null");
			}
			
			if(returnToClock) {
				Clock.yield();
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
