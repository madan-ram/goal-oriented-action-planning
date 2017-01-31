package goap;

import java.util.ArrayList;
import java.util.HashMap;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import fsm.*;
import god.*;
import actions.*;

public abstract class GoapAgent {
	
	private FSM fsm = new FSM();
	public static FSMState idleState;
	public static FSMState moveToState;
	public static FSMState performActionState;
	
	public static ArrayList<GoapAction> actionList = new ArrayList<GoapAction>();
	
	public void addAction(GoapAction... as) {
		for(GoapAction a:as) {
			actionList.add(a);
		}
	}
	
	public void createActionList(RobotController rc) {
		addAction(
				new actions.HireFarmGardenerAction(rc),
				new actions.LocateFreeSpaceAction(rc),
				new actions.PlantTreeAction(rc),
				new actions.MoveRandomAction(rc)
		);
	}
	
	public static ArrayList<GoapAction> getActions() {
		return actionList;
	}
	
	public void start(RobotController rc) {
		
		//init action to provided useful methods
		GoapAction.init(rc);
		
		//create all the action list
		createActionList(rc);
				
		//create data provider for each agent
		DataProvider dataProvider = new DataProvider(rc);
				
		//Create IdleState
		idleState = new IdleState(fsm, rc, this, dataProvider);
		fsm.addToFSM(idleState);
		
		//Create PerformActionState
		performActionState = new PerformActionState(fsm, rc, this, dataProvider);
		fsm.addToFSM(performActionState);
		
		//create MoveState
		moveToState = new MoveState(fsm, rc, this, dataProvider);
		fsm.addToFSM(moveToState);
		
		//start with idlestate
		fsm.pushState(idleState);
		
		//start the game
		try {
			fsm.start();
		} catch (GameActionException e) {
			System.out.println("###############################################################################");
			System.out.printf("Finite State Machine error\n");
			e.printStackTrace();
			System.out.println("###############################################################################");
		}
		
	}
	
	public abstract HashMap<String, Object> createGoalState();
	
	public static String prettyPrint(HashMap<String, Object> state) {
		String s = "";
		for(String key:state.keySet()) {
			s += key + ":" + state.get(key).toString();
			s += "\n ";
		}
		return s;
	}
}
