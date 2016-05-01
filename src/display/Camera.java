package display;

import entities.Player;
import main.GamePanel;
import main.Level;

public class Camera {
	int screenHeight; 
	int screenWidth;
	int x; //x and y offsets.
	int y;
	Player player; // player the camera will follow.

	public Camera(Player p){ // screen width, screen height, player
		screenHeight = GamePanel.WIDTH;
		screenWidth = GamePanel.HEIGHT;
		player = p;
		//x/y offsets
		x = 0;
		y = 0;
	}
	
	//Next two methods return an offset location based off the player's
	//current tile location.
	private int getX(){
		return player.getX() * Level.TILE_SIZE - (screenWidth / 2 ) + (Level.TILE_SIZE / 2); // center camera on player. 
	}
	
	private int getY(){
		return player.getY() * Level.TILE_SIZE - (screenHeight / 2) + (Level.TILE_SIZE / 2);
	}
	
	public int translateXToScreen(int ox){ // object x value
		// changes raw x coordinate value to screen x value. 
		return ox - getX(); // screen location = object location - camera location.
		
	}
	
	public int translateYToScreen(int oy){
		// same as 
		return oy - getY();
	}
}
