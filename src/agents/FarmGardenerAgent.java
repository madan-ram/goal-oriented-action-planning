package agents;

import java.util.ArrayList;
import java.util.HashMap;

import battlecode.common.RobotController;
import goap.GoapAgent;
import god.DataProvider;

public class FarmGardenerAgent extends GoapAgent {

	public ArrayList<HashMap<String, Object>> createGoalsState() {
		ArrayList<HashMap<String, Object>> goals = new ArrayList<HashMap<String, Object>>();
		
		//addGoal takes in list of K,V and return goal, this list represent "and goal"
		//list of "and goal" forms goals
		
		goals.add(addGoal(new Tuple("plantTrees", true)));
		
		goals.add(addGoal(new Tuple("waterTrees", true)));
				
		goals.add(addGoal(new Tuple("DonateForVictoryPoints", true)));
		
		goals.add(addGoal(new Tuple("requestLumberForFreeSpace", true)));
		
		goals.add(addGoal(new Tuple("moveRandom", true)));
		
		return goals;
	}
	
	public void createActionList(RobotController rc,DataProvider dataProvider) {
		addAction(
				new actions.LocateFreeSpaceAction(rc, dataProvider),
				new actions.PlantTreeAction(rc,dataProvider),
				new actions.MoveRandomAction(rc, dataProvider)
		);
	}
}
