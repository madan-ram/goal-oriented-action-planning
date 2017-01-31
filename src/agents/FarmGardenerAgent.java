package agents;

import java.util.HashMap;
import java.util.HashSet;

import battlecode.common.RobotController;
import goap.GoapAgent;

public class FarmGardenerAgent extends GoapAgent {

	public HashMap<String, Object> createGoalState() {
		HashMap<String, Object> goal = new HashMap<String, Object>();
		
		goal.put("plantTrees", true);
		goal.put("waterTrees", true);
		goal.put("locateFreeSpace", true);
		goal.put("DonateForVictoryPoints", true);
		goal.put("requestLumberForFreeSpace", true);
		
		return goal;
	}
}
