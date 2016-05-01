package tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import display.Camera;
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
	public void paint(Graphics g, BufferedImage img, int x, int y ,Camera c) {
		int xTopLeft = c.translateXToScreen(x * Level.TILE_SIZE);
		int yTopLeft = c.translateYToScreen(y * Level.TILE_SIZE);
		//16 = width of images in picture file. TODO : change to constant.
		g.drawImage(img, xTopLeft, yTopLeft, xTopLeft + Level.TILE_SIZE, yTopLeft + Level.TILE_SIZE , imgX, imgY, imgX + 16, imgY + 16, null);
	}
	
	//Interactive Tile methods.
	public void onMoveInto() {} //to be implemented by Button but maintain functionality here.
	public void onExit() {}

}
