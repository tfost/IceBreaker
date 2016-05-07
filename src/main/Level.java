package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import display.Camera;
import entities.MovableStone;
import entities.Player;
import entities.Slime;
import generators.WorldBuilder;
import interfaces.Direction;
import interfaces.Entity;
import tiles.Floor;
import tiles.Staircase;
import tiles.Tile;
import tiles.Wall;

//stores data containing information about the level.
public class Level {
	
	public static final int TILE_SIZE = 64;	
	private Tile[][] data;
	private Point startingPoint;
	private int width;
	private int height;
	private Entity[][] entities; //stores the different entities on the board
	private Set<Entity> nonPlayerEntities;
	private Player player;
	
	/**
	 * constructs a new level based off the default test level.
	 */
	/*public Level() {
		this(testLevel4);
	}*/
	
	
	public Level(char[][] data) {
		this(data, null);
	}
	
	/**
	 * Constructs a new Level using an array of data and a given player.
	 * @param data		A character array of data.
	 * @param player	A Player from a previous level. If player is null, one will be created.
	 * 					Either way, the player will be reinitialized to be at the level's starting coords.
	 */
	public Level(char[][] data, Player player) {
		this.nonPlayerEntities = new HashSet<>();
		this.data = new Tile[data.length][data[0].length];
		this.entities = new Entity[data.length][data[0].length];
		int monsSpawned = 0;
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				Tile t;
				if (data[i][j] == 'E') { //special instance where it's the starting point.
					t = new Floor();
					this.startingPoint = new Point(j, i);
					if (player == null) {
						this.entities[i][j] = new Player(j, i, this);
						this.player = (Player) this.entities[i][j];
					} else {
						this.entities[i][j] = player;
					}
				} 
				else if (data[i][j] == 'X') {
					t = new Staircase();
				}
				else if (data[i][j] == '@') {
					t = new Wall();
				} else if (data[i][j] == 'M') {  // monster spawning tile
					t = new Floor();
					this.entities[i][j] = new Slime(j, i, this);
					this.nonPlayerEntities.add(this.entities[i][j]);
					monsSpawned++;
				}
				else {
					t = new Floor();
				}
				this.data[i][j] = t;
			}
		}
		System.out.println(monsSpawned + " Monsters Spawned");
		this.width = this.data.length;
		this.height = this.data[0].length;
	}

	public Tile getTileInDirection(Entity p, Direction dir) {
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
	
	public Point getTileCoordsInDirection(Entity p, Direction dir) {
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
		return new Point(newX, newY);
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
	public void paint(Graphics g, BufferedImage img, Camera c) {
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				Tile t = this.data[i][j];
				//g.setColor(t.getColor());
				t.paint(g, img, j, i, c);
				//g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				
			}
		}
		for (int i = 0; i < this.entities.length; i++) {
			for (int j = 0; j < this.entities[0].length; j++) {
				if (this.entities[i][j] != null) {
					this.entities[i][j].defaultPaint(g, img, c);
				}				
			}
		}		
	}
	
	public void removeEntity(int x, int y) {
		this.entities[y][x] = null;
	}
	
	public boolean canMoveInto(int x, int y) {
		return this.data[y][x].canMoveInto() && this.entities[y][x] == null;
	}
	
	//moves an entity from one spot to another.
	public void moveEntity(int x, int y, int destx, int desty) {
		Entity entity = this.getEntity(x, y);
		this.entities[y][x] = null;
		this.entities[desty][destx] = entity;
	}
	
	public Set<Entity> getNonPlayerEntities() {
		return this.nonPlayerEntities;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void printEntities() {
		for (int i = 0; i < entities.length; i++) {
			System.out.println(Arrays.toString(entities[i]));
		}
		System.out.println();
	}
	
}
