package agents;
import goap.*;
import god.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

import battlecode.common.RobotController;

public class DefenderLumberjackAgent extends GoapAgent {

	public ArrayList<HashMap<String, Object>> createGoalsState() {
		ArrayList<HashMap<String, Object>> goals = new ArrayList<HashMap<String, Object>>();
		
		//addGoal takes in list of K,V and return goal, this list represent "and goal"
		//list of "and goal" forms goals
		
		goals.add(addGoal(new Tuple("defendArchon", true)));
		
		goals.add(addGoal(new Tuple("defendGardener", true)));

		return goals;
	}
	
	public void createActionList(RobotController rc,DataProvider dataProvider) {
		addAction(
				new actions.HireFarmGardenerAction(rc, dataProvider),
				new actions.LocateFreeSpaceAction(rc, dataProvider),
				new actions.PlantTreeAction(rc,dataProvider),
				new actions.MoveRandomAction(rc, dataProvider)
		);
	}
} 
