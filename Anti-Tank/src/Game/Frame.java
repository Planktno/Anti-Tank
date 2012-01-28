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

        this.validate();
	}
	
	public static void main (String[] args) {
		Frame f = new Frame();
	}
	
}
