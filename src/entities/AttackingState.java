package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import display.Camera;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;

public class AttackingState implements EntityState{
	
	private Player p;
	private Entity defender;
	
	public AttackingState(Player p, Entity defender) {
		this.p = p;
		this.defender = defender;
	}
	
	public void update() {
		p.attack(defender);
		p.setInTurn(false);
		p.setState(new IdleState(p));
	}

	@Override
	public void paint(Graphics g, BufferedImage img, Camera c) {
		p.defaultPaint(g, img, c);
		
	}

	@Override
	public void handleInput(KeyboardInput input) {
			
	}
}
