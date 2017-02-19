package actions;

import battlecode.common.*;
import goap.GoapAction;
import god.DataProvider;

public class PlantTreeAction extends GoapAction {
	
	boolean plantedTree = false;
	static RobotController rc;
	
	public PlantTreeAction(RobotController rc, DataProvider dataProvider) {
		super(rc, dataProvider);
		
		MoveRandomAction.dataProvider = dataProvider;
		PlantTreeAction.rc = rc;
		
		//{hasArchon=true, hasBullets=true, hasFarmGardener=false}
		
		addPreCondition("hasLocatedFreeSpace", true);
		addPreCondition("hasPlantTrees", false);
		addPreCondition("hasBullets", true);
		
		addEffect("hasPlantTrees", true);
		//Effect performed goal
		addEffect("plantTrees", true);	
	}
	
	@Override
	public void doReset() {
		plantedTree = false;
		
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return plantedTree;
	}
	
	@Override
	public boolean perform(RobotController rc) throws GameActionException {
		Direction[] dir6 = GoapAction.getSixDirection();
		float spawnDist = rc.getType().bodyRadius + GameConstants.GENERAL_SPAWN_OFFSET + GameConstants.BULLET_TREE_RADIUS;
		
		//Assume we will be successful
		plantedTree = true;
		for(int i=0; i<dir6.length; i++) {
			MapLocation spawnLoc = rc.getLocation().add(dir6[i], spawnDist);
			if(rc.canPlantTree(dir6[i])) {
				rc.plantTree(dir6[i]);
			} else if(!isCircleOccupiedByTree(spawnLoc, GameConstants.BULLET_TREE_RADIUS, rc.getTeam())) {
				//failed once is considered plan failed, but should not be failed because of our own tree
				plantedTree = false;
			}
		}
		
		//Failed action should not create complete new plan
		return true;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		return true;
	}
}
