package actions;

import battlecode.common.*;
import common.Utils;
import goap.GoapAction;
import god.DataProvider;

public class ChopForSpaceAction extends GoapAction {
	
	//TODO implement action to water trees
	boolean chopedTree = false;
	TreeInfo[] blockingTrees = new TreeInfo[6];
	int numBlockingTrees = 0;

	public ChopForSpaceAction(RobotController rc, DataProvider dataProvider) {
		super(rc, dataProvider);
		
		addEffect("chopForSpace", true);
	}
	
	@Override
	public void doReset() {
		numBlockingTrees=0;
		chopedTree = false;
		
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return chopedTree;
	}
	
	@Override
	public boolean perform(RobotController rc) throws GameActionException {
		
		//Failed action should not create complete new plan
		for(int i=0; i<numBlockingTrees; i++) {
			Utils.printERROR(rc, "Number of blocking tree:%d ", numBlockingTrees);
			TreeInfo tree = blockingTrees[i];
			//chop the tree until they are removed completely
			if(rc.canShake(tree.ID) && tree.containedBullets!=0) {
				rc.shake(tree.ID);
			}
			while(rc.canChop(tree.ID)) {
				rc.chop(tree.ID);
			}
		}
		chopedTree = true;
		return true;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		//ASSUME: that tree are near and with in "water radius" as specified in spec and no movement needed
		
		//get tree near vision radius and it's health of tree 
		TreeInfo[] nearbyTrees = rc.senseNearbyTrees(GameConstants.INTERACTION_DIST_FROM_EDGE+GameConstants.BULLET_TREE_RADIUS, Team.NEUTRAL);
		for(TreeInfo tree: nearbyTrees) {
			if(rc.canChop(tree.ID)) {
				blockingTrees[numBlockingTrees] = tree;
				numBlockingTrees++;
			}
		}
		if(numBlockingTrees>0) {
			return true;
		}
		return false;
	}
}
