package god;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import battlecode.common.GameActionException;
import battlecode.common.GameConstants;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import common.ChannelConstIndex;
import goap.GoapAction;

public class DataProvider {
	
	static RobotController rc;
	Queue<GoapAction> currentActions = null;
	ArrayList<HashMap<String, Object>> currentGoal = null;
	
	public static int hireGardenerTurn = -10;
	
	//some constant value
	static int MaxNumFarmGardener = 5;
	static int MaxNumPlantTrees = 20;
	static int MaxTreeGardenerCanPlant = 6;
	static int TreePlantedByThisGardener = 0;
	static double ThirstyThreshold = GameConstants.BULLET_TREE_MAX_HEALTH*0.20;
	
	public static double getThirstyThreshold() {
		return ThirstyThreshold;
	}
	
	//store the world state
	static HashMap<String, Object> worldState = new HashMap<String, Object>();;
	
	public DataProvider(RobotController rc) {
		this.rc = rc;
	}
	
	public static HashMap<String, Object> getWorldState() {
		MapLocation[] locations = rc.getInitialArchonLocations(rc.getTeam());
		
		//Initial world state
		worldState.put("hasArchon", locations.length > 0);
		worldState.put("hasBullets", rc.getTeamBullets() > 0.0);
		try {
			worldState.put("hasFarmGardener", rc.readBroadcast(ChannelConstIndex.GARDENER_COUNT) >= MaxNumFarmGardener);
		} catch (GameActionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		worldState.put("hasPlantTrees", TreePlantedByThisGardener >=  MaxTreeGardenerCanPlant);
		worldState.put("hasLocatedFreeSpace", false);
		return worldState;
	}

	
	public static void updateState(String key, boolean value) {
		worldState.put(key, value);
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
	}
	
	public static boolean hasHireGardenerTurn() {
		if(rc.getRoundNum() - hireGardenerTurn > 10) {
			return true;
		}
		return false;
	}
}
