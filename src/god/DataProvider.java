package god;

import java.util.HashMap;
import java.util.Queue;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import goap.GoapAction;

public class DataProvider {
	
	RobotController rc;
	Queue<GoapAction> currentActions = null;
	HashMap<String, Object> currentGoal = null;
	
	//store the world state
	HashMap<String, Object> worldState;
	
	public DataProvider(RobotController rc) {
		this.rc = rc;
	}
	
	public HashMap<String, Object> getWorldState() {
		worldState = new HashMap<String, Object>();
		MapLocation[] locations = rc.getInitialArchonLocations(rc.getTeam());
		
		//Initial world state
		worldState.put("hasArchon", locations.length > 0);
		worldState.put("hasBullets", rc.getTeamBullets()> 0.0);
		return worldState;
	}

	public Queue<GoapAction> getCurrentActions() {
		return currentActions;
	}
	
	public HashMap<String, Object> getCurrentGoal() {
		return currentGoal;
	}
	
	public void planFound(HashMap<String, Object> goal, Queue<GoapAction> plan) {
		this.currentActions = plan;
		this.currentGoal = goal;
	}

	public void planFailed(HashMap<String, Object> goal) {
		// TODO Auto-generated method stub
		
	}

	public void planAborted(GoapAction action) {
		System.out.printf("Plan aborted because of action %s \n", action);
		
	}

	public void actionsFinished() {
		System.out.printf("Plan sucrssfully completed %s \n");
		
	}
}
