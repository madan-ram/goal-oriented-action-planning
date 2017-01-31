package agents;
import goap.*;

import java.util.HashMap;

import battlecode.common.RobotController;

public class DefenderLumberjackAgent extends GoapAgent {

	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		
		goal.put("defendArchon", true);
		goal.put("defendGardener", true);

		return goal;
	}
} 
