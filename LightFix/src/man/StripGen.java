package man;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class StripGen {
	ArrayList<Color> Hards = new ArrayList<Color>();
	ArrayList<Color> TotalCl = new ArrayList<Color>();
	ArrayList<Color> FreeCl = new ArrayList<Color>();
	ArrayList<Color> PH = new ArrayList<Color>();
	ArrayList<Color> TotalAlk = new ArrayList<Color>();
	ArrayList<Color> Stablizer = new ArrayList<Color>();
	
	ArrayList<Color> profile = new ArrayList<Color>();
	
	Color REF = new Color(243,246,239);
	
	Color source = new Color(255,255,255);
	Point source_location = new Point(578/3,65/2);
	
	
	BufferedImage refStrip = null;
	BufferedImage noStrip = null;
	BufferedImage enbStrip = null;
	
	public StripGen() {
		Hards.add(new Color(76,101,141));
		Hards.add(new Color(93,104,149));
		Hards.add(new Color(107,101,149));
		Hards.add(new Color(126,98,138));
		Hards.add(new Color(141,93,141));
		
		TotalCl.add(new Color(248,246,146));
		TotalCl.add(new Color(231,241,154));
		TotalCl.add(new Color(214,240,149));
		TotalCl.add(new Color(165,209,132));
		TotalCl.add(new Color(123,184,115));
		
		FreeCl.add(new Color(245,247,172));
		FreeCl.add(new Color(207,205,184));
		FreeCl.add(new Color(168,151,187));
		FreeCl.add(new Color(157,125,174));
		FreeCl.add(new Color(130,89,147));
		
		PH.add(new Color(233,168,68));
		PH.add(new Color(206,124,51));
		PH.add(new Color(207,113,62));
		PH.add(new Color(202,102,76));
		PH.add(new Color(195,78,86));
		
		TotalAlk.add(new Color(206,182,72));
		TotalAlk.add(new Color(154,161,84));
		TotalAlk.add(new Color(105,133,93));
		TotalAlk.add(new Color(86,121,91));
		TotalAlk.add(new Color(68,94,81));
		
		Stablizer.add(new Color(202,154,78));
		Stablizer.add(new Color(190,122,75));
		Stablizer.add(new Color(176,100,87));
		Stablizer.add(new Color(171,83,143));
		Stablizer.add(new Color(82,106,92));
	}
	
	
	public BufferedImage generateStrip() {
		Random r = new Random();
		BufferedImage strip = new BufferedImage(578,65,BufferedImage.TYPE_INT_RGB);
		Graphics g = strip.createGraphics();
		
		g.setColor(REF);
		g.fillRect(0, 0, strip.getWidth(), strip.getHeight());
		
		g.setColor(Hards.get(r.nextInt(Hards.size())));
		profile.add(g.getColor());
		g.fillRect(0, 0, 65, 65);
		
		g.setColor(TotalCl.get(r.nextInt(TotalCl.size())));
		profile.add(g.getColor());
		g.fillRect(37+65, 0, 65, 65);
		
		g.setColor(FreeCl.get(r.nextInt(FreeCl.size())));
		profile.add(g.getColor());
		g.fillRect((37+65)*2, 0, 65, 65);
		
		g.setColor(PH.get(r.nextInt(PH.size())));
		profile.add(g.getColor());
		g.fillRect((37+65)*3, 0, 65, 65);
		
		g.setColor(TotalAlk.get(r.nextInt(TotalAlk.size())));
		profile.add(g.getColor());
		g.fillRect((37+65)*4, 0, 65, 65);
		
		g.setColor(Stablizer.get(r.nextInt(Stablizer.size())));
		profile.add(g.getColor());
		g.fillRect((37+65)*5, 0, 65, 65);
		
		g.dispose();
		
		
		refStrip = strip;
		return strip;
	}
	
	int noise = 5;
	int softnoise = 3;
	public BufferedImage addNoiseRef() {
		Random ran = new Random();
		BufferedImage strip = new BufferedImage(578,65,BufferedImage.TYPE_INT_RGB);
		Graphics g = strip.createGraphics();
		
		
		for(int x=0;x<strip.getWidth();x++) {
			for(int y=0;y<strip.getHeight();y++) {
				Color s = REF;
				
				int r1 = Math.max(Math.min(s.getRed() + ran.nextInt(softnoise*2+1)-softnoise,255),0);
				int g1 = Math.max(Math.min(s.getGreen() + ran.nextInt(softnoise*2+1)-softnoise,255),0);
				int b1 = Math.max(Math.min(s.getBlue() + ran.nextInt(softnoise*2+1)-softnoise,255),0);
				
				s = new Color(r1,g1,b1);
				
				g.setColor(s);
				g.fillRect(x, y, 1, 1);
			}
		}
		
		for(int i=0;i<profile.size();i++) {
			for(int x=0;x<65;x++) {
				for(int y=0;y<65;y++) {
					Color s = profile.get(i);
					
					int r1 = Math.max(Math.min(s.getRed() + ran.nextInt(noise*2+1)-noise,255),0);
					int g1 = Math.max(Math.min(s.getGreen() + ran.nextInt(noise*2+1)-noise,255),0);
					int b1 = Math.max(Math.min(s.getBlue() + ran.nextInt(noise*2+1)-noise,255),0);
					
					s = new Color(r1,g1,b1);
					g.setColor(s);
					g.fillRect((37+65)*i + x, y, 1, 1);
				}
			}
			
			
		}
		
		
		g.dispose();
		
		noStrip = strip;
		return strip;
	}
	
	
	public BufferedImage deSatSq(BufferedImage strip) {
		Graphics g = strip.createGraphics();
		
//		double a = 1.01068;//1.01068; OLD , created 2 instead 1 with + powers. For f1
//		double spread = 0.06198;//For f1
		
		double a = 0.01;//1.01068; OLD , created 2 instead 1 with + powers. For f2
		double spread = 0.001;//For f2
		
//		double a = 0.0153845;//0.0153845 For f3
//		double spread = 0.001;//For f3
		
		for(int i=0;i<profile.size();i++) {
			for(int x=0;x<65;x++) {
				for(int y=0;y<65;y++) {
					int d = strip.getRGB((37+65)*i + x, y);
					
					double[] colorsrc = intToRGB(d);
					
					if(x<65/2 && i>0) {
						d = strip.getRGB((37+65)*i - 1, y);
					}else {
						d = strip.getRGB((37+65)*i + 66, y);
					}
					
					double[] colorsample = intToRGB(d);
					
					double x1 = (x-65/2);
					double y1 = (y-65/2);
					
					
//					double vintensitysrc = 		Math.sqrt(colorsrc[0]*colorsrc[0] + colorsrc[1]*colorsrc[1] + colorsrc[2]*colorsrc[2])/Math.sqrt(3);
//					double vintensitysample = 	Math.sqrt(colorsample[0]*colorsample[0] + colorsample[1]*colorsample[1] + colorsample[2]*colorsample[2])/Math.sqrt(3);
					
//					double f = (Math.pow(a, Math.pow(x1,2)*spread)-1)+(Math.pow(a, Math.pow(y1,2)*spread)-1);
//					f = Math.min(f, 1);
					
					
					double f = 1.077 - 10*(Math.pow(a, Math.pow(x1,2)*spread))*(Math.pow(a, Math.pow(y1,2)*spread));
					f = Math.min(f, 1);
					f = Math.max(f, 0);
					
//					double f  = 1 - 4*(Math.sin(a*2*Math.PI*x1)/(a*2*Math.PI*x1))*(Math.sin(a*2*Math.PI*y1)/(a*2*Math.PI*y1));
//					
//					if(x1==0) {
//						f  = 1 - 4*Math.sin(a*2*Math.PI*y1)/(a*2*Math.PI*y1);
//					}
//					if(y1==0) {
//						f  = 1 - 4*Math.sin(a*2*Math.PI*x1)/(a*2*Math.PI*x1);
//					}
//					if(y1==0 && x1==0) {
//						f=0;
//					}
//					f = Math.min(f, 1);
//					f = Math.max(f, 0);
					
					int r1 = (int) ((colorsample[0]-colorsrc[0])*f 	+colorsrc[0]);
					int g1 = (int) ((colorsample[1]-colorsrc[1])*f 	+colorsrc[1]);
					int b1 = (int) ((colorsample[2]-colorsrc[2])*f 	+colorsrc[2]);
					
					
					r1 = Math.min(r1, 255);
					g1 = Math.min(g1, 255);
					b1 = Math.min(b1, 255);
					
					try {
						Color s = new Color(r1,g1,b1);
						g.setColor(s);
						g.fillRect((37+65)*i + x, y, 1, 1);
					}catch(Exception e) {
						System.out.println(colorsample[0]);
						System.out.println(colorsample[1]);
						System.out.println(colorsample[2]);
						System.out.println(colorsrc[0]);
						System.out.println(colorsrc[1]);
						System.out.println(colorsrc[2]);
						System.out.println(r1);
						System.out.println(g1);
						System.out.println(b1);
						System.exit(0);
					}
					
				}
			}
			
			
		}
		
		
		g.dispose();
		
		
		
		return strip;
	}
	
	
	public double[] intToRGB(int d) {
		double red = (d & 0x00ff0000) >> 16;
		double green = (d & 0x0000ff00) >> 8;
		double blue = d & 0x000000ff;
		
		return new double[] {red,green,blue};
	}
	
	
	public BufferedImage runENB() {
		BufferedImage strip = new BufferedImage(578,65,BufferedImage.TYPE_INT_RGB);
		strip = deSatSq(strip);
		
		Graphics g = strip.createGraphics();
		g.drawImage(noStrip, 0, 0, null);
		g.dispose();
		
		double a = 1.001;
		double spread = 0.03;
		
		double drop = 0.6;//Drop per channel
		
		for(int x=0;x<strip.getWidth();x++) {
			for(int y=0;y<strip.getHeight();y++) {
				int d = strip.getRGB(x, y);
				
				double red = 0.0;
				double green = 0.0;
				double blue = 0.0;
				
				///GREYSCALE OVERRIDE
				red = (d & 0x00ff0000) >> 16;
				green = (d & 0x0000ff00) >> 8;
				blue = d & 0x000000ff;
				
				double x1 = (x-source_location.x)*spread;
				double y1 = (y-source_location.y)*spread;
				
				red*=		Math.pow(a, -Math.pow(x1,2))*Math.pow(a, -Math.pow(y1,2));
				green*=		Math.pow(a, -Math.pow(x1,2))*Math.pow(a, -Math.pow(y1,2));
				blue*=		Math.pow(a, -Math.pow(x1,2))*Math.pow(a, -Math.pow(y1,2));
				
				double vintensity = Math.sqrt(red*red + green*green + blue*blue)/Math.sqrt(3);
				
				red 	-= drop*vintensity;
				green 	-= drop*vintensity;
				blue 	-= drop*vintensity;
				
				red = Math.max(Math.min(red, 255),0);
				green = Math.max(Math.min(green, 255),0);
				blue = Math.max(Math.min(blue, 255),0);
				
				d = (int)red;
				d = (d<<8) + (int)green;
				d = (d<<8) + (int)blue;
				
				strip.setRGB(x, y, d);
			}
		}
		
		enbStrip = strip;
		
		return strip;
	}
}
