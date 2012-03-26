package entities;

import org.newdawn.slick.geom.Vector2f;

public class Camera {

	//Attributes for general adaptability to screen size
	private int screenWidth, screenHeight; //size of the actual presented area (Window or full screen)
	private int frameWidth, frameHeight; //size of the in-game frames
	private float scale; //multiplication factor to get from the frame size to the screen size
	private Vector2f offset; //offset from the top left corner for centering the image in fullscreen
	
	//objects that can be focused on. only one at a time (others must be null)
	private World world;
	private Tank tank;
	private Projectile projectile;
	private Vector2f focusOffset; //The added offset (to int offset) to center on the focused object
	private float focusScale; //The multiplied scale to offer a good visibility of the focused object
	
	private boolean smoothFocusChange; //Changed focus should be done smoothly
	private static float smoothVelocity = 0.0001f;
	private Vector2f startingDistance;
	private Vector2f deltaDistance;
	
	public Camera(int frameWidth, int frameHeight, int screenWidth, int screenHeight) {
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
		startingDistance = new Vector2f(0,0);
		deltaDistance = new Vector2f(0,0);
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
		//get world size
		int wH = world.getImage().getHeight();
		int wW = world.getImage().getWidth();
		
		if(this.tank != null) {
			Vector2f worldcenter = new Vector2f(wW/2, wH/2);
			startingDistance = new Vector2f(worldcenter.x - tank.getPos().x, worldcenter.y - tank.getPos().y);
			deltaDistance = startingDistance.copy();
		} else if(this.projectile != null) {
			Vector2f worldcenter = new Vector2f(wW/2, wH/2);
			startingDistance = new Vector2f(worldcenter.x - projectile.getPos().x, worldcenter.y - projectile.getPos().y);
			deltaDistance = startingDistance.copy();
		} else { //World
			startingDistance = new Vector2f(0,0);
			deltaDistance = startingDistance.copy();
		}
		
		this.world = world;
		this.tank = null;
		this.projectile = null;
	}
	
	public void setFocus(Tank tank) {
		if(this.tank != null) {
			startingDistance = new Vector2f(this.tank.getPos().x - tank.getPos().x, this.tank.getPos().y -tank.getPos().y);
			deltaDistance = startingDistance.copy();
		} else if(this.projectile != null) {
			startingDistance = new Vector2f(this.projectile.getPos().x - tank.getPos().x, this.projectile.getPos().y -tank.getPos().y);
			deltaDistance = startingDistance.copy();
		} else { //World
			Vector2f worldcenter = new Vector2f(world.getImage().getWidth()/2, world.getImage().getHeight()/2);
			startingDistance = new Vector2f(worldcenter.x - tank.getPos().x, worldcenter.y -tank.getPos().y);
			deltaDistance = startingDistance.copy();
		}
		focusScale = 1;
		this.tank = tank;
		this.projectile = null;
	}
	
	public void setFocus(Projectile projectile) {
		if(this.tank != null) {
			startingDistance = new Vector2f(this.tank.getPos().x - projectile.getPos().x, this.tank.getPos().y -projectile.getPos().y);
			deltaDistance = startingDistance.copy();
		} else if(this.projectile != null) {
			startingDistance = new Vector2f(this.projectile.getPos().x - projectile.getPos().x, this.projectile.getPos().y -projectile.getPos().y);
			deltaDistance = startingDistance.copy();
		} else { //World
			Vector2f worldcenter = new Vector2f(world.getImage().getWidth()/2, world.getImage().getHeight()/2);
			startingDistance = new Vector2f(worldcenter.x - projectile.getPos().x, worldcenter.y -projectile.getPos().y);
			deltaDistance = startingDistance.copy();
		}
		focusScale = 1;
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
		return new Vector2f((pos.getX() - focusOffset.getX())*scale*focusScale + offset.getX(), (pos.getY() - focusOffset.getY())*scale*focusScale + offset.getY());
	}
	
	public float getMultipliedScale() { //get the current 'game scale' (for gameElements) CHANGE THE NAME
		return focusScale * scale;
	}
	
	public float getFocusScale() {
		return focusScale;
	}
	
	public void setFocusScale(float focusScale) {
		this.focusScale = focusScale;
	}
	
	public void setSmooth(boolean smooth) {
		this.smoothFocusChange = smooth;
	}
	
	public void update(int delta) { //delta function to calculate the smooth focus change
		if(this.smoothFocusChange) {
			//return relPos using focusOffset and focusScale as calculated by update
			if(tank != null) {
				Vector2f tpos = tank.getPos();
				//deltaDistance = deltaDistance.sub(startingDistance.scale(delta*smoothVelocity));
				deltaDistance = deltaDistance.scale((float)Math.pow(0.999, delta));
				focusScale = 1-((startingDistance.length())/(float)(2*world.getImage().getWidth()))*(float)Math.sin(Math.PI*deltaDistance.length()/startingDistance.length());
				focusOffset = new Vector2f((tpos.getX()+deltaDistance.x+tank.getImage().getWidth()/2-frameWidth/2)*focusScale, (tpos.getY()+deltaDistance.y-frameHeight/2)*focusScale);
			} else if(projectile != null) {
				Vector2f ppos = projectile.getPos();
				//deltaDistance = deltaDistance.sub(startingDistance.scale(delta*smoothVelocity));
				deltaDistance = deltaDistance.scale((float)Math.pow(0.999, delta));
				focusScale = 1-((startingDistance.length())/(float)(2*world.getImage().getWidth()))*(float)Math.sin(Math.PI*deltaDistance.length()/startingDistance.length());
				focusOffset = new Vector2f((ppos.getX()+deltaDistance.x+projectile.getImage().getWidth()/2-frameWidth/2)*focusScale, (ppos.getY()+deltaDistance.y-frameHeight/2)*focusScale);
			} else { //World
				//get world size
				int wH = world.getImage().getHeight();
				int wW = world.getImage().getWidth();
				
				Vector2f wpos = new Vector2f(wW/2, wH/2);
				//deltaDistance = deltaDistance.sub(startingDistance.scale(delta*smoothVelocity));
				deltaDistance = deltaDistance.scale((float)Math.pow(0.999, delta));
				
				//calculate scale to render entire world
				float targetFocusScale;
				if(frameHeight/(float)wH >= frameWidth/(float)wW) {
					targetFocusScale = frameHeight/(float)wH;
				} else {
					targetFocusScale = frameWidth/(float)wW;
				}
				
				focusScale = targetFocusScale/(1+deltaDistance.length());
				focusOffset = deltaDistance;
			}
		}
//		} else if(tank != null) {
//			//get tank position
//			Vector2f tpos = tank.getPos();
//			//calculate offset
//			focusOffset = new Vector2f((tpos.getX()+tank.getImage().getWidth()/2-frameWidth/2)*focusScale, (tpos.getY()-frameHeight/2)*focusScale);
//		} else if(projectile != null) {
//			//get tank position
//			Vector2f ppos = projectile.getPos();
//			//calculate offset
//			focusOffset = new Vector2f((ppos.getX()-frameWidth/2)*focusScale, (ppos.getY()-frameHeight/2)*focusScale);
//		} else {
//			
//		}
		
	}
}
