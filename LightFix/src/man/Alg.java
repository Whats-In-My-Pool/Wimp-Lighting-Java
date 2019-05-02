/**
 * 
 */
package man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
	
	static volatile int[] bestC = new int[3];
	static volatile int bestResult = 6000;
	
	static final int fileslen = 1000;
	
	static volatile ArrayList<Thread> openThreads = new ArrayList<Thread>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new Alg();         // This runs the strip image examiner to see what surface is.
//		new ShowFrame();	//this creates new strips and shows them as they are made for debugging. I disabled it for now.
		
		
		
		
		
		File testFile = new File("OUTPUT");
		File refFile = new File("REF");
		File[] tests = testFile.listFiles();
		File[] refs = refFile.listFiles();
		
		Random r = new Random();
		

		BufferedImage s1 = null;
		BufferedImage ref = null;
		Colorc correction = new Colorc();
		
		ArrayList<int[][]> testData = new ArrayList<int[][]>();
		
		for(int i=0;i<fileslen;i++) {//tests.length
			if(i%100==0) {
//				System.out.println(i + " tests finished..");
			}
			try {
				s1 = ImageIO.read(tests[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				ref = ImageIO.read(refs[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			correction.matchStripJON(s1, ref);
			testData.add(correction.matchStripJON(s1, ref));
			
//			testData.add(correction.matchStrip(s1, ref));// LAB method.
//			break;
		}
		
		
		System.out.println("DONE TESTING");
		
		int wrongStrip = 0;
		int numberRight = 0;
		int numberWrong = 0;
		double exp2 = 0; 
		for(int i=0;i<testData.size();i++) {
			int[][] t = testData.get(i);
			boolean broken = false;//If a strip has any wrong, its going to be counted for non-perfect tests.
			int errs = 0;
			for(int u=0;u<6;u++) {
				if(Math.abs(t[u][0]-t[u][1])>1) { //Selection is +- 1 apart
//				if(t[u][0]!=t[u][1]) {				// Selections are equal.
					broken=true;
					numberWrong++;
					errs++;
				}else {
					numberRight++;
				}
			}
			if(broken) {wrongStrip++;}
			exp2+=errs*errs;
		}
		exp2/=testData.size();
		exp2 = Math.sqrt(exp2 - numberWrong/testData.size());
		System.out.println("TOTAL WRONG: " + numberWrong + " TOTAL RIGHT: " + numberRight + " # Wrong Strips: " + wrongStrip + " TOTAL STRIPS: " + testData.size() + " Standard Deviation : " + exp2);
		
		
		///THIS CODE BELOW WAS USED TO FIND MINIMA.
/*				
		int ppa = -16;//-20 + r.nextInt(41);
		int ppb = -14;//-20 + r.nextInt(41);
		int ppc = 8;//-20 + r.nextInt(41);
		
		System.out.println(ppa + " " + ppb + " " + ppc);
		
		int[][] window = new int[][] {
			{ppa-2,ppa+2},
			{ppb-2,ppb+2},
			{ppc-2,ppc+2}
		};
		
		int[] lastresults = new int[3];

		for(int l=0;l<20;l++) {
			while(openThreads.size()>0) {
				for(int i=0;i<openThreads.size();i++) {
					if(!openThreads.get(i).isAlive()) {
						openThreads.remove(i);
						i--;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int cx=window[0][0];cx<window[0][1];cx++) {
//				System.out.println(cx);
				for(int cy=window[1][0];cy<window[1][1];cy++) {
					for(int cz=window[2][0];cz<window[2][1];cz++) {
						Thread std = new Thread(cx+","+cy+","+cz){
							public void run() {
								BufferedImage s1 = null;
								BufferedImage ref = null;
								Colorc correction = new Colorc();
								String[] ccs = this.getName().split(",");
								correction.colorShift = new int[] {Integer.parseInt(ccs[0]),Integer.parseInt(ccs[1]),Integer.parseInt(ccs[2])};
								
								ArrayList<int[][]> testData = new ArrayList<int[][]>();
								
								for(int i=0;i<fileslen;i++) {//tests.length
									if(i%100==0) {
//										System.out.println(i + " tests finished..");
									}
									try {
										s1 = ImageIO.read(tests[i]);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									try {
										ref = ImageIO.read(refs[i]);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
//									correction.matchStripJON(s1, ref);
									testData.add(correction.matchStripJON(s1, ref));
									
//									testData.add(correction.matchStrip(s1, ref));
//									break;
								}
								
								
//								System.out.println("DONE TESTING");
								
//								int wrongStrip = 0;
								int numberRight = 0;
								int numberWrong = 0;
								double exp2 = 0; 
								for(int i=0;i<testData.size();i++) {
									int[][] t = testData.get(i);
									boolean broken = false;
									int errs = 0;
									for(int u=0;u<6;u++) {
										if(t[u][0]!=t[u][1]) {
											broken=true;
											numberWrong++;
											errs++;
										}else {
											numberRight++;
										}
									}
//									if(broken) {wrongStrip++;}
//									exp2+=errs*errs;
								}
//								exp2/=testData.size();
//								exp2 = Math.sqrt(exp2 - numberWrong/testData.size());
//								System.out.println("TOTAL WRONG: " + numberWrong + " TOTAL RIGHT: " + numberRight + " # Wrong Strips: " + wrongStrip + " TOTAL STRIPS: " + testData.size() + " Standard Deviation : " + exp2);
								if(numberWrong<bestResult) {
									bestC = new int[] {Integer.parseInt(ccs[0]),Integer.parseInt(ccs[1]),Integer.parseInt(ccs[2])};
									bestResult = numberWrong;
								}
							}
						};
						openThreads.add(std);
						std.start();
						
						while(openThreads.size()>40) {
							for(int i=0;i<openThreads.size();i++) {
								if(!openThreads.get(i).isAlive()) {
									openThreads.remove(i);
									i--;
								}
							}
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				
			}
			System.out.println("MIN POSSIBLE errors " + bestResult + " OUT OF " + (fileslen*6) + " tests");
			System.out.println("Best SCORE " + bestC[0] + " " + bestC[1] + " " + bestC[2]);
			
			window[0][0] = bestC[0]-3;
			window[0][1] = bestC[0]+3;
			window[1][0] = bestC[1]-3;
			window[1][1] = bestC[1]+3;
			window[2][0] = bestC[2]-3;
			window[2][1] = bestC[2]+3;
			if(lastresults[0]==bestC[0] && lastresults[1]==bestC[1] && lastresults[2]==bestC[2]) {
				return;
			}
			lastresults[0]=bestC[0];
			lastresults[1]=bestC[1];
			lastresults[2]=bestC[2];
		}
		*/
		///THIS CODE ABOVE WAS USED TO FIND MINIMA.
		
	}
	
	
	int[][] colors = null;
	int pixel = 2;//DEFAULT
//	int pixel = 4;//USE FOR SCALING
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
//			BufferedImage test = ImageIO.read(new File("OUT1.png"));
			
			
			///RESCALE IMAGE
			
//			Image test2	 = test.getScaledInstance(test.getWidth()/2, test.getHeight()/2, BufferedImage.SCALE_SMOOTH);
//			
//			test = new BufferedImage(test.getWidth()/2,test.getHeight()/2,BufferedImage.TYPE_INT_RGB);
//			Graphics h = test.createGraphics();
//			h.drawImage(test2, 0, 0, null);
//			h.dispose();
			
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
