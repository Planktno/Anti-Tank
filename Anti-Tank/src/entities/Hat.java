package entities;

import game.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import states.GameState;

public class Hat {
	
	private int type;

	private Vector2f pos;
	private float scale;
	private Image image;
	
	private float vx, vy;
	
	private Tank wearer;
	private boolean hatEffects;
	
	public Hat(int type, Vector2f pos, float scale) {
		this.type = type;
		this.pos = pos;
		this.scale = scale;
		
		switch(type) {
		case 1: this.image = ResourceManager.getInstance().getImage("HATS").getSubImage(0, 0, 32, 32); break;
		case 2: this.image = ResourceManager.getInstance().getImage("HATS").getSubImage(32, 0, 32, 32); break;
		case 3: this.image = ResourceManager.getInstance().getImage("HATS").getSubImage(64, 0, 32, 32); break;
		case 4: this.image = ResourceManager.getInstance().getImage("HATS").getSubImage(96, 0, 32, 32); break;
		}
		vx = vy = 0;
		
		wearer = null;
		hatEffects = false;
	}
	
	public void update(GameContainer gc, StateBasedGame game, int delta, World world, Input in, GameState gs, Camera cam) {
		if(wearer == null) {
			vy += world.getGravity() * delta / 100;
			Vector2f deltaPos = new Vector2f(vx*delta/100, vy*delta/100);
			pos.add(deltaPos);
			if(GameState.checkCollision(this, world)) {
				vx = vy = 0;
				pos.sub(deltaPos);
			}
			for(Player p : gs.getPlayers()) {
				for(Tank t : p.getTanks()) {
					if(t.isAlive() && GameState.checkCollision(this, t)) {
						wearer = t;
						vx = vy = 0;
						return;
					}
				}
			}
			System.out.println("Hat:" + pos.x + " " + pos.y);
		} else {
			if(!wearer.isAlive()) {
				wearer = null;
				hatEffects = false;
			}
			else {
				pos = new Vector2f(wearer.getPos()).add(new Vector2f(0,-16*cam.getFocusScale()));
				if(!hatEffects) {
					hatEffects = true;
					switch(type) {
					case 1: wearer.setMaxMovementAmount(Tank.MAXMOVEMENT*2); break;
					case 2: wearer.setHealth(2*wearer.returnHp()); break;
					case 3: Weapon[] weapons = wearer.getWeapons();
							Weapon[] newWeapons = new Weapon[weapons.length];
							for(int i = 0; i < weapons.length; i++) {
								Vector2f wepPosition = weapons[0].getPosition();
								switch(weapons[i].getProjectile()) {
								case 1: newWeapons[i] = new Weapon(2,wepPosition); break;
								case 2: newWeapons[i] = new Weapon(7,wepPosition); break;
								case 3: newWeapons[i] = new Weapon(4,wepPosition); break;
								case 4: newWeapons[i] = new Weapon(9,wepPosition); break;
								case 5: newWeapons[i] = new Weapon(5,wepPosition); break;
								case 6: newWeapons[i] = new Weapon(8,wepPosition); break;
								case 7: newWeapons[i] = new Weapon(7,wepPosition); break;
								case 8: newWeapons[i] = new Weapon(8,wepPosition); break;
								case 9: newWeapons[i] = new Weapon(9,wepPosition); break;
								}
							}
							wearer.setWeapons(newWeapons);
							break;
					case 4: wearer.setWeight(3); break;
					}
				}
			}
		}
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics gr, Camera cam) {
		Vector2f relpos = cam.getRelFocusPos(pos);
		image.draw(relpos.x, relpos.y, scale * cam.getMultipliedScale());
	}
	
	public Vector2f getPosition() {
		return pos;
	}
	
	public Image getImage() {
		return image;
	}
}
