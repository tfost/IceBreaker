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
	
	// Graphics Information : how the entity should be drawn to the screen.
	protected int imgX;
	protected int imgY;
	protected Direction facing;
	
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
		this.facing = Direction.DOWN;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	// Soluble entities take damage from potions, no matter what!
	public boolean isSoluble() {
		return false;
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
	//which will allow frictionless tiles. The caller is responsible for making
	//sure the place being moved to can be moved into, otherwise raises an exception.
	public void move(int x, int y) {
		if (!l.getTile(x, y).canMoveInto()) {
			throw new IllegalStateException("Cannot move into " + x + ", " + y);
		}
		this.facing = getDirectionOfMovement(x - this.x, y - this.y);
		l.getTile(this.x, this.y).onExit();
		l.moveEntity(this.x, this.y, x, y);
		this.x = x;
		this.y = y;
		l.getTile(x, y).onMoveInto(this);
	}
	
	public Direction getDirectionOfMovement(int dx, int dy) {
		if (dx > 0) {
			return Direction.RIGHT;
		} else if (dx < 0) {
			return Direction.LEFT;
		} else if (dy < 0) {
			return Direction.UP;
		} else {
			return Direction.DOWN;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public Level getLevel() {
		return l;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
		if (this.hp > this.maxHp) {
			this.hp = this.maxHp;
		} else if (this.hp < 0) {
			this.hp = 0;
		}			 
	}
	
	//default paint method.
	//takes a graphics object, and an image containing the tileset.
	//also takes x/y coordinates of where the tile's top left corner shoudl be drawn.
	//uses default tile_size from Level class
	public void paint(Graphics g, BufferedImage img, Camera c) {
		int xTopLeft = c.translateXToScreen(x * Level.TILE_SIZE);
		int yTopLeft = c.translateYToScreen(y * Level.TILE_SIZE);
		//16 = width of images in picture file. TODO : change to constant.
		int dirOffset = this.getDirectionOffset();
		g.drawImage(img, xTopLeft, yTopLeft, xTopLeft + Level.TILE_SIZE, yTopLeft + Level.TILE_SIZE, 
									imgX, imgY + dirOffset, imgX + 16, imgY + 16 + dirOffset, null);
		g.setColor(Color.white);
		g.drawString(this.hp + "/" + this.maxHp, xTopLeft, yTopLeft);
	}
	
	// Returns the vertical offset of the proper image for the player in the game.
	protected int getDirectionOffset() {
		int dir = 0;
		switch(this.facing) {
			case UP:
				dir = 3;
				break;
			case DOWN:
				dir =  0;
				break;
			case LEFT:
				dir = 1;
				break;
			case RIGHT:
				dir = 2;	
				break;
		}
		return dir * 16; // size of tiles on img.		
	}
	
	public void setDirection(Direction dir) {
		this.facing = dir;
	}
	
	public Direction getDirection() {
		return this.facing;
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







