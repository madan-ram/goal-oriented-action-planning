package agents;
import goap.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DefenderLumberjackAgent extends GoapAgent {

	public ArrayList<HashMap<String, Object>> createGoalsState() {
		ArrayList<HashMap<String, Object>> goals = new ArrayList<HashMap<String, Object>>();
		
		//addGoal takes in list of K,V and return goal, this list represent "and goal"
		//list of "and goal" forms goals
		
		goals.add(addGoal(new Tuple("defendArchon", true)));
		
		goals.add(addGoal(new Tuple("defendGardener", true)));

		return goals;
	}
} 
