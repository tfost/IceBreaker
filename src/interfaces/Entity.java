package interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import display.Camera;
import entities.IdleState;
import io.KeyboardInput;
import main.Level;

/**
 * An Entity is an object in the game that can move. Examples include
 * the Player, boxes, or rocks that can be pushed around. 
 * @author Tyler
 *
 */
public class Entity {
	// Spatial Information: Where the player should be located and drawn to the screen.
	protected int x;			//horizontal location of the player.
	protected int y;			//vertical location of the player
	protected boolean inturn;
	protected Level l;
	protected EntityState state;
	protected int imgX;
	protected int imgY;
	
	//Stat information
	protected int maxHp;
	protected int hp;
	protected int atk;
	protected int def;
	protected int range;
	protected String name;


	public Entity(int x, int y, Level l) {
		this.x = x;
		this.y = y;
		this.l = l;
		this.state = new IdleState(this);
		this.inturn = false;
		this.name = "DEFAULT";
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void handleInput(KeyboardInput input) {
		//state.handleInput(input);//default does nothing.
	}
	
	public void onTurnStart(int turnnum) {
		this.inturn = true;
	}
	
	public boolean inTurn() {return inturn;}
	
	public void setInTurn(boolean b) {
		this.inturn = b;
	}
	
	//TODO : move in a way that takes time, instead of instantaneously moving
	//which will allow frictionless tiles.
	public void move(int x, int y) {
		if (!l.getTile(x, y).canMoveInto()) {
			throw new IllegalStateException("It is forbidden to move into tile " + x + ", " + y);
		}
		l.getTile(this.x, this.y).onExit();
		l.moveEntity(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
		l.getTile(x, y).onMoveInto(this);
	}
	
	public Level getLevel() {
		return l;
	}
	
	//default paint method.
	//takes a graphics object, and an image containing the tileset.
	//also takes x/y coordinates of where the tile's top left corner shoudl be drawn.
	//uses default tile_size from Level class
	public void paint(Graphics g, BufferedImage img, Camera c) {
		int xTopLeft = c.translateXToScreen(x * Level.TILE_SIZE);
		int yTopLeft = c.translateYToScreen(y * Level.TILE_SIZE);
		//16 = width of images in picture file. TODO : change to constant.
		g.drawImage(img, xTopLeft, yTopLeft, xTopLeft + Level.TILE_SIZE, yTopLeft + Level.TILE_SIZE , imgX, imgY, imgX + 16, imgY + 16, null);
		g.setColor(Color.white);
		g.drawString(this.hp + "/" + this.maxHp, xTopLeft, yTopLeft);
	}
	
	public void defaultPaint(Graphics g, BufferedImage img, Camera c) {	}
	
	protected void setLevel(Level l) {
		this.l = l;
	}
	
	protected void setX(int x) {
		this.x = x;
	}
	
	protected void setY(int y) {
		this.y = y;
	}
	
	public void onAttack() {}
	
	public void attack(Entity other) {
		int dmg =  calculateDamage(this, other);
		other.hp -= dmg;
		if (other.hp < 0) {
			other.hp = 0;
		}
		System.out.println(this.name + " attacked " + other.name + " for " + dmg + " damage!");
		other.onAttack();
	}

	protected Color getColor() {
		return Color.GREEN;
	}
	
	public void setState(EntityState state) {
		this.state = state;
	}

	public void update() {
		this.state.update();		
	}
	
	protected void setImgX(int imgX) {
		this.imgX = imgX;
	}
	
	protected void setImgY(int imgY) {
		this.imgY = imgY;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public boolean isPlayer() {
		return false;
	}
	
	public static int calculateDamage(Entity attacker, Entity defender) {
		int dmg = attacker.atk - defender.def;
		if (dmg <= 0) {
			return 1;
		} else {
			return dmg;
		}
	}

	

}







