package main;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Stack;

import javax.swing.JFrame;
import io.KeyboardInput;
import interfaces.GameState;

public class GamePanel extends JPanel{
	
	public static final int WIDTH = 900;
	public static final int HEIGHT = 900;
	
	public KeyboardInput keyboard;	
	public GamePanel panel;
	public Stack<GameState> stateStack;
	
	public GamePanel() {
		this.keyboard = new KeyboardInput();
		this.addKeyListener(keyboard);
		this.stateStack = new Stack<>();
		
		//push initial state.
		this.stateStack.push(new GS_TitleScreen(keyboard));
		JFrame frame = new JFrame("Prototype");
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension d = new Dimension(WIDTH, HEIGHT);		
		frame.setSize(d);
		frame.setResizable(false);
		frame.setVisible(true); // set frame visible and render first pass.
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(this.keyboard);
		frame.getContentPane().setBackground(new Color(47, 40, 58));
		Graphics2D g2d = (Graphics2D) this.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		this.setOpaque(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		this.stateStack.peek().paint(g);
		//insert draw functions here.
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Loading");
		GamePanel game = new GamePanel();
		while (!game.stateStack.isEmpty()) {
			if ((game.stateStack.peek().inState())) {
				game.stateStack.peek().update();
				game.repaint();
				Thread.sleep(15);
			} else {
				if (game.stateStack.peek().nextState() == null) {
					game.stateStack.pop(); // done with this state. do the one beneath it now.
				} else {
					game.stateStack.push(game.stateStack.peek().nextState());
				}
			}
		}
	}
	
	
}
