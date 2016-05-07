package entities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import interfaces.Entity;
import main.Level;
import tiles.Tile;

/**
 * The hunter behavior is a behavior that lets AI Characters hunt down
 * the player from across the map. Hunter mobs work alone, searching the
 * entire map for a path from themselves to the player. Should they get
 * within range of the player to attack, they will do so.
 * @author Tyler
 */
public class HunterBehavior implements AIBehavior {
	
	private Entity e;
	private Level l;
		
	public HunterBehavior(Entity e, Level l) {
		this.e = e;
		this.l = l;
	}
	
	@Override
	public void determineAction(int turnnum) {
		Player p = this.l.getPlayer();
		Stack<Point> path = pathfindToPoint(p.getX(), p.getY(), e.getX(), e.getY());
		if (path == null) {
			throw new IllegalStateException("No Path Generated: my location is " + this.e.getX() + ", " + this.e.getY());
		}
		Point pt = path.pop();
		
		// Move if able. Otherwise, attack player if able. Otherwise, do nothing.
		if (this.l.canMoveInto(pt.x, pt.y)) {
			this.e.move(pt.x, pt.y);
		} else if (p.getX() == pt.x && p.getY() == pt.y) {
			this.e.attack(p);			
		}
		this.e.setInTurn(false);
	}

	// pathfinds to the target x/y values (tx/y) from the source values (sx/y) using
	// the A* algorithm
	//Returns a stack representing the path the current character of this state
	//would need to take to get to a given target.
	//pre: target should be in the movement distance of the character, determined
	//via in the selectedState.
	private Stack<Point> pathfindToPoint(int tx, int ty, int sx, int sy) {
		PathNode start = new PathNode(new Point(sx, sy)); // starting square.
		List<PathNode> openList = new ArrayList<PathNode>(); //list of nodes to consider
		openList.add(start);
		List<PathNode> closedList = new ArrayList<PathNode>(); //nodes that have been checked.
		boolean targetFound = false; // whether or not we have added the target to the closed list.
		Point target = new Point(tx, ty);
		while (!targetFound) {
			PathNode current = openList.get(0);
			for (int i = 1; i < openList.size(); i++) {
				PathNode next = openList.get(i);
				
				if (next.f < current.f) { // this 
					current = next;
				}
			}
			closedList.add(current);
			openList.remove(current);
			
			Point next = new Point((int) current.getTileCoords().getX(), (int) current.getTileCoords().getY() - 1);			
			pathNodeHelper(current, next, target, openList, closedList);
			next = new Point((int) current.getTileCoords().getX(), (int) current.getTileCoords().getY() + 1);			
			pathNodeHelper(current, next, target, openList, closedList);
			next = new Point((int) current.getTileCoords().getX() - 1, (int) current.getTileCoords().getY());			
			pathNodeHelper(current, next, target, openList, closedList);
			next = new Point((int) current.getTileCoords().getX() + 1, (int) current.getTileCoords().getY());			
			pathNodeHelper(current, next, target, openList, closedList);
			if (containsPathNode(closedList, (int) target.getX(), (int) target.getY())) {
				targetFound = true;
			}
			//System.out.println(openList);
		}
		
		Stack<Point> path = new Stack<>();
		PathNode current = getPathNodeFromTile(closedList, target);
		path.push(current.getTileCoords());
		current = current.parent;
		
		while (current != null && current.parent != null) { // don't want start in final path.
			path.push(current.getTileCoords());
			current = current.parent;
		}
		return path;		
	}	
	
	//does the work of adding a node to either the open list or closed list, reordering things.
	private void pathNodeHelper(PathNode current, Point next, Point target, List<PathNode> openList, 
																	List<PathNode> closedList) {
		if (this.l.getTile(next.x, next.y).canMoveInto() && !closedList.contains(next)) {
			PathNode nextNode = new PathNode(current, next, (int) target.getX(), (int) target.getY());
			
			//if the open list doesn't contain a path to this point, add the current path to this point.
			if (!containsPathNode(openList, (int) nextNode.getTileCoords().getX(), (int) nextNode.getTileCoords().getY())) {
				openList.add(nextNode);					
			} else { // there is a pathnode to this tile. do comparisons for g scores.
					//if there's already a pathnode to this same tile, compare g scores from that 
					//parent with g scores for if we approached it from this tile.
				PathNode inList = getPathNodeFromTile(openList, nextNode.getTileCoords());
				if (inList.g > nextNode.g) { // 
					 inList.setParent(current);
					 inList.calculateF();
				}
			}
			
		}
	}	
	
	//Finds the pathnode corresponding to a given point. Throws
	// IllegalStateException if the tile is not found in the open list.
	private PathNode getPathNodeFromTile(List<PathNode> openList, Point t) {
		for (int i = 0; i < openList.size(); i++) {
			PathNode next = openList.get(i);
			if (next.getTileCoords().x == t.x && next.getTileCoords().y == t.y) {
				return next;
			}
		}
		throw new IllegalStateException("Tile not found in open list. Check References.");
	}
	
	//determines if a given list contains a pathnode to a given x/y position
	private boolean containsPathNode(List<PathNode> openList, int x, int y) {
		for (int i = 0; i < openList.size(); i++) {
			PathNode curr = openList.get(i);
			if (curr.getTileCoords().getX() == x && curr.getTileCoords().getY() == y) {
				return true; // we found it!
			}
		}
		return false;
	}
	
	private class PathNode {
		private PathNode parent;
		Point location;
		public int f; // total cost of paht
		public int g; //movement cost into this tile.
		public int h; // estimated movement cost to move from this to target
		int tx; // target x pos
		int ty; // target y pos
		
		public PathNode(Point location) { // for initial node
			this.parent = null;
			this.location = location;
			f = g = h = 0;
			
		}
		
		//takes a parent node, location of this tile, and target x/y values
		public PathNode(PathNode p, Point location, int tx, int ty) {
			this.tx = tx;
			this.ty = ty;
			this.parent = p;
			this.location = location;
			f = getMovementCost() + calculateH(tx, ty);
		}
		
		public Point getTileCoords() {
			return location;
		}
		
		public PathNode getParent() {
			return this.parent;
		}
		
		public void setParent(PathNode p) {
			this.parent = p;
		}
		
		public void calculateF() {
			f = getMovementCost() + calculateH(tx, ty);

		}
		
		public int getMovementCost() {
			if (parent == null) {
				return 0;
			} else {
				return 1 + parent.getMovementCost();
			}
		}
		
		public int calculateH(int targetX, int targetY) {
			int xDiff = (int) Math.abs(location.getX() - targetX);
			int yDiff = (int) Math.abs(location.getY() - targetY);
			return xDiff + yDiff;
		}
		
		public String toString() {
			return "" + location.getX() + "," + location.getY();
		}
		
	}

}
