package god;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import goap.GoapAction;

public class DataProvider {
	
	static RobotController rc;
	Queue<GoapAction> currentActions = null;
	ArrayList<HashMap<String, Object>> currentGoal = null;
	
	public static int hireGardenerTurn = -10;
	
	//store the world state
	static HashMap<String, Object> worldState;
	
	public DataProvider(RobotController rc) {
		this.rc = rc;
	}
	
	public static HashMap<String, Object> getWorldState() {
		worldState = new HashMap<String, Object>();
		MapLocation[] locations = rc.getInitialArchonLocations(rc.getTeam());
		
		//Initial world state
		worldState.put("hasArchon", locations.length > 0);
		worldState.put("hasBullets", rc.getTeamBullets()> 0.0);
		worldState.put("hasFarmGardener", false);
		return worldState;
	}

	public Queue<GoapAction> getCurrentActions() {
		return currentActions;
	}
	
	public ArrayList<HashMap<String, Object>> getCurrentGoal() {
		return currentGoal;
	}
	
	public void planFound(ArrayList<HashMap<String, Object>> goal, Queue<GoapAction> plan) {
		this.currentActions = plan;
		this.currentGoal = goal;
	}

	public void planFailed(ArrayList<HashMap<String, Object>> goal) {
		// TODO Auto-generated method stub
		
	}

	public void planAborted(GoapAction action) {
		System.out.printf("Plan aborted because of action %s \n", action);
		
	}

	public void actionsFinished() {
		System.out.printf("Plan sucrssfully completed \n");	
	}

	public static void hiredGardener() {
		hireGardenerTurn = rc.getRoundNum();
		System.out.printf("Gardener %d\n",hireGardenerTurn);
	}
	
	public static boolean hasHireGardenerTurn() {
		if(rc.getRoundNum() - hireGardenerTurn > 10) {
			System.out.printf("Has hireGardenerTurn %d rc.getRoundNum() %d\n", hireGardenerTurn,  rc.getRoundNum());
			return true;
		}
		return false;
	}
}
