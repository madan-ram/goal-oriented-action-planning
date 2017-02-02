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
	
	public void createActionList(RobotController rc,DataProvider dataProvider) {
		addAction(
				new actions.HireFarmGardenerAction(rc, dataProvider),
				new actions.LocateFreeSpaceAction(rc, dataProvider),
				new actions.PlantTreeAction(rc,dataProvider),
				new actions.MoveRandomAction(rc, dataProvider)
		);
	}
	
	protected class Tuple {
		String key;
		Object value;
		public Tuple(String key, Object value) {
			this.key = key;
			this.value = value;
		}
	}
	
	protected HashMap<String, Object> addGoal(Tuple... a) {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		for(Tuple b:a) {
			goal.put(b.key, b.value);
		}
		return goal;
	}
	
	public static ArrayList<GoapAction> getActions() {
		return actionList;
	}
	
	public void start(RobotController rc) {
		
		//create data provider for each agent
		DataProvider dataProvider = new DataProvider(rc);
				
		//init action to provided useful methods
		GoapAction.init(rc);
		
		//create all the action list
		createActionList(rc, dataProvider);
				
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
		} catch (Exception e) {
			System.out.println("###############################################################################");
			System.out.printf("Finite State Machine error\n");
			e.printStackTrace();
			System.out.println("###############################################################################");
		}
		
	}
	
	public abstract ArrayList<HashMap<String, Object>> createGoalsState();
	
	public static String prettyPrint(HashMap<String, Object> state) {
		String s = "";
		for(String key:state.keySet()) {
			s += key + ":" + state.get(key).toString();
			s += "\n ";
		}
		return s;
	}
}
