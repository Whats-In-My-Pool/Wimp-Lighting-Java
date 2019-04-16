/**
 * 
 */
package man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * @author DeadRiver
 *
 */
public class Alg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Alg();
	}
	int[][] colors = null;
//	int pixel = 2;//DEFAULT
	int pixel = 4;//USE FOR SCALING
	int amplify = 10;
	
	int[][] slopes = null;
	int[][] slopes2 = null;
	
	JSlider adjustment = new JSlider(1, 255, 10);
	JSlider graphsA;
	JSlider graphsB;
	
	JFrame frame = new JFrame();
	JPanel wind = new JPanel() {
		public void paintComponent(Graphics g) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
			
			g.setColor(Color.RED);
			g.drawString("AMP " + amplify, 30, 450);
			
			g.setColor(new Color(amplify,0,0));
			g.fillRect(28 - pixel, 450 - pixel, pixel, pixel);
//			g.fillRect(0, 505, 1200, 100);
			
			for(int x=0;x<colors.length;x++) {
				for(int y=0;y<colors[0].length;y++) {
					//int cp = Math.min(, 255);
					int cp = colors[x][y]*amplify;
					g.setColor(new Color(Math.max(Math.min(cp,255),0),Math.max(Math.min(cp-100,255),0),Math.max(Math.min(cp-200,255),0)));
					g.fillRect(x*pixel, y*pixel, pixel, pixel);
				}
			}
			
			g.setColor(Color.CYAN);
			int val = graphsA.getValue();//////////////
//			int py = slopes[val][0];
			int displacey = 800;
			int displacex = 500;
			g.drawString("GRAPH: " + (val+1) + "  SLICES: " + slopes.length, 0, 500);
			
			g.fillRect((colors.length)*pixel, val*pixel, pixel, pixel);
			
			for(int x=0;x<slopes[val].length;x++) {
//				g.drawLine(2*x, -py + displacey, 2*(x+1), -slopes[val][x] + displacey);
//				py = slopes[val][x];
				g.drawRect(x*amplify, displacey, amplify, -slopes[val][x]);
			}
			
			
			val = graphsB.getValue();/////////////
			g.drawString("GRAPH: " + (val+1) + "  SLICES: " + slopes2.length, displacex, 500);
			g.setColor(Color.GREEN);
			
//			int px = slopes2[val][0];
			g.fillRect(val*pixel, (colors[0].length)*pixel, pixel, pixel);
			
			for(int x=0;x<slopes2[val].length;x++) {
//				g.drawLine((10*x)+displacex, -px + displacey, (10*(x+1))+displacex, -slopes2[val][x] + displacey);
//				px = slopes2[val][x];
				g.drawRect(x*amplify+displacex, displacey + 100, amplify, -slopes2[val][x]);
			}
			
		}
	};
	public Alg() {
		Thread refresh = new Thread() {
			public void run() {
				while(true) {
					
					amplify = adjustment.getValue();
					
					wind.repaint();
					
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		};
		
		
		try {
			BufferedImage source = ImageIO.read(new File("Source.jpg"));
			BufferedImage test = ImageIO.read(new File("Test1.jpg"));
			
			
			///RESCALE IMAGE
			
			Image test2	 = test.getScaledInstance(test.getWidth()/2, test.getHeight()/2, BufferedImage.SCALE_SMOOTH);
			
			test = new BufferedImage(test.getWidth()/2,test.getHeight()/2,BufferedImage.TYPE_INT_RGB);
			Graphics h = test.createGraphics();
			h.drawImage(test2, 0, 0, null);
			h.dispose();
			
			//REASCALE
			
			
			graphsA = new JSlider(0, test.getHeight()-1, 0);
			graphsB = new JSlider(0, test.getWidth()-1, 0);
			slopes = new int[test.getHeight()][test.getWidth()];
			slopes2 = new int[test.getWidth()][test.getHeight()];
			colors = new int[test.getWidth()][test.getHeight()];
			
			int[][] dx = new int[test.getWidth()][test.getHeight()];
			int[][] dy = new int[test.getWidth()][test.getHeight()];
			
			for(int x=0;x<test.getWidth();x++) {
				for(int y=0;y<test.getHeight();y++) {
					int d = test.getRGB(x, y);
					int red = (d & 0x00ff0000) >> 16;
					int green = (d & 0x0000ff00) >> 8;
					int blue = d & 0x000000ff;
					colors[x][y] = (int)(Math.sqrt(red*red + green*green + blue*blue)/Math.sqrt(3));
				}
			}
			
			for(int x=0;x<test.getWidth();x++) {
				for(int y=0;y<test.getHeight()-1;y++) {
					dy[x][y] = colors[x][y+1] - colors[x][y];
//					slopes[y][x] = dy[x][y];
					slopes[y][x] = colors[x][y];
				}
			}
			for(int y=0;y<test.getHeight();y++) {
				for(int x=0;x<test.getWidth()-1;x++) {
					dx[x][y] = colors[x+1][y] - colors[x][y];
//					slopes2[x][y] = dx[x][y];
					slopes2[x][y] = colors[x][y];
				}
			}
			
			
			//ASSIGN DIFERENTIAL TO COLORS
//			for(int x=0;x<test.getWidth();x++) {
//				for(int y=0;y<test.getHeight();y++) {
//					colors[x][y] = Math.abs(dx[x][y] + dy[x][y]);
//				}
//			}
			//ASSIGN DIFERENTIAL TO COLORS
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		frame.setSize(600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		wind.setSize(1200, 600);
		
		
		frame.add(wind);
		wind.add(adjustment);
		wind.add(graphsA);
		wind.add(graphsB);
		
		frame.setVisible(true);
		
		refresh.start();
		
	}
	
}
