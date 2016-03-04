package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Level;

public class Tile {
	int imgX;
	int imgY;
	
	public Tile() {
		imgX = 0;
		imgY = 0;
	}
	
	public boolean canMoveInto() {
		return false;
	}
	
	public Color getColor() {
		return Color.MAGENTA;
	}
	
	public boolean hasFriction() {
		return true;
	}
	
	protected void setImgX(int imgX) {
		this.imgX = imgX;
	}
	
	protected void setImgY(int imgY) {
		this.imgY = imgY;
	}
	
	//default paint method.
	//takes a graphics object, and an image containing the tileset.
	//also takes x/y coordinates of where the tile's top left corner shoudl be drawn.
	//uses default tile_size from Level class
	public void paint(Graphics g, BufferedImage img, int x, int y) {
		g.drawImage(img,  x * Level.TILE_SIZE, y * Level.TILE_SIZE, x * Level.TILE_SIZE + Level.TILE_SIZE, y * Level.TILE_SIZE + Level.TILE_SIZE, imgX, imgY, imgX + 16, imgY + 16, null);
	}
	
	//Button Functions
	public void onMoveInto() {} //to be implemented by Button but maintain functionality here.
	public void onExit() {} //called after leaving a tile.
	public void reset() {}
}
