package entities;

import java.awt.Color;

import interfaces.Entity;
import main.Level;

public class MovableStone extends Entity {
	Color brown;
	public MovableStone(int x, int y, Level l) {
		super(x, y, l);
		brown = new Color(139,69,19);
		this.setImgX(64);
		this.setImgY(32);
	}
	
	
	
	public Color getColor() {
		return brown;
	}
	
	public String toString() {
		return "@";
	}
	
}
