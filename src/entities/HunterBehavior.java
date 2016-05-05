package entities;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

import interfaces.Entity;
import main.Level;

public class HunterBehavior implements AIBehavior {
	
	private Entity e;
	private Level l;
	
	public HunterBehavior(Entity e, Level l) {
		this.e = e;
		this.l = l;
	}
	
	@Override
	public void determineAction(int turnnum) {
		/*
		 * Find a path to the player, disregarding if other units are in the way
		 * if a unit is in the way of the next immediate move, wait.
		 * if a unit is in th eway and is the player, attack the player!
		 */
		Player p = this.l.getPlayer();
		Queue<Point> path = pathFindToPoint(p.getX(), p.getY(), e.getX(), e.getY());
	}

	//pathfinds to the target x/y values (tx/y) from the source values (sx/y)
	private Queue<Point> pathFindToPoint(int tx, int ty, int sx, int sy) {
		Queue<Point> path = new LinkedList<>();
		
		
		return null;
	}

}
