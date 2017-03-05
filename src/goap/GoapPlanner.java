package goap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import actions.LocateFreeSpaceAction;
import actions.PlantTreesAction;
import actions.WaterTreesAction;
import battlecode.common.*;

final class Node {
	public Node parent;
	public float runningCost;
	public HashMap<String, Object> state;
	public GoapAction action;

	public Node(Node parent, float runningCost, HashMap<String, Object> state, GoapAction action) {
		this.parent = parent;
		this.runningCost = runningCost;
		this.state = state;
		this.action = action;
	}
}

public class GoapPlanner {
	
	public Queue<GoapAction> plan(RobotController rc, ArrayList<GoapAction> actionList,
			HashMap<String, Object> worldState, ArrayList<HashMap<String, Object>> goals
			) throws GameActionException {
		
		// reset the actions so we can start fresh with them
		for(GoapAction a:actionList) {
			a.doReset();
		}
		
			
		// check what actions can run using their checkProceduralPrecondition
		ArrayList<GoapAction> usableActions = new ArrayList<GoapAction> ();
		for(GoapAction action : actionList) {
			if ( action.checkProceduralPreCondtion(rc) )
				usableActions.add(action);
		}
		
		
		// we now have all actions that can run, stored in usableActions
		// build up the tree and record the leaf nodes that provide a solution to the goals.
		ArrayList<Node> leaves = new ArrayList<Node>();
		
		// build graph
		Node start = new Node (null, 0, worldState, null);
		boolean success = buildGraph(start, leaves, usableActions, goals, 1000000.0f);
		
		if (!success) {
			// oh no, we didn't get a plan
			System.out.println("No plan found");
			return null;
		}
		
		Node cheapest = null;
		for(Node leaf:leaves) {
			if (cheapest == null)
				cheapest = leaf;
			else {
				if (leaf.runningCost < cheapest.runningCost)
					cheapest = leaf;
			}
		}
		
		// get its node and work back through the parents
		Stack<GoapAction> result = new Stack<GoapAction>();
		Node n = cheapest;
		while (n != null) {
			if (n.action != null) {
				result.push(n.action);
			}
			n = n.parent;
		}
		// we now have this action list in correct order

		Queue<GoapAction> queue = new LinkedList<GoapAction> ();
		for(GoapAction a:result) {
			queue.add(a);
		}

		// hooray we have a plan!
		return queue;
	}
	
	private boolean inState(HashMap<String, Object> a, HashMap<String, Object> b) {
		for(String key: a.keySet()) {
			if(b.get(key) == null || b.get(key) != a.get(key)) {
				return false;
			}
		}
		return true;
	}
	
	private HashMap<String, Object> populateState(HashMap<String, Object> currentState, HashMap<String, Object> stateChange) {
		HashMap<String, Object> state = new HashMap<String, Object>();
		
		//copy current state to new state
		for(String key:currentState.keySet()) {
			state.put(key, currentState.get(key));
		}
		
		//Add key with in stateChange to state
		for(String key:stateChange.keySet()) {
			state.put(key, stateChange.get(key));
		}
		
		return state;
	}
	
	private ArrayList<GoapAction> actionSubset(ArrayList<GoapAction> actions, GoapAction removeMe) {
		ArrayList<GoapAction> subset = new ArrayList<GoapAction> ();
		for(GoapAction a:actions) {
			if (!a.equals(removeMe))
				subset.add(a);
		}
		return subset;
	}
	
	private boolean buildGraph(Node parent, ArrayList<Node> leaves, ArrayList<GoapAction> usableActions, ArrayList<HashMap<String, Object>> goals,float leastcost)
	{
		boolean foundOne = false;
		
		// go through each action available at this node and see if we can use it here
		for(GoapAction action: usableActions) {
			// if the parent state has the conditions for this action's preconditions, we can use it here
			if ( inState(action.getPreConditions(), parent.state) ) {

				// apply the action's effects to the parent state
				HashMap<String, Object> currentState = populateState(parent.state, action.getEffects());
				
				Node node = new Node(parent, parent.runningCost+action.getCost(), currentState, action);
				
				for(HashMap<String, Object> goal:goals) {
					if(inState(goal, currentState)) {
						leaves.add(node);
						foundOne = true;
						//found at least one goal then we note down the leaf return
						break;
					}
				}
				//explore further if current cost is less than or equals to least cost found so far.
				if(!foundOne && parent.runningCost+action.getCost() <= leastcost) {
					// not at a solution yet, so test all the remaining actions and branch out the tree
					ArrayList<GoapAction> subset = actionSubset(usableActions, action);
					
					boolean found = buildGraph(node, leaves, subset, goals, leastcost);
					if (found)
						foundOne = true;
				}
			}
		}
		return foundOne;
	}
	
}
