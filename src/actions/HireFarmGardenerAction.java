package actions;

import battlecode.common.*;
import goap.GoapAction;
import god.DataProvider;
import common.Utils;

public class HireFarmGardenerAction extends GoapAction {
		
	static boolean hiredGardener = false;
	Direction targetDir = null;
	public static RobotController rc;
	
	public HireFarmGardenerAction(RobotController rc,DataProvider dataProvider) {
		super(rc, dataProvider);
		HireFarmGardenerAction.rc = rc;
		
		addPreCondition("hasArchon", true);
		addPreCondition("hasFarmGardener", false);
		
		addEffect("hasFarmGardener", true);
		//Effect performed goal
		addEffect("hireFarmGardener", true);
	}
	
	@Override
	public boolean perform(RobotController rc) {
		System.out.printf("HIRING GARDNER %f in direction\n", targetDir.getAngleDegrees());
		if(targetDir != null && rc.canHireGardener(targetDir)) {
			try {
				//communicate your hired info to data provider
				DataProvider.hiredGardener();
				rc.hireGardener(targetDir);
			} catch (GameActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hiredGardener = true;
		}else {
			hiredGardener = false;
		}
		
		//Failed action should not create complete new plan
		return true;
	}
	
	@Override
	public boolean isDone() {
		System.out.printf("Hired GARDNER and set isDone %s\n", hiredGardener);
		return hiredGardener;
	}
	
	@Override
	public void doReset() {
		hiredGardener = false;
		targetDir = null;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) throws GameActionException {
		
		/* TODO
			check weather other Archon hired gardener if yes then note down and update internal turn count
			if no hire the gardener and let data provider broadcast this info to rest of archon
		 */
		/*if(!DataProvider.hasHireGardenerTurn()) {
			System.out.println("Does not have turns #################");
			return false;
		}*/
		
		//System.out.printf("Number of cool down turn %d turn number %d\n", rc.getBuildCooldownTurns(), rc.getRoundNum());
		for(Direction dir:getSixDirection()) {
			if(rc.canHireGardener(dir) && rc.hasRobotBuildRequirements(RobotType.GARDENER)) {
				System.out.printf("Found direction to hire gardener %s\n", dir.getAngleDegrees());
				targetDir = dir;
				return true;
			} else {
				float spawnDist = rc.getType().bodyRadius + GameConstants.GENERAL_SPAWN_OFFSET + RobotType.GARDENER.bodyRadius;
				MapLocation spawnLoc = rc.getLocation().add(dir, spawnDist);
				
				//test for hire gardener and find out reason for task failure
				if(!rc.hasRobotBuildRequirements(RobotType.GARDENER))
				{
					if(rc.getTeamBullets() < RobotType.GARDENER.bulletCost) {
						Utils.printWarning(rc, "Amount of bullet %f and need %d to hire gardener", rc.getTeamBullets(), RobotType.GARDENER.bulletCost);
					} else {
						Utils.printWarning(rc, "Invalid builder %s to build gardener", rc.getType().toString());
					}
				} else if(rc.isCircleOccupied(spawnLoc, RobotType.GARDENER.bodyRadius)) {
					Utils.printWarning(rc, "Location %s with radius %f is not empty on map", spawnLoc.toString(), RobotType.GARDENER.bodyRadius);
				} else if(rc.onTheMap(spawnLoc, RobotType.GARDENER.bodyRadius)) {
					Utils.printWarning(rc, "Location %s with radius %f is not on the map", spawnLoc.toString(), RobotType.GARDENER.bodyRadius);	
				} else if(!rc.isBuildReady()) {
					Utils.printWarning(rc, "Is not build ready with cooldown turn of %d", rc.getBuildCooldownTurns());
				} else {
					Utils.printERROR(rc, "canHireGardener has unknown reson to fail");
				}
			}
		}
		
		return false;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
