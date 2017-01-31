package fsm;
import battlecode.common.*;
import goap.GoapAgent;
import goap.GoapPlanner;
import god.DataProvider;

public abstract class FSMState {
	public abstract void play() throws GameActionException;
}
