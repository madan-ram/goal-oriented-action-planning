package agents;
import goap.*;

import java.util.HashMap;

import battlecode.common.RobotController;

public class ChopperLumberjackAgent extends GoapAgent {
	
	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		
		goal.put("chopForPath", true);
		goal.put("chopForSpace", true);
		goal.put("chopForbot", true);
		goal.put("chopEnemyTree", true);
		
		return goal;
	}
} 
