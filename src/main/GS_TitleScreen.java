package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import interfaces.GameState;
import io.KeyboardInput;

public class GS_TitleScreen extends GameState{

	private boolean buttonPressed;
	public GS_TitleScreen(KeyboardInput input) {
		super(input);
		this.buttonPressed = false;
	}
	
	@Override
	public void update() {
		this.keyboard.poll();
		if (this.keyboard.keyDownOnce(KeyEvent.VK_ENTER)) {
			this.buttonPressed = true;
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.drawString("Press Enter to start!", 100, 100);
		
	}

	@Override
	public GameState nextState() {
		return new GS_LevelState(this.keyboard);
	}

	@Override
	//wait at title screen until player types enter.
	public boolean inState() {
		if (buttonPressed) {
			this.buttonPressed = false;
			return false;
		} else {
			return true;
		}
	}
	

}
