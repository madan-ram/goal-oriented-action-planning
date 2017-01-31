package agents;

import java.util.HashMap;

import battlecode.common.RobotController;
import goap.GoapAgent;

public class BotGardenerAgent extends GoapAgent {
	
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		
		goal.put("buildLumberJack", true);
		goal.put("buildSoldier", true);
		goal.put("buildScout", true);
		goal.put("buildtank", true);
		
		return goal;
	}
}
