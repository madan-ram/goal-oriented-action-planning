package agents;

import java.util.ArrayList;
import java.util.HashMap;

import goap.GoapAgent;

public class BotGardenerAgent extends GoapAgent {
	
	public ArrayList<HashMap<String, Object>> createGoalsState() {
		ArrayList<HashMap<String, Object>> goals = new ArrayList<HashMap<String, Object>>();
		
		//addGoal takes in list of K,V and return goal, this list represent "and goal"
		//list of "and goal" forms goals
		
		goals.add(addGoal(new Tuple("buildLumberJack", true)));
		
		goals.add(addGoal(new Tuple("buildSoldier", true)));
		
		goals.add(addGoal(new Tuple("buildScout", true)));
		
		goals.add(addGoal(new Tuple("buildtank", true)));
		
		return goals;
	}
}
