package actions;

import battlecode.common.*;
import goap.GoapAction;
import god.DataProvider;
import common.Utils;

public class MoveRandomAction extends GoapAction {
	
	boolean moved = false;
	
	public MoveRandomAction(RobotController rc, DataProvider dataProvider) {
		super(rc, dataProvider);

		//default action to be performed when there is no plan
		//move randomly if hasLocatedFreeSpace is false
		addPreCondition("hasLocatedFreeSpace", false);
		
		//Effect performed goal
		addEffect("moveRandom", true);
	}
	
	static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {
		
        // First, try intended direction
        if (!rc.hasMoved() && rc.canMove(rc.getLocation().add(dir, rc.getType().strideRadius))) {
            rc.move(rc.getLocation().add(dir, rc.getType().strideRadius));
            return true;
        }

        // Now try a bunch of similar angles
        //boolean moved = rc.hasMoved();
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(!rc.hasMoved() && rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(! rc.hasMoved() && rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }
	
	static Direction moveRandom() {
		int d = Utils.nextRandomInt(0, 7);
		return dirs_8s[d];
	}
	
	static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove( dir, 20, 3);
    }
	
	
	@Override
	public void doReset() {
		moved = false;
	}

	@Override
	public float getCost() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return moved;
	}
	
	@Override
	public boolean perform(RobotController rc) throws GameActionException {
		if(!rc.hasMoved()) {
			Direction dir = moveRandom();
			tryMove(dir);
			
			//try to move if failed due to blocking and not because of failed hasMoved,  don't repeat same action
			moved = true;
		} else {
			moved = false;
		}
		
		//Failed action should not create complete new plan
		return true;
	}

	@Override
	public boolean checkProceduralPreCondtion(RobotController rc) {
		if(!rc.hasMoved()) {
			return true;
		}
		return false;
	}

}

