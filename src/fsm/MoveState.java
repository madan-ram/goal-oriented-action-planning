package fsm;

import java.util.ArrayList;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import goap.GoapAction;
import goap.GoapAgent;
import goap.GoapPlanner;
import god.DataProvider;

public class MoveState extends FSMState {

	ArrayList<GoapAction> availableActions;
	
	DataProvider dataProvider;
	GoapAgent agent;
	RobotController rc;
	FSM fsm;
	
	public MoveState(FSM fsm, RobotController rc, GoapAgent agent, DataProvider dataProvider) {
		this.fsm = fsm;
		this.rc = rc;
		this.agent = agent;
		this.dataProvider = dataProvider;
		
		availableActions = GoapAgent.getActions();
	}
	
	public void play() {
	}

}
