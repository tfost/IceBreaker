package interfaces;

import display.Camera;

//A class that helps abstract out the rendering mechanism for game objects, by
//providing seperate rendering functions.
public abstract class GraphicsComponent {
	
	public Camera camera;
	public GraphicsComponent(Camera c) {
		this.camera = c;
	}
	
	public abstract void render();
}
