package Level;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Game.Frame;

public class GameContainer {
	
	private Frame frame;
	private JPanel panel;
	
	private boolean gameOver;
	
	private LevelContainer level;
	private Tank[] tanks;
	
	private PhysicsManager pm;

	public GameContainer(Frame frame){
		//this();
		this.frame = frame;
		this.panel = new JPanel();
		this.panel.setLayout(null);
		
		this.gameOver = false;
		
		this.tanks = new Tank[1];
		this.tanks[0] = new Tank();
		this.panel.add(this.tanks[0]);
		
		this.level = new LevelContainer();
		this.panel.add(this.level);
		
		this.panel.setVisible(true);
		
		this.pm = new PhysicsManager();
		pm.setLevelContainer(level);
		pm.setTanks(tanks);
	}
	
	public void mainLoop() throws InterruptedException {
//		frame.removeAll();
		frame.add(panel);
		frame.validate();
		
		long time = System.currentTimeMillis();
		int delta = 0;
		
		while(!gameOver){
			delta = (int) (System.currentTimeMillis() - time);
			time = System.currentTimeMillis();
			
			pm.update(delta);
			
			Thread.sleep(10);
		}
	}
	
}
