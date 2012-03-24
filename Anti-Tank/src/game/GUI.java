package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Dimension;
import java.awt.Toolkit;

import states.GameState;
import entities.Camera;
import entities.Player;
import entities.Tank;
import entities.Weapon;
import entities.World;
public class GUI {
	
	Image sprites = ResourceManager.getInstance().getImage("SPRITES_GUI");
	
	
	Camera camera;
	
	GameState gs;
	World world;
	Player[] players;
	
	Integer c1, c2;
	
	Image[] Digit = new Image[] {
	sprites.getSubImage(253, 107, 15, 18),
	sprites.getSubImage(270, 107, 15, 18),
	sprites.getSubImage(288, 107, 15, 18),
	sprites.getSubImage(305, 107, 15, 18),
	sprites.getSubImage(323, 107, 15, 18),
	sprites.getSubImage(340, 107, 15, 18),
	sprites.getSubImage(358, 107, 15, 18),
	sprites.getSubImage(375, 107, 15, 18),
	sprites.getSubImage(393, 107, 15, 18),
	sprites.getSubImage(410, 107, 15, 18)};
	
	public GUI() {
		
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public void setGameState(GameState gs) {
		this.gs = gs;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	private void drawTwoDigitNumber(int n, float x, float y, float scale){
		int c1=n/10, c2=n%10;
		//if (c1!=0) {
			Digit[c1].draw(x,y,scale);
			Digit[c2].draw(x+12*scale,y,scale);
		//}
		/*else {
			Digit[c2].draw(x+6*scale,y,scale);
		}*/
	}
	
	private void formatTime(long time, float x, float y, float scale){
		int n1=(int)time/60;
		int n2=(int)time%60;
		drawTwoDigitNumber(n1, x, y, scale);
		drawTwoDigitNumber(n2, x+40*scale, y, scale);
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics gr, GameState gs) {		
		//********* The Player Section ************
		//moved from wind, needed it here
		float scale = camera.getScale();
		
		//get controls background
		Image backFrame = sprites.getSubImage(0, 247, 152, 470-247);
		
		//get screen dimensions
		float controlX, screenX, controlY, screenY;
		controlX = backFrame.getWidth()*scale;
		controlY = backFrame.getHeight()*scale;
		if (!GunsAndHats.isFULLSCREEN())
		{
			screenX = camera.getScreenWidth();
			screenY = camera.getScreenHeight();
		}
		else
		{
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension dim = toolkit.getScreenSize();
			screenX = dim.width;
			screenY = dim.height;
		}
		
		//initialize player and current player images
		Image[] Players = new Image[] {
			sprites.getSubImage(252, 0, 126, 40),
			sprites.getSubImage(379, 0, 126, 40),
			sprites.getSubImage(506, 0, 126, 40),
			sprites.getSubImage(633, 0, 126, 40)};
				
		Image[] currPlayer = new Image[] {
			sprites.getSubImage(252, 43, 125, 40),
			sprites.getSubImage(379, 43, 125, 40),
			sprites.getSubImage(506, 43, 125, 40),
			sprites.getSubImage(633, 43, 125, 40)};
			
		//initialize tank numbers
		Image[] tankNos = new Image[] {
			sprites.getSubImage(253, 85, 14, 18),
			sprites.getSubImage(270, 85, 14, 18),
			sprites.getSubImage(288, 85, 14, 18),
			sprites.getSubImage(305, 85, 14, 18)};
			
		//initialize current tank of each team
		Image[] currTank = new Image[]{
			tankNos[0].copy(),
			tankNos[0].copy(),
			tankNos[0].copy(),
			tankNos[0].copy()};
			
		//set x offset of player displays
		Integer[] xOffset = new Integer[]{
			(int)(10*scale),
			(int)((10+Players[0].getWidth())*scale),
			(int)((10+2*Players[0].getWidth())*scale),
			(int)((10+3*Players[0].getWidth())*scale)};
		
		int currentPlayer = gs.getCurrentPlayerNo();
		for(int i = 0; i < players.length; i++) {
			int currentTank = players[i].getCurrentTankNo();
			currTank[i] = tankNos[currentTank].copy();
			if(i == currentPlayer) {
				//Draw the ith Player as the currentPlayer
				//Display currentTank
				currPlayer[i].draw(/*camera.getOffset().getX()+*/xOffset[i], /*camera.getOffset().getY()+*/5*scale, (float)(scale*0.8));
			} else {
				Players[i].draw(/*camera.getOffset().getX()+*/xOffset[i], /*camera.getOffset().getY()+*/5*scale, (float)(scale*0.8));
			}
			currTank[i].draw(xOffset[i]+80*scale, 12*scale, (float)(scale*0.8));
		}
		
		
		//********** The General Information Section *************
		backFrame.draw(screenX-controlX, 0, (float)(scale));
			
		//Draw the current round			
		int rounds = gs.getRoundsPlayed();
		Image Round = sprites.getSubImage(429, 106, 65, 20);
		Round.draw(screenX-controlX+controlX/3+controlX/60, controlY/30, (float)(scale));
		drawTwoDigitNumber(rounds+1, screenX-controlX/5, controlY/30, (float)(scale));

		//Draw the current time
		//long time = gc.getTime()-gs.getTimeStarted();
		long time = gc.getTime()/1000 - gs.getTimeStarted()/1000000000;
		Image Colon = sprites.getSubImage(565, 109, 6, 19);
		formatTime(time+1,screenX-controlX/2-controlX/19,controlY/6+controlY/30,scale);
		Colon.draw(screenX-controlX/2-controlX/19+30*scale,controlY/6+controlY/30, scale);
				
		//********** The Current Round Section **************
	
		
		//Draw the wind + windAngle
		float wind = world.getWindSpeed();
		float windAngle = world.getWindAngle();
		
		//load windspeed indicators
		Image[] windRight = new Image[] {
			sprites.getSubImage(0, 0, 22, 58),
			sprites.getSubImage(24, 0, 28, 58),
			sprites.getSubImage(55, 0, 49, 58),
			sprites.getSubImage(105, 0, 69, 58),
			sprites.getSubImage(176, 0, 74, 58)
		};
		
		Image[] windLeft = new Image[] {
			sprites.getSubImage(0, 59, 22, 58),
			sprites.getSubImage(24, 59, 28, 58),
			sprites.getSubImage(55, 59, 49, 58),
			sprites.getSubImage(105, 59, 69, 58),
			sprites.getSubImage(176, 59, 74, 58)
		};
		
		//draw wind indicator
		if (windAngle<90 || 270<windAngle)
			windRight[(int)Math.floor(wind)].getScaledCopy(scale).drawCentered(screenX-controlX/3, controlY-controlY/6);
		else
			windLeft[(int)Math.floor(wind)].getScaledCopy(scale).drawCentered(screenX-controlX/3, controlY-controlY/6);
		
		
		//load shooting angle elements
		Tank currentTank = players[currentPlayer].getCurrentTank();
		float shootingAngle = currentTank.getbAngle();
		
		Image disk = sprites.getSubImage(113, 119, 64, 65);
		Image pointer = sprites.getSubImage(180,120,2,31*2);
		Image cap = sprites.getSubImage(184, 120, 2, 2);
		
		//Draw the shooting Angle
		disk.getScaledCopy(scale).drawCentered(screenX-controlX/3, controlY/2-controlY/80);
		pointer.getScaledCopy(scale);
		pointer.rotate(90);
		pointer.rotate(shootingAngle);
		pointer.drawCentered(screenX-controlX/3+controlX/70, controlY/2-controlY/80);
		cap.getScaledCopy(scale).drawCentered(screenX-controlX/3+controlX/70, controlY/2-controlY/80);
		
		//angle arrows
		Image angleLeft = sprites.getSubImage(116, 186, 21, 23);
		Image angleRight = sprites.getSubImage(140, 186, 21, 23);
		Image angleLeftP = sprites.getSubImage(116, 210, 21, 23);
		Image angleRightP = sprites.getSubImage(140, 210, 21, 23);
		Input input = gc.getInput();
		angleLeft.draw(screenX-controlX/2-controlX/8, controlY/2+controlY/30, scale);
		angleRight.draw(screenX-controlX/6, controlY/2+controlY/30, scale);
		boolean rightPressed = input.isKeyDown(205);
		boolean leftPressed = input.isKeyDown(203);
		if (rightPressed == true) 
			angleRightP.draw(screenX-controlX/6, controlY/2+controlY/30, scale);
		if (leftPressed == true)
			angleLeftP.draw(screenX-controlX/2-controlX/8, controlY/2+controlY/30, scale);

		
		
		//load shooting strength
		Image nullStrength = sprites.getSubImage(0, 120, 29, 245-120);
		Image strengthUp = sprites.getSubImage(61, 122, 84-61, 142-120);
		Image strengthDown = sprites.getSubImage(61, 145, 84-61, 142-122);
		Image strengthUpP = sprites.getSubImage(86, 122, 84-61, 142-120);
		Image strengthDownP = sprites.getSubImage(86, 145, 84-61, 142-122);
		
		//adjusted strength meter
		Image fullStrength = sprites.getSubImage(31, 120, 28, 125);
		nullStrength.draw(screenX-controlX+controlX/14, controlY/7, (float)(scale));
		strengthUp.draw(screenX-controlX+controlX/14+controlX/50, controlY/30, (float)(scale));
		strengthDown.draw(screenX-controlX+controlX/14+controlX/50, controlY-controlY/3+controlY/18, (float)(scale));
		
		//adjust strength
		float shootingStrength = currentTank.getLaunchSpeed();
		Image fullStrengthW;
		fullStrengthW = fullStrength.getSubImage(0, (int)(fullStrength.getHeight()*(1-shootingStrength)), fullStrength.getWidth(), (int)(fullStrength.getHeight()*(shootingStrength)));
		fullStrengthW.draw(screenX-controlX+controlX/14, (controlY/7+ (scale*fullStrength.getHeight()-scale*fullStrengthW.getHeight())), fullStrength.getWidth()*scale, (int)(fullStrength.getHeight()*scale*shootingStrength ));
		boolean upPressed = input.isKeyDown(200);
		boolean downPressed = input.isKeyDown(208);
		if (upPressed == true) 
			strengthUpP.draw(screenX-controlX+controlX/14+controlX/50, controlY/30, (float)(scale));
		if (downPressed == true)
			strengthDownP.draw(screenX-controlX+controlX/14+controlX/50, controlY-controlY/3+controlY/18, (float)(scale));
		
		//Draw the weapon(selection) + Ammo
		Image selectWeapon = sprites.getSubImage(62, 206, 22, 19);
		Image selectWeaponP = sprites.getSubImage(86, 206, 22, 19);
		selectWeapon.draw(screenX-controlX+controlX/14+controlX/50, controlY-controlY/30, (float)(scale));
		
		Weapon[] weapons = currentTank.getWeapons();
		int currentWeapon = currentTank.getCurrentWeapon();
		
	}
	
	

}


/*Input input = gc.getInput();
int mouseX = input.getMouseX();
int mouseY = input.getMouseY();

//if inside the right Y range for all buttons
if(mouseY >= camera.getOffset().getY()+9 && mouseY <= camera.getOffset().getY()+9+142-120) {
	//if inside the right X range for the start button
	if(mouseX >= camera.getScreenWidth()-152+16 && mouseX <= camera.getScreenWidth()-152+16+84-61) {
		strengthUp = strengthUpP.copy();//.setRotation(5.0f);
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (players[currentPlayer].getCurrentTank().getLaunchSpeed() <1)
				players[currentPlayer].getCurrentTank().setLaunchSpeed( (float)
						(players[currentPlayer].getCurrentTank().getLaunchSpeed() + 0.005));
				//players[currentPlayer].getCurrentTank().setLaunchSpeed() += 0.005;
		}
	} else {
		strengthUp = sprites.getSubImage(61, 122, 84-61, 142-122);
	}
} else {
	strengthUp = sprites.getSubImage(61, 122, 84-61, 142-122);
}	*/
