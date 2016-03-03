package entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

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
	public void paint(Graphics g) {
		p.paint(g);
		
	}

	@Override
	public void handleInput(KeyboardInput input) {		
		if (input.keyDownOnce(KeyEvent.VK_UP)) {
			p.setState(new MovingState(p,Direction.UP));
		} else if (input.keyDownOnce(KeyEvent.VK_DOWN)) {
			p.setState(new MovingState(p,Direction.DOWN));
		} else if (input.keyDownOnce(KeyEvent.VK_LEFT)) {
			p.setState(new MovingState(p,Direction.LEFT));
		} else if (input.keyDownOnce(KeyEvent.VK_RIGHT)) {
			p.setState(new MovingState(p,Direction.RIGHT));
		}
		
	}
	
}
