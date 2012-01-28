package Game;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame() {
        super("Anti-Tank");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);
        
        Panel_StartScreen s = new Panel_StartScreen();
        this.add(s);
        
        //Panel_HistoryScreen h = new Panel_HistoryScreen();
        //this.add(h);
        
        //Panel_AnimationTest a = new Panel_AnimationTest();
        //this.add(a);

        this.validate();
	}
	
	public static void main (String[] args) {
		Frame f = new Frame();
	}
	
}
