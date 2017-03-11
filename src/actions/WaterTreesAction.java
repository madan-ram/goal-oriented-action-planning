package actions;

import battlecode.common.*;
import common.Utils;
import goap.GoapAction;
import god.DataProvider;

public class WaterTreesAction extends GoapAction {
	
	//TODO implement action to water trees
	boolean wateredTree = false;
	TreeInfo[] thirstyTrees = new TreeInfo[6];
	int numThirstyTrees = 0;
	
	public WaterTreesAction(RobotController rc, DataProvider dataProvider) {
		super(rc, dataProvider);
						
		addPreCondition("hasPlantTrees", false);
		
		addEffect("hasWateredTrees", true);
	}
	
	@Override
	public void doReset() {
		numThirstyTrees = 0;
		wateredTree = false;
		
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return wateredTree;
	}
	
	@Override
	public boolean perform(RobotController rc) throws GameActionException {
		
		//Failed action should not create complete new plan
		for(int i=0; i<numThirstyTrees; i++) {
			TreeInfo tree = thirstyTrees[i];
			//test which all tree near to the gardener require water
			//can water those tree if less health
			if(rc.canWater(tree.ID)) {
				rc.water(tree.ID);
			} else {
				Utils.printERROR(rc, "Failed to water tree with id:%d", tree.ID);
				return false;
			}
		}
		wateredTree = true;
		return true;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		//ASSUME: that tree are near and with in "water radius" as specified in spec and no movement needed
		
		//get tree near vision radius and it's health of tree 
		TreeInfo[] nearbyTrees = rc.senseNearbyTrees(GameConstants.INTERACTION_DIST_FROM_EDGE+GameConstants.BULLET_TREE_RADIUS, rc.getTeam());
		for(TreeInfo tree: nearbyTrees) {
			//test which all tree near to the gardener require water
			//can water those tree if less health
			double health_percent = (100.-(tree.health/GameConstants.BULLET_TREE_MAX_HEALTH) * 100.);
			if(dataProvider.getThirstyThreshold() <= health_percent && rc.canWater(tree.ID)) {
				thirstyTrees[numThirstyTrees] = tree;
				numThirstyTrees++;
			}
		}
		if(numThirstyTrees>0) {
			return true;
		}
		return false;
	}
}
