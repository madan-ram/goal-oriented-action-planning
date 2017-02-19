package fsm;

import java.util.ArrayList;
import java.util.Stack;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import goap.GoapAgent;
import god.DataProvider;
import scala.tools.nsc.settings.RC;

final class FSMNoStateException extends Exception {
	
	FSMNoStateException(String s) {
		super(s);
		System.exit(-1);
	}
}

public class FSM {
	
	public ArrayList<FSMState> FSMList = new ArrayList<FSMState>();
	private Stack<FSMState> stateStack = new Stack<FSMState>();
	
	public void addToFSM(FSMState state) {
		if (state != null)
			FSMList.add(state);
	}
	
	public void start() throws GameActionException, FSMNoStateException {
		boolean returnToClock;
		while(true) {
			returnToClock = false;
			//if not in idle state then there is some task to be done, this means we need to return clock
			if(stateStack.peek() != GoapAgent.idleState) {
				returnToClock = true;
			}
			
			//play the push state
			if(stateStack.peek() != null) {
				stateStack.peek().play();
			} else {
				throw new FSMNoStateException("FSM recived null exception which break system");
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
