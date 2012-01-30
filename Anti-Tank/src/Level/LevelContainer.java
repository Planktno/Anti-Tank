package Level;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class LevelContainer extends JLabel{
	
	BufferedImage img;
 
    public LevelContainer() {
       try {
           img = ImageIO.read(new File("data/testlevel.png"));
       } catch (IOException e) {
       }
       this.setBounds(0, 0, 800, 600);
    }
    
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
 
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }

}
