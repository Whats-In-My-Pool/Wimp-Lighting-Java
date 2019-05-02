/**
 * 
 */
package man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author DeadRiver
 *
 */
public class ShowFrame {
	
	BufferedImage display = null;
	BufferedImage refstrip = null;
	
	JFrame frame = new JFrame();
	JPanel wind = new JPanel() {
		public void paintComponent(Graphics g) {
			g.setColor(Color.black);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
			
			if(display!=null) {
				g.drawImage(display, 0, frame.getHeight()/2, display.getWidth()*2, display.getHeight()*2, null);
			}
		}
	};
	
	public ShowFrame() {
		File tame = new File("OUTPUT");
		tame.mkdirs();
		File tame2 = new File("REF");
		tame2.mkdirs();
		
		Thread refresh = new Thread() {
			public void run() {
				for(int x=0;x<1000;x++) {
					StripGen stripmaker = new StripGen();
					
					
					display = stripmaker.generateStrip();
					refstrip=display;
					wind.repaint();
					
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					display = stripmaker.addNoiseRef();
					wind.repaint();
					
//					try {
//						Thread.sleep(3000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					
					display = stripmaker.runENB();
					wind.repaint();
					
//					try {
//						Thread.sleep(6000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					display = stripmaker.deSatSq(display);
					wind.repaint();
					
					
					
					try {
						ImageIO.write(display, "png", new File("OUTPUT/OUT" + tame.listFiles().length + ".png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						ImageIO.write(refstrip, "png", new File("REF/REF" + tame2.listFiles().length + ".png"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
				System.exit(0);
				
			}
		};
		
		
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wind.setSize(1200, 600);
		
		
		frame.add(wind);
		
		frame.setVisible(true);
		
		
		refresh.start();
	}

}
