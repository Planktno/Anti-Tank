package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Tank {
	
	private Image img;
	private Vector2f pos;
	private float scale;
	private float rotation;
	private int height, width;
	
	private int health;
	private int weight;
	private int player;
	
	public Tank() {
		try {
			img = new Image("data/Tank.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pos = new Vector2f(400,274);
		scale = 1;
		rotation = 0;
		height = img.getHeight();
		width = img.getWidth();
		health = 100;
		weight = 100;
		player = 1;
	}
	
	public Tank(String imgName, Vector2f pos, int player) {
		
	}
	
	public void update(int delta) {
		
	}
	
	public void render(Camera camera) {
		Vector2f rel_pos = camera.getRelPos(pos);
		float rel_scale = camera.getScale();
		
		img.setRotation(rotation);
		img.draw(rel_pos.getX(), rel_pos.getY(), rel_scale*scale);
	}
	
	public Image getImage() {
		return img;
	}
	
	public Vector2f getPos() {
		return pos;
	}

}
