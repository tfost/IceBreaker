package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import interfaces.Direction;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;
import main.Level;
import tiles.Tile;

//allows support for frictionless tiles.
public class MovingState implements EntityState{

	private Entity p;
	private long enterTime;
	private Direction dir;
	private Queue<Point> path;
	public static final long DELAY = 200;
	
	
	public MovingState(Entity p, Direction dir) {
		this.p = p;
		path = new LinkedList<>();
		enterTime = System.currentTimeMillis();
		Tile current = p.getLevel().getTile(p.getX(), p.getY());
		//determine direction of motion.
		int dx = 0;
		int dy = 0;		
		switch (dir) {		
			case UP:
				dy--;
				break;
			case DOWN:
				dy++;
				break;
			case LEFT:
				dx--;
				break;
			case RIGHT:
				dx++;
				break;
		}
		int x = p.getX();
		int y = p.getY();
		Tile init = p.getLevel().getTile(x + dx, y + dy);
		Level level = p.getLevel();
		if (init.canMoveInto()) {
			if (level.getEntity(x + dx, y + dy) != null) {//there's something where we're tryign to move!
				if (level.getTile(x + dx + dx, y + dy + dy).canMoveInto() && level.getEntity(x + dx + dx, y + dy + dy) == null) {//next spot is empty
					level.getEntity(x + dx, y + dy).setState(new MovingState(level.getEntity(x + dx, y + dy), dir));
					path.add(new Point(x + dx, y + dy));
				}
			} else {//cell is unoccupied
				path.add(new Point(x + dx, y + dy));
			}
			boolean searching = !init.hasFriction();//only continue to search if there's no friction on the destination tile.
			int i = 2;
			while (searching) {
				int newDx = dx * i;
				int newDy = dy * i;
				Tile t = p.getLevel().getTile(x + newDx, y + newDy);
				if (t.canMoveInto()) {					
					if (level.getEntity(x + newDx, y + newDy) != null) {//there's something where we're tryign to move!
						if (level.getTile(x + newDx + newDx, y + newDy + newDy).canMoveInto() && level.getEntity(x + newDx + newDx, y + newDy + newDy) == null) {//next spot is empty
							level.getEntity(x + newDx, y + newDy).setState(new MovingState(level.getEntity(x + newDx, y + newDy), dir));
							path.add(new Point(x + newDx, y + newDy));
						}
					} else {//cell is unoccupied
						path.add(new Point(x + newDx, y + newDy));
					}				
				} else {
					searching = false;
				}
				searching &= !t.hasFriction(); //only continue if we can move into it and there is no friction				
				i++;
			}
			if (!path.isEmpty()) {
				Point point = path.remove();
				p.getLevel().moveEntity(p.getX(), p.getY(), point.x, point.y);
			}
			
		}
		
		
	}
	
	@Override
	public void update() {
		if (path.isEmpty()) {
			p.setState(new IdleState(p));
		} else {
			long currentTime = System.currentTimeMillis();
			if (currentTime - enterTime >= DELAY) {//time to move again!
				enterTime = currentTime;
				Point pt = path.remove();
				p.getLevel().moveEntity(p.getX(), p.getY(), pt.x, pt.y);
			}
		}
		
	}

	@Override
	public void paint(Graphics g) {
		p.paint(g);
		
	}

	@Override
	public void handleInput(KeyboardInput input) {		
		//don't do anything while moving.		
	}
}
