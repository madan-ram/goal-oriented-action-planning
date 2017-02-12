package fsm;

import java.util.ArrayList;
import java.util.Queue;

import battlecode.common.*;
import common.Utils;
import goap.GoapAction;
import goap.GoapAgent;
import god.DataProvider;

public class PerformActionState extends FSMState {

ArrayList<GoapAction> availableActions;
	
	DataProvider dataProvider;
	GoapAgent agent;
	RobotController rc;
	FSM fsm;
	
	public PerformActionState(FSM fsm, RobotController rc, GoapAgent agent, DataProvider dataProvider) {
		this.fsm = fsm;
		this.rc = rc;
		this.agent = agent;
		this.dataProvider = dataProvider;
		
		availableActions = GoapAgent.getActions();
		
	}
	
	public void play() throws GameActionException {
		
		Queue<GoapAction> currentActions = dataProvider.getCurrentActions();

		GoapAction action = currentActions.peek();
		
		if ( action.isDone() ) {
			// the action is done. Remove it so we can perform the next one
			if(currentActions.poll() == null) {
				System.out.println("Error: currentActions is null");
			}
		} else {
			Utils.printERROR(rc, "Action not done");
		}

		//TODO fix this problem
		//if (hasActionPlan()) {
		if (!currentActions.isEmpty()) {
			// perform the next action
			action = currentActions.peek();
			//boolean inRange = action.requiresInRange() ? action.isInRange() : true;
			//TODO fix this problem
			boolean inRange = true;
			
			if ( inRange ) {
				// we are in range, so perform the action
				boolean success = action.perform(rc);
				
				if (!success) {
					// action failed, we need to plan again (perform return false only when action failure should reconstruct new plan)
					fsm.popState();
					fsm.pushState(GoapAgent.idleState);
					dataProvider.planAborted(action);
				}
			} else {
				// we need to move there first
				// push moveTo state
				fsm.pushState(GoapAgent.moveToState);
			}

		} else {
			// no actions left, move to Plan state
			fsm.popState();
			fsm.pushState(GoapAgent.idleState);
			dataProvider.actionsFinished();
		}
	}

}
