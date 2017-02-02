package actions;
import battlecode.common.Direction;
import battlecode.common.RobotController;
import goap.GoapAction;
import god.DataProvider;

public class LocateFreeSpaceAction extends GoapAction {
	
	boolean located = false;
	RobotController rc;
	
	public LocateFreeSpaceAction(RobotController rc, DataProvider dataProvider) {
		super(rc, dataProvider);
		this.rc = rc;
		
		addPreCondition("hasFarmGardener", true);
		addPreCondition("hasFreeSpace", false);
		
		addEffect("hasFreeSpace", true);
		//Effect performed goal
		addEffect("locateFreeSpace", true);
	}
	
	@Override
	public boolean perform(RobotController rc) {
		located = true;
		//Failed action should not create complete new plan
		return true;
	}
	
	@Override
	public boolean isDone() {
		return located;
	}
	
	@Override
	public void doReset() {
		located = false;
	}
	
	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		//return true if all 6 direction are free
		Direction[] dir6 = GoapAction.getSixDirection();
		
		for(int i=0; i<dir6.length; i++) {
			if(!rc.canPlantTree(dir6[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 0;
	}

}
