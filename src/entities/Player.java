package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import display.Camera;
import interfaces.Direction;
import interfaces.Entity;
import interfaces.EntityState;
import io.KeyboardInput;
import main.GamePanel;
import main.Level;
import tiles.Tile;

public class Player extends Entity {
	///private int x;			//horizontal location of the player.
	//private int y;			//vertical location of the player
	//private Level l;
	//private EntityState state;
	
	private boolean inlevel;
	private boolean inturn;
		
	public Player(int x, int y, Level l) {
		super(x, y, l);
		this.state = new IdleState(this);
		this.setImgX(0);
		this.setImgY(32);
		this.inturn = true;
		this.maxHp = this.hp = 20;
		this.atk = 3;
		this.def = 2;
		this.name = "Player";
		this.inlevel = true;
	}
	
	public void handleInput(KeyboardInput input) {
		state.handleInput(input);
	}
	
	public Level getLevel() {
		return l;
	}
	
	protected Color getColor() {
		return Color.blue;
	}
	
	public void setState(EntityState state) {
		this.state = state;
	}

	public boolean isPlayer() {
		return true;
	}
	
	public void update() {
		this.state.update();
	}
	
	
	public void onTurnStart(int turnnum) {
		super.onTurnStart(turnnum);
	}
	
	public void paint(Graphics g, BufferedImage img, Camera c) {
		this.state.paint(g, img, c);
	}
	
	public boolean isInlevel() {
		return inlevel;
	}

	public void setInlevel(boolean inlevel) {
		this.inlevel = inlevel;
	}

	public void defaultPaint(Graphics g, BufferedImage img, Camera c) {
		g.drawImage(img, GamePanel.WIDTH / 2 - Level.TILE_SIZE / 2, GamePanel.HEIGHT / 2 - Level.TILE_SIZE / 2, 
					GamePanel.WIDTH / 2 + Level.TILE_SIZE / 2, GamePanel.HEIGHT / 2 + Level.TILE_SIZE / 2, 
					imgX, imgY, imgX + 16, imgY + 16, null);
		g.setColor(Color.white);
		g.drawString(this.hp + "/" + this.maxHp, GamePanel.WIDTH / 2 - Level.TILE_SIZE / 2, GamePanel.HEIGHT / 2 - Level.TILE_SIZE / 2);

	}
	
	public String toString() {
		return "X";
	}

	public void resetForNewLevel(Level level, int x, int y) {
		this.l = level;
		this.x = x;
		this.y = y;
		this.inturn = true;
		this.inlevel = true;
		this.setState(new IdleState(this));
	}
	
}
