package entities;

import java.awt.Point;
import java.util.Random;

import interfaces.Direction;
import interfaces.Entity;
import main.Level;

public class TestBehavior implements AIBehavior{
	private Random r;
	private Level l;
	private Entity entity;
	
	public TestBehavior(Level l, Entity e) {
		this.l = l;
		this.entity = e;
		r = new Random();
		// TODO Auto-generated constructor stub
	}
	
	public void determineAction(int turnnum) {
		if (turnnum % 2 == 0) {
			int dir = r.nextInt(4);
			Direction toMoveInto;
			switch (dir) {
			case 0:
				toMoveInto = Direction.UP;
				break;
			case 1:
				toMoveInto = Direction.DOWN;
				break;
			case 2:
				toMoveInto = Direction.LEFT;
				break;
			default:
				toMoveInto = Direction.RIGHT;
				break;					
			}
			
			Point p = l.getTileCoordsInDirection(entity, toMoveInto);
			if (l.canMoveInto(p.x, p.y)) {
				entity.move(p.x, p.y);
			}
		}
		this.entity.setInTurn(false);//regardless of what we've done, we're now finished with our turn.
	}
	
}
