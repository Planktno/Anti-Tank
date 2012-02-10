package entities;

import org.newdawn.slick.geom.Vector2f;

public class Camera {

	//Attributes for general adaptability to screen size
	private int screenWidth, screenHeight; //size of the actual presented area
	private int frameWidth, frameHeight; //size of the in-game frames
	private float scale; //multiplication factor to get from the frame size to the screen size
	private Vector2f offset; //offset from the top left corner for centering the image in fullscreen
	
	//objects that can be focused on. only one at a time (others must be null)
	private World world;
	private Tank tank;
	private Projectile projectile;
	private Vector2f focusOffset; //The added offset to center on the focused object
	private float focusScale; //The multiplied scale to offer a good visibility of the focused object
	private boolean smoothFocusChange; //Changed focus should be done smoothly
	
	
	public Camera(int screenWidth, int screenHeight, int frameWidth, int frameHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		if(screenWidth/(float)frameWidth < screenHeight/(float)frameHeight) { //Scale maximizes either width or height, whatever is smaller
			scale = screenWidth/(float)frameWidth;
			offset = new Vector2f(0,(screenHeight-this.scale*frameHeight)/2.0f); //Half of the remaining height is the offset
		} else {
			scale = screenHeight/(float)frameHeight;
			offset = new Vector2f((screenWidth-this.scale*frameWidth)/2.0f,0); //Halt of the remaining width id the offset
		}
		
		world = null;
		tank = null;
		projectile = null;
		focusOffset = new Vector2f(0,0);
		focusScale = 1;
		smoothFocusChange = false;
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public int getFrameWidth() {
		return frameWidth;
	}
	
	public int getFrameHeight() {
		return frameHeight;
	}
	
	public float getScale() {
		return scale;
	}
	
	public Vector2f getOffset() {
		return offset;
	}
	
	public void setFocus(World world) {
		this.world = world;
		this.tank = null;
		this.projectile = null;
	}
	
	public void setFocus(Tank tank) {
		this.world = null;
		this.tank = tank;
		this.projectile = null;
	}
	
	public void setFocus(Projectile projectile) {
		this.world = null;
		this.tank = null;
		this.projectile = projectile;
	}
	
	public void resetFocus() {
		this.world = null;
		this.tank = null;
		this.projectile = null;
	}
	
	public Vector2f getRelPos(Vector2f pos) { //get the relative position on the screen (for GUI)
		return new Vector2f(pos.getX()*this.scale + this.offset.getX(), pos.getY()*this.scale + this.offset.getX());
	}
	
	public Vector2f getRelFocusPos(Vector2f pos) { //get the relative position on the screen relative to the current focus (for gameElements)
		if(this.smoothFocusChange) {
			//return relPos using focusOffset and focusScale as calculated by update
			return null;
		} else if(world != null) {
			//get world size
			//calculate scale to render entire world
			//return relPos
			return null;
		} else if(tank != null) {
			//get tank position
			//calculate offset
			//use default focusScale = 1
			//return relPos
			return null;
		} else if(projectile != null) {
			//get projectile position
			//calculate offset
			//use default focusScale = 1
			//return relPos
			return null;
		} else {
			return this.getRelPos(pos);
		}
	}
	
	public float getFocusScale() { //get the current 'game scale' (for gameElements)
		return focusScale;
	}
	
	public void setFocusScale(float focusScale) {
		this.focusScale = focusScale;
	}
	
	public void update(int delta) { //delta function to calculate the smooth focus change
		if (this.smoothFocusChange) {

		}
	}
}
