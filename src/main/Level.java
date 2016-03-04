package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import entities.MovableStone;
import entities.Player;
import interfaces.Direction;
import interfaces.Entity;
import tiles.Floor;
import tiles.PressOnlyOnceButton;
import tiles.SlipperyPressOnlyOnceButton;
import tiles.Tile;
import tiles.UnbreakingIce;
import tiles.Wall;
import tiles.WeightedButton;
import tiles.Button;

//stores data containing information about the level.
public class Level {
	/*
	 * 1 = wall. Player can't walk through here.
	 * 0 = ground
	 * 2 = starting point. character starts out here.
	 * 3 = button. If pushed more than once, door won't open.
	 * 4 = door. Can't go through until all buttons are in a valid state.
	 * 5 = ice
	 */
	public static int[][] testLevel = {
			{1, 1, 1, 1, 1, 1},
			{1, 2, 0, 3, 0, 4},
			{1, 1, 1, 1, 1, 1}
	};
	
	public static int[][] testLevel2 = {
			{1,3,1,1,1,1,1,1,1,1,1,1,1},
			{1,3,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,1,1,1,1,0,1,1,0,0,1},
			{1,0,1,0,3,0,0,3,0,3,1,0,1},
			{1,0,1,0,1,1,1,3,1,3,1,0,1},
			{1,0,1,0,0,3,0,3,3,3,1,0,1},
			{1,0,0,0,3,1,1,3,0,1,0,0,1},
			{1,2,0,0,3,3,3,3,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	public static int[][] testLevel3 = { //based off sootopolis 3rd level
			{1,1,1,1,1,3,1,1,1,1,1},
			{3,3,1,3,3,3,3,3,3,3,3},
			{3,3,3,3,3,3,1,3,3,1,3},
			{3,1,3,3,1,3,3,3,3,3,3},
			{3,3,3,3,3,3,3,3,1,3,3},
			{1,1,1,1,1,2,1,1,1,1,1}
			
	};
	
	public static int[][] testLevel4 = {
			{1,3,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,5,0,7,0,0,0,1},
			{1,0,0,0,0,0,5,0,0,0,0,0,1},
			{1,0,0,0,0,0,5,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,0,8,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,2,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	public static int[][] testLevel5 = {
			{1,1,1,3,1,1,1},
			{1,1,0,0,0,1,1},
			{1,7,7,0,7,7,1},
			{1,0,7,7,7,0,1},
			{1,7,0,0,0,7,1},
			{1,0,7,7,7,0,1},
			{1,0,0,0,0,0,1},
			{1,0,0,0,0,0,1},
			{1,0,0,2,0,0,1},
			{1,1,1,1,1,1,1}
	};
	
	public static final int TILE_SIZE = 32;	
	private Tile[][] data;
	private Point startingPoint;
	private int width;
	private int height;
	private Set<Button> buttons;
	private Entity[][] entities; //stores the different entities on the board
	private Set<Entity> nonPlayerEntities;
	
	/**
	 * constructs a new level based off the default test level.
	 */
	public Level() {
		this(testLevel4);
	}
	
	/**
	 * creates a new level based off of a given multidimensional array of tile data.
	 * @param data
	 */
	public Level(int[][] data) {
		this.buttons = new HashSet<>();
		this.nonPlayerEntities = new HashSet<>();
		this.data = new Tile[data.length][data[0].length];
		this.entities = new Entity[data.length][data[0].length];
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				Tile t;
				if (data[i][j] == 2) { //special instance where it's the starting point.
					t = new Floor();
					this.startingPoint = new Point(j, i);
					this.entities[i][j] = new Player(j, i, this);
				} 
				else if (data[i][j] == 1) {
					t = new Wall();
				} 
				else if (data[i][j] == 3) {
					t = new PressOnlyOnceButton();
					this.buttons.add((Button) t);
				}
				else if (data[i][j] == 5) {
					t = new UnbreakingIce();
				}
				else if (data[i][j] == 6) {
					t = new SlipperyPressOnlyOnceButton();
					this.buttons.add((Button) t);
				} else if (data[i][j] == 7) {
					t = new Floor();
					MovableStone stone = new MovableStone(j,i,this);
					this.entities[i][j] = stone;
					nonPlayerEntities.add(stone);
				} else if (data[i][j] == 8) {
					t = new WeightedButton();
					this.buttons.add((Button) t);
				}
				else {
					t = new Floor();
				}
				this.data[i][j] = t;
			}
		}
		this.width = this.data.length;
		this.height = this.data[0].length;
	}
	
	public Tile getTileInDirection(Player p, Direction dir) {
		int newX = p.getX();
		int newY = p.getY();		
		switch (dir) {		
			case UP:
				newY--;
				break;
			case DOWN:
				newY++;
				break;
			case LEFT:
				newX--;
				break;
			case RIGHT:
				newX++;
				break;
		}
		Tile t = this.getTile(newX, newY);
		return t;
	}
	
	
	public Point getStartingPointCoords() {
		return this.startingPoint;
	}
	
	/**
	 * returns the tile at position x, y, where 0,0 is the top left corner.
	 * @param x	x coordinate of the tile.
	 * @param y y coordinate of the tile
	 * @return null if x or y is outside the bounds of the level.
	 * @return the tile at x, y otherwise.
	 */
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= this.height || y >= this.width) {
			return null;//Internal implementation got backwards at some point.
		} else {
			return this.data[y][x];
		}
	}
	
	public Entity getEntity(int x, int y) {
		if (x < 0 || y < 0 || x >= this.height || y >= this.width) {
			return null;//Internal implementation got backwards at some point.
		} else {
			return this.entities[y][x];
		}
	}
	
	//paint each tile to the screen. 
	public void paint(Graphics g, BufferedImage img) {
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				Tile t = this.data[i][j];
				//g.setColor(t.getColor());
				t.paint(g, img, j, i);
				//g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				
			}
		}
		for (int i = 0; i < this.entities.length; i++) {
			for (int j = 0; j < this.entities[0].length; j++) {
				if (this.entities[i][j] != null) {
					this.entities[i][j].paint(g, img);
				}
				
			}
		}
			
		if (levelValid()) {
			g.setColor(Color.red);
			g.drawString("Level Complete!", 200, 300);
		}
	}
	
	//moves an entity from one spot to another.
	public void moveEntity(int x, int y, int destx, int desty) {
		Entity entity = this.getEntity(x, y);
		entity.move(destx, desty);
		this.entities[y][x] = null;
		this.entities[desty][destx] = entity;
	}
	
	public boolean levelValid() {
		for (Button b : this.buttons) {
			if (!b.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	public void reset() {
		for (Button b : this.buttons) {
			b.reset();
		}
	}
	
	public Set<Entity> getNonPlayerEntities() {
		return this.nonPlayerEntities;
	}
	
	public void printEntities() {
		for (int i = 0; i < entities.length; i++) {
			System.out.println(Arrays.toString(entities[i]));
		}
		System.out.println();
	}
	
}
