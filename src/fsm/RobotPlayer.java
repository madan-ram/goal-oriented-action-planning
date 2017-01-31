package fsm;

import agents.*;
import battlecode.common.*;
import goap.GoapAgent;

public strictfp class RobotPlayer {
    static RobotController rc;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
    	
    	switch (rc.getType()) {
        case ARCHON:
            GoapAgent archon = new ArchonAgent();
            archon.start(rc);
            break;
        case GARDENER:
        	GoapAgent farmGardner = new FarmGardenerAgent();
        	farmGardner.start(rc);
        	//GoapAgent botGardner = new BotGardenerAgent(rc);
            break;
        case SOLDIER:
        	GoapAgent soldier = new SoldierAgent();
        	soldier.start(rc);
            break;
        case LUMBERJACK:
        	GoapAgent defenderLumberjack = new DefenderLumberjackAgent();
        	defenderLumberjack.start(rc);
        	//GoapAgent chopperLumberjack = new ChopperLumberjackAgent(rc);
            break;
        case SCOUT:
        	GoapAgent scout = new ScoutAgent();
        	scout.start(rc);
        	
        	break;
        case TANK:
        	GoapAgent tank = new TankAgent();
        	tank.start(rc);
        	
        	break;
        default:
        	System.out.println("Error: Unknow bot type");
    	}
    }
}
