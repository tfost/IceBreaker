package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import display.Camera;
import entities.Player;
import generators.WorldBuilder;
import interfaces.Entity;
import interfaces.GameState;
import io.KeyboardInput;

public class GS_LevelState extends GameState{

	private Level level;
	private Entity player;
	private BufferedImage img;
	private Set<Entity> nonPlayerEntities;
	private Camera c;
	private int turnNum;
	
	// Creates a levelstate.
	public GS_LevelState(KeyboardInput input) {
		super(input);
		WorldBuilder builder = new WorldBuilder();
		builder.populate();
		//this.level = new Level(builder.toCharArray()); 
		//DEBUG:
		this.level = new Level();
		Point p = this.level.getStartingPointCoords();
		this.player = level.getEntity(p.x, p.y);
		c = new Camera((Player) this.player);
		this.turnNum = 1;
		
		this.nonPlayerEntities = level.getNonPlayerEntities();
		this.img = null;
		try {
			img = ImageIO.read(getClass().getResource("/tileset.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Basic level flow:
	 * 		Increment turn counter
	 *		Wait for player to determine their action
	 *		execute player action
	 *		go through loop of all characters, to determine what their action will be
	 *			should enemy attack, do one after another.
	 *			should enemy move, decide where to move, move entity in world, but then display entity moving over time.
	 */
	
	@Override
	public void update() {
		
		keyboard.poll();
		if (player.inTurn()) {
			player.handleInput(keyboard);
			player.update();
			//If the player just completed their turn.
			if (!player.inTurn()) {
				for (Entity e : this.nonPlayerEntities) {
					e.onTurnStart(this.turnNum);
				}
			}
		} else { //at least 1 entity in the nonplayerentities is in their turn.
			for (Entity e : this.nonPlayerEntities) {
				e.update(); 
			}
			if (!entitiesInTurn()) { //time for the player to go!
				this.turnNum++;
				player.onTurnStart(this.turnNum);
			}
		}
	}
	
	private boolean entitiesInTurn() {
		for (Entity e : this.nonPlayerEntities) {
			if (e.inTurn()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		this.level.paint(g, img, c);	
		this.player.paint(g, img, c);
		for (Entity e : this.nonPlayerEntities) {
			e.paint(g, img, c);
		}
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
