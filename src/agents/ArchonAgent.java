package agents;
import goap.*;

import java.util.HashMap;

import battlecode.common.RobotController;

public class ArchonAgent extends GoapAgent {
	
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		
		goal.put("hireFarmGardener", true);
		goal.put("hireBotGardener", true);
		return goal;
	}
} 
