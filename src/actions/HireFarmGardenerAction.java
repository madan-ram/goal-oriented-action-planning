package actions;

import battlecode.common.*;
import goap.GoapAction;
import god.DataProvider;
import common.Utils;

public class HireFarmGardenerAction extends GoapAction {
		
	boolean hiredGardener = false;
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
		
		/* TODO
			check weather other Archon hired gardener if yes then note down and update internal turn count
			if no hire the gardner and let data provider broadcast this info to rest of archon
		 */
		
		//int d = Utils.nextRandomInt(0, 7);
		for(Direction dir:getSixDirection()) {
			if(rc.canHireGardener(dir) && DataProvider.hasHireGardenerTurn()) {
				System.out.printf("Found direction to hire gardener %s\n", dir.getAngleDegrees());
				targetDir = dir;
				return true;
			} /*else {
				System.out.printf("Not Found direction to hire gardener %s\n", dir.getAngleDegrees());
			}*/
		}
		
		return false;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
