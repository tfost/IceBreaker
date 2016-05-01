package entities;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.print.attribute.standard.PagesPerMinute;

import display.Camera;
import interfaces.Direction;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;

public class IdleState implements EntityState{
	
	Entity p;
	
	public IdleState(Entity entity) {
		this.p = entity;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Graphics g, BufferedImage img, Camera c) {
		p.paint(g, img, c);
		
	}

	private void determineMovementAction(Direction dir) {
		Point pt = p.getLevel().getTileCoordsInDirection(p, dir);
		if (p.getLevel().getEntity(pt.x, pt.y) == null) {
			p.setState(new MovingState(p, dir));
		} else {  // There is an entity there! Attack it!
			//System.out.println("Attacking something!");
			Entity e = p.getLevel().getEntity(pt.x, pt.y);
			p.setState(new AttackingState((Player) p, e));
		}
	}
	@Override
	public void handleInput(KeyboardInput input) {	
		if (input.keyDownOnce(KeyEvent.VK_UP) || input.keyDownOnce(KeyEvent.VK_W)) {
			determineMovementAction(Direction.UP);
		} else if (input.keyDownOnce(KeyEvent.VK_DOWN)|| input.keyDownOnce(KeyEvent.VK_S)) {
			determineMovementAction(Direction.DOWN);
		} else if (input.keyDownOnce(KeyEvent.VK_LEFT)|| input.keyDownOnce(KeyEvent.VK_A)) {
			determineMovementAction(Direction.LEFT);
		} else if (input.keyDownOnce(KeyEvent.VK_RIGHT)|| input.keyDownOnce(KeyEvent.VK_D)) {
			determineMovementAction(Direction.RIGHT);
		}
		
		
	}
	
}
