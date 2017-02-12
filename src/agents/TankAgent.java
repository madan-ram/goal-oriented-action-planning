package agents;
import goap.*;
import god.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;

import battlecode.common.RobotController;

public class TankAgent extends GoapAgent {
	
	public ArrayList<HashMap<String, Object>> createGoalsState() {
		return null;
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
