package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import display.Camera;
import interfaces.Direction;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;
import main.Level;
import tiles.Tile;

//TODO - generate moves after each delay, so that we only push stones and the like immediately after we move into them
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
		if (p.getLevel().canMoveInto(x + dx, y + dy)) {
			path.add(new Point(x + dx, y + dy));
		} else {
			path = null;
		}
		/*Tile init = p.getLevel().getTile(x + dx, y + dy);
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
			
			
			if (!path.isEmpty()) {
				Point point = path.remove();
				p.getLevel().moveEntity(p.getX(), p.getY(), point.x, point.y);
			}
		}*/
	}
	
	@Override
	public void update() {  //TODO: have moving state move the graphic of the character slowly.
		if (path == null) { //no path was found - we couldn't move!
			p.setState(new IdleState(p));
		} else if (path.isEmpty()) {
			p.setState(new IdleState(p));
			p.setInTurn(false); // We have complted moving, and are now done with our turn!
		} else {
			Point pt = path.remove();
			p.move(pt.x, pt.y);
			
		}
		
	}

	@Override
	public void paint(Graphics g, BufferedImage img, Camera c) {
		p.defaultPaint(g, img, c);
		
	}

	@Override
	public void handleInput(KeyboardInput input) {		
		//don't do anything while moving.		
	}
}
