package actions;

import battlecode.common.*;
import goap.GoapAction;
import common.Utils;

public class HireFarmGardenerAction extends GoapAction {
		
	boolean hiredGardener = false;
	Direction targetDir = null;
	public static RobotController rc;
	
	public HireFarmGardenerAction(RobotController rc) {
		super(rc);
		HireFarmGardenerAction.rc = rc;
		
		addPreCondition("hasArchon", true);
		addPreCondition("hasFarmGardener", false);
		
		addPreCondition("hasFarmGardener", true);
		//Effect performed goal
		addEffect("hireFarmGardener", true);
	}
	
	@Override
	public boolean perform(RobotController rc) {
		if(targetDir != null && rc.canHireGardener(targetDir)) {
			try {
				rc.hireGardener(targetDir);
			} catch (GameActionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hiredGardener = true;
		}
		hiredGardener = false;
		
		//Failed action should not create complete new plan
		return true;
	}
	
	@Override
	public boolean isDone() {
		return hiredGardener;
	}
	
	@Override
	public void doReset() {
		hiredGardener = false;
		targetDir = null;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		
		//int d = Utils.nextRandomInt(0, 7);
		for(Direction dir:getEightDirection()) {
			if(rc.canHireGardener(dir)) {
				targetDir = dir;
				return true;
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
