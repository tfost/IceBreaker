package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Player;
import interfaces.Entity;
import interfaces.GameState;
import io.KeyboardInput;

public class GS_LevelState extends GameState{

	private Level level;
	private Entity player;
	private BufferedImage img;
	
	public GS_LevelState(KeyboardInput input) {
		super(input);
		this.level = new Level(); //DEBUG - use default level.
		Point p = this.level.getStartingPointCoords();
		this.player = level.getEntity(p.x, p.y);
		this.img = null;
		try {
			img = ImageIO.read(getClass().getResource("/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void update() {
		keyboard.poll();
		if (keyboard.keyDown(KeyEvent.VK_X)) {
			this.reset();
		}
		player.handleInput(keyboard);
		player.update();
		for (Entity e : level.getNonPlayerEntities()) {
			e.update();
		}
	}
	
	private void reset() {
		this.level.reset();
		this.player = new Player(this.level.getStartingPointCoords().x, this.level.getStartingPointCoords().y, this.level);

	}
	
	@Override
	public void paint(Graphics g) {
		this.level.paint(g, img);	
		this.player.paint(g);
	}

	@Override
	public GameState nextState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean inState() {
		// TODO Auto-generated method stub
		return true;
	}

}
