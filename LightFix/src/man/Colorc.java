/**
 * 
 */
package man;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author DeadRiver
 *
 */
public class Colorc {
	BufferedImage test = null;
	BufferedImage output = null;
	BufferedImage source = null;
	
//	Point s1 = new Point(614,237);//87 spacing on y axis in pixels
//	Point s2 = new Point(614,375);
//	Point s3 = new Point(614,506);
//	Point s4 = new Point(614,633);
//	Point s5 = new Point(614,752);
//	Point s6 = new Point(614,616);
	
	int[] trueWhite = new int[] {243,246,239};
	
	int[] points = new int[] {237,375,506,633,752,872};
	int[] diff= new int[] {0,138,131,127,119,120};
	
	int[][][] refChart = new int[5][6][3];
	
	public Colorc(BufferedImage p) {
		test = p;
	}
	
	public Colorc() {
		try {
			source = ImageIO.read(new File("Source.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int y=0;y<6;y++) {
//			System.out.println();
			for(int x=0;x<5;x++) {
				int[] sum = new int[3];
				for(int i=-3;i<4;i++) {
					for(int j=-3;j<4;j++) {
						int[] temp = intToRGB(source.getRGB(614 + 87*x + i, points[y] + j));
						sum[0] += temp[0];
						sum[1] += temp[1];
						sum[2] += temp[2];
					}
				}
				sum[0] /= 49;
				sum[1] /= 49;
				sum[2] /= 49;
				
				
				refChart[x][y] = sum;
//				System.out.print(refChart[x][y][0] + "," + refChart[x][y][1] + "," + refChart[x][y][2] + "  ");
			}
//			System.out.println("-------------");
		}
		
	}
	
	int[] colorShift = new int[]{2,7,14};//{2,7,14};{0,0,0};
	public int[][] matchStripJON(BufferedImage s1, BufferedImage ref){
		int[][] whiteSamples = new int[5][3];// FOR WHITE SCALING
		int[][] spS = new int[6][3];
		int[][] spRS = new int[6][3];
		
		double[][] whiteScale = new double[5][3];
		
//		for(int x=0;x<5;x++) {
//			for(int i=-3;i<4;i++) {
//				for(int j=-3;j<4;j++) {
//					int[] temp = intToRGB(s1.getRGB(83 + x*102 + i, 32 + j));
//					whiteSamples[x][0] += temp[0];
//					whiteSamples[x][1] += temp[1];
//					whiteSamples[x][2] += temp[2];
//				}
//			}
//			whiteSamples[x][0]/=49;
//			whiteSamples[x][1]/=49;
//			whiteSamples[x][2]/=49;
////			System.out.println(whiteSamples[x][0] + " " + whiteSamples[x][1] + " " + whiteSamples[x][2]);
//		}
//		
//		for(int i=0;i<5;i++) {
//			whiteScale[i][0] = trueWhite[0]/whiteSamples[i][0];
//			whiteScale[i][1] = trueWhite[1]/whiteSamples[i][1];
//			whiteScale[i][2] = trueWhite[2]/whiteSamples[i][2];
//		}
		
		///////////////////////////////////////////OBTAIN AVERAGE SAMPLE IN CENTER OF SQUARE
		for(int x=0;x<6;x++) {
			int[] sum = new int[3];
			for(int i=-3;i<4;i++) {
				for(int j=-3;j<4;j++) {
					int[] temp = intToRGB(s1.getRGB(65/2 + 102*x + i,65/2 + j));
					sum[0] += temp[0];
					sum[1] += temp[1];
					sum[2] += temp[2];
				}
			}
			sum[0] /= 49;
			sum[1] /= 49;
			sum[2] /= 49;
			
			spS[x] = sum;
		}
		for(int x=0;x<6;x++) {
			int[] sum = new int[3];
			for(int i=-3;i<4;i++) {
				for(int j=-3;j<4;j++) {
					int[] temp = intToRGB(ref.getRGB(65/2 + 102*x + i,65/2 + j));
					sum[0] += temp[0];
					sum[1] += temp[1];
					sum[2] += temp[2];
				}
			}
			sum[0] /= 49;
			sum[1] /= 49;
			sum[2] /= 49;
			
			spRS[x] = sum;
		}
		///////////////////////////////////OBTAIN AVERAGE SAMPLE IN CENTER OF SQUARE		END
		
		
		///rescale spS whiteValue to more like the chart.
		//USE linear interpolation between points to determine whiteness.
		
		
		
//		for(int k=0;k<5;k++) {
//			System.out.println("WHITE: " + whiteScale[k][0] + " " + whiteScale[k][1] + " " + whiteScale[k][2]);
//		}
		
		for(int i=0;i<6;i++) {
			for(int k=0;k<3;k++) {
				spS[i][k] += colorShift[k];
			}
		}
		
		
//		for(int i=0;i<6;i++) {
//			System.out.println("spS " + spS[i][0] + " " + spS[i][1] + " " + spS[i][2]);
//		}
//		System.out.println("SPS======================");
		
//		spS[0][0] = (int) (spS[0][0]*whiteScale[0][0]);
//		spS[0][1] = (int) (spS[0][1]*whiteScale[0][1]);
//		spS[0][2] = (int) (spS[0][2]*whiteScale[0][2]);
//		
//		spS[1][0] = (int) (spS[1][0]*(whiteScale[0][0]*0.5 + whiteScale[1][0]*0.5));
//		spS[1][1] = (int) (spS[1][1]*(whiteScale[0][1]*0.5 + whiteScale[1][1]*0.5));
//		spS[1][2] = (int) (spS[1][2]*(whiteScale[0][2]*0.5 + whiteScale[1][2]*0.5));
//		
//		spS[2][0] = (int) (spS[2][0]*(whiteScale[1][0]*0.5 + whiteScale[2][0]*0.5));
//		spS[2][1] = (int) (spS[2][1]*(whiteScale[1][1]*0.5 + whiteScale[2][1]*0.5));
//		spS[2][2] = (int) (spS[2][2]*(whiteScale[1][2]*0.5 + whiteScale[2][2]*0.5));
//		
//		spS[3][0] = (int) (spS[3][0]*(whiteScale[2][0]*0.5 + whiteScale[3][0]*0.5));
//		spS[3][1] = (int) (spS[3][1]*(whiteScale[2][1]*0.5 + whiteScale[3][1]*0.5));
//		spS[3][2] = (int) (spS[3][2]*(whiteScale[2][2]*0.5 + whiteScale[3][2]*0.5));
//
//		spS[4][0] = (int) (spS[4][0]*(whiteScale[3][0]*0.5 + whiteScale[4][0]*0.5));
//		spS[4][1] = (int) (spS[4][1]*(whiteScale[3][1]*0.5 + whiteScale[4][1]*0.5));
//		spS[4][2] = (int) (spS[4][2]*(whiteScale[3][2]*0.5 + whiteScale[4][2]*0.5));
//		
//		spS[5][0] = (int) (spS[5][0]*whiteScale[4][0]);
//		spS[5][1] = (int) (spS[5][1]*whiteScale[4][1]);
//		spS[5][2] = (int) (spS[5][2]*whiteScale[4][2]);
		
//		for(int i=0;i<6;i++) {
//			System.out.println("spS " + spS[i][0] + " " + spS[i][1] + " " + spS[i][2]);
//		}
//		System.out.println("SPS======================");
		
		//rescale 		END
		
		double[][] vectorS = new double[6][3];
		double[][] vectorRS = new double[6][3];
		double[][][] vectorC = new double[5][6][3];
		
		for(int i=0;i<6;i++) {
			double mag = Math.sqrt(spS[i][0]*spS[i][0] + spS[i][1]*spS[i][1] + spS[i][2]*spS[i][2]);
			vectorS[i][0] = spS[i][0]/mag;
			vectorS[i][1] = spS[i][1]/mag;
			vectorS[i][2] = spS[i][2]/mag;
			
			mag = Math.sqrt(spRS[i][0]*spRS[i][0] + spRS[i][1]*spRS[i][1] + spRS[i][2]*spRS[i][2]);
			vectorRS[i][0] = spRS[i][0]/mag;
			vectorRS[i][1] = spRS[i][1]/mag;
			vectorRS[i][2] = spRS[i][2]/mag;
			
			for(int k=0;k<5;k++) {
				mag = Math.sqrt(refChart[k][i][0]*refChart[k][i][0] + refChart[k][i][1]*refChart[k][i][1] + refChart[k][i][2]*refChart[k][i][2]);
				vectorC[k][i][0] = refChart[k][i][0]/mag;
				vectorC[k][i][1] = refChart[k][i][1]/mag;
				vectorC[k][i][2] = refChart[k][i][2]/mag;
			}
		}
		
		int[][] choices = new int[6][2];
		
		for(int i=0;i<6;i++) {
//			System.out.println("TEST " + i);
			double minS = 100000.0;
			double minR = 100000.0;
			int choiceS = -1;
			int choiceR = -1;
			for(int k=0;k<5;k++) {
				double dist1 = Math.sqrt(Math.pow(vectorS[i][0]-vectorC[k][i][0], 2) + Math.pow(vectorS[i][1]-vectorC[k][i][1], 2) + Math.pow(vectorS[i][2]-vectorC[k][i][2], 2));
				double dist2 = Math.sqrt(Math.pow(vectorRS[i][0]-vectorC[k][i][0], 2) + Math.pow(vectorRS[i][1]-vectorC[k][i][1], 2) + Math.pow(vectorRS[i][2]-vectorC[k][i][2], 2));
				
				if(dist1<minS || (dist1>minS&&minS>10000.0)) {
					minS = dist1;
					choiceS = k;
				}
				if(dist2<minR || (dist2>minR&&minR>10000.0)) {
					minR = dist2;
					choiceR = k;
				}
//				System.out.println("D1: " + dist1 + " D2: " + dist2);
			}
			choices[i][0] = choiceS;
			choices[i][1] = choiceR;
//			System.out.println("DECISIONS: " + choiceS + " " + choiceR);
//			System.out.println("==============================");
		}
		
		
		return choices;
		
		
	}
	
	public int[][] matchStrip(BufferedImage s1, BufferedImage ref) {
		double[][][][] LabSTRIP = new double[65][s1.getHeight()][6][3];
		double[][][][] LabCHART = new double[442][121][6][3];
		
		
		//CONVERT IMAGES TO LAB SPACE
		for(int j=0;j<6;j++) {
			for(int x=0;x<65;x++) {
				for(int y=0;y<s1.getHeight();y++) {
					int[] temp = intToRGB(s1.getRGB(x + j*102, y));
//					s1.setRGB(x + j*102, y, 000000);
					LabSTRIP[x][y][j] = rgbToLab(temp[0],temp[1],temp[2]);
				}
			}
		}
		
		int accum = 0;
		for(int j=0;j<6;j++) {
			accum+=diff[j];
			for(int x=0;x<442;x++) {
				for(int y=0;y<121;y++) {
					int[] temp = intToRGB(source.getRGB(563 + x,179 + y + accum));
//					source.setRGB(563 + x,179 + y + accum, 000000);
					LabCHART[x][y][j] = rgbToLab(temp[0],temp[1],temp[2]);
				}
			}
		}
		//CONVERT IMAGES TO LAB SPACE			END
		
		
		File debug1 = new File("DEBUG source.png");
		if(!debug1.exists()) {
			try {
				ImageIO.write(source, "png", debug1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File debug2 = new File("DEBUG test.png");
			try {
				ImageIO.write(s1, "png", debug2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		double[][] meanChart = new double[6][3];
		double[][] stdChart = new double[6][3];
		double[][] meanS = new double[6][3];
		double[][] stdS = new double[6][3];
		
		for(int j=0;j<6;j++) {
		for(int x=0;x<65;x++) {
			for(int y=0;y<s1.getHeight();y++) {
				double[] temp = LabSTRIP[x][y][j];
				meanS[j][0] += temp[0];
				meanS[j][1] += temp[1];
				meanS[j][2] += temp[2];
				stdS[j][0] += temp[0]*temp[0];
				stdS[j][1] += temp[1]*temp[1];
				stdS[j][2] += temp[2]*temp[2];
			}
		}
		
		meanS[j][0] /= s1.getWidth()*s1.getHeight()/6;
		meanS[j][1] /= s1.getWidth()*s1.getHeight()/6;
		meanS[j][2] /= s1.getWidth()*s1.getHeight()/6;
		stdS[j][0] /= s1.getWidth()*s1.getHeight()/6;
		stdS[j][1] /= s1.getWidth()*s1.getHeight()/6;
		stdS[j][2] /= s1.getWidth()*s1.getHeight()/6;
		
		stdS[j][0] = Math.sqrt(stdS[j][0] - meanS[j][0]);
		stdS[j][1] = Math.sqrt(stdS[j][1] - meanS[j][1]);
		stdS[j][2] = Math.sqrt(stdS[j][2] - meanS[j][2]);
		}
		
		
		for(int j=0;j<6;j++) {
		for(int x=0;x<LabCHART.length;x++) {
			for(int y=0;y<LabCHART[0].length;y++) {
				double[] temp = LabCHART[x][y][j];
				meanChart[j][0] += temp[0];
				meanChart[j][1] += temp[1];
				meanChart[j][2] += temp[2];
				stdChart[j][0] += temp[0]*temp[0];
				stdChart[j][1] += temp[1]*temp[1];
				stdChart[j][2] += temp[2]*temp[2];
			}
		}

		meanChart[j][0] /= 442*121;
		meanChart[j][1] /= 442*121;
		meanChart[j][2] /= 442*121;
		stdChart[j][0] /= 442*121;;
		stdChart[j][1] /= 442*121;
		stdChart[j][2] /= 442*121;
		
		stdChart[j][0] = Math.sqrt(stdChart[j][0] - meanChart[j][0]);
		stdChart[j][1] = Math.sqrt(stdChart[j][1] - meanChart[j][1]);
		stdChart[j][2] = Math.sqrt(stdChart[j][2] - meanChart[j][2]);
		}
		
		for(int j=0;j<6;j++) {
			for(int x=0;x<65;x++) {
				for(int y=0;y<s1.getHeight();y++) {
					LabSTRIP[x][y][j][0] = LabSTRIP[x][y][j][0] - meanS[j][0];
					LabSTRIP[x][y][j][1] = LabSTRIP[x][y][j][1] - meanS[j][1];
					LabSTRIP[x][y][j][2] = LabSTRIP[x][y][j][2] - meanS[j][2];
					
					
					LabSTRIP[x][y][j][0] = LabSTRIP[x][y][j][0] * stdS[j][0]/(stdChart[j][0]*0.15);
					LabSTRIP[x][y][j][1] = LabSTRIP[x][y][j][1] * stdS[j][1]/(stdChart[j][1]*0.15);
					LabSTRIP[x][y][j][2] = LabSTRIP[x][y][j][2] * stdS[j][2]/(stdChart[j][2]*0.15);
					
					LabSTRIP[x][y][j][0] = LabSTRIP[x][y][j][0] + meanChart[j][0];
					LabSTRIP[x][y][j][1] = LabSTRIP[x][y][j][1] + meanChart[j][1];
					LabSTRIP[x][y][j][2] = LabSTRIP[x][y][j][2] + meanChart[j][2];
					
					
//					LabSTRIP[x][y][j][0] = Math.max(Math.min(LabSTRIP[x][y][j][0],255),0);
//					LabSTRIP[x][y][j][1] = Math.max(Math.min(LabSTRIP[x][y][j][1],255),0);
//					LabSTRIP[x][y][j][2] = Math.max(Math.min(LabSTRIP[x][y][j][2],255),0);
				}
			}
		}
		
		
		
		double[][] sampleSLab = new double[6][3];
		double[][] sampleREFLab = new double[6][3];
		for(int x=0;x<6;x++) {
			double[] sum = new double[3];
			for(int i=-3;i<4;i++) {
				for(int j=-3;j<4;j++) {
					double[] temp = LabSTRIP[65/2 + i][65/2 + j][x];
					sum[0] += temp[0];
					sum[1] += temp[1];
					sum[2] += temp[2];
				}
			}
			sum[0] /= 49;
			sum[1] /= 49;
			sum[2] /= 49;
			
			sampleSLab[x] = sum;
		}
		
		for(int x=0;x<6;x++) {
			int[] sum = new int[3];
			for(int i=-3;i<4;i++) {
				for(int j=-3;j<4;j++) {
					int[] temp = intToRGB(ref.getRGB(65/2 + 102*x + i,65/2 + j));
					sum[0] += temp[0];
					sum[1] += temp[1];
					sum[2] += temp[2];
				}
			}
			sum[0] /= 49;
			sum[1] /= 49;
			sum[2] /= 49;
			
			sampleREFLab[x] = rgbToLab(sum[0],sum[1],sum[2]);
		}
		
		
		
		int[][] choices = new int[6][2];
		
		for(int y=0;y<6;y++) {
//			System.out.println("TEST " + y);
			double minS = 100000.0;
			double minR = 100000.0;
			int choiceS = -1;
			int choiceR = -1;
			for(int x=0;x<5;x++) {
//				System.out.println();
				double t1 = CIEDE(rgbToLab(refChart[x][y][0],refChart[x][y][1],refChart[x][y][2]),sampleSLab[y]);
				if(t1<minS || (t1>minS&&minS>10000.0)) {
					minS = t1;
					choiceS = x;
				}
//				System.out.println("-----------------------");
				double t2 = CIEDE(rgbToLab(refChart[x][y][0],refChart[x][y][1],refChart[x][y][2]),sampleREFLab[y]);
				if(t2<minR || (t2>minR&&minR>10000.0)) {
					minR = t2;
					choiceR = x;
				}
//				System.out.println("Sample " + sampleSLab[y][0] + " "  + sampleSLab[y][1] + " "  + sampleSLab[y][2]);
//				System.out.println("T1: " + t1 + " T2: " + t2);
			}
			choices[y][0] = choiceS;
			choices[y][1] = choiceR;
//			System.out.println("DECISIONS: " + choiceS + " " + choiceR);
//			System.out.println("===============================");
		}
		
		
		return choices;
	}
	
	public int[] intToRGB(int d) {
		int red = (d & 0x00ff0000) >> 16;
		int green = (d & 0x0000ff00) >> 8;
		int blue = d & 0x000000ff;
		
		return new int[] {red,green,blue};
	}
	
	public int RGBToInt(int[] RGB) {
		int d = RGB[0];
		d = (d<<8) + RGB[1];
		d = (d<<8) + RGB[2];
		
		return d;
	}
	
	public void loadImage(BufferedImage p) {
		test = p;
	}
	
	public void process() {
		
	}
	
	public double CIEDE(double[] Lab1, double[] Lab2) {
		//www2.ece.rochester.edu/~gsharma/ciede2000/ciede2000noteCRNA.pdf
		
		double KL = 1.0;
		double KC = 1.0;
		double KH = 1.0;
		
		double C1 = Math.sqrt(Lab1[1]*Lab1[1] + Lab1[2]*Lab1[2]);
		double C2 = Math.sqrt(Lab2[1]*Lab2[1] + Lab2[2]*Lab2[2]);
		
		double C = (C1+C2)/2;
		
		double G = 0.5*(1 - Math.sqrt(Math.pow(C, 7)/(Math.pow(C, 7)+Math.pow(25, 7))));
		
		double h1 = (Math.atan(Lab1[2]/Lab1[1]) + 2*Math.PI)*180/Math.PI;
		double h2 = (Math.atan(Lab2[2]/Lab2[1]) + 2*Math.PI)*180/Math.PI;
		
		double dL = Lab2[0]-Lab1[0];
		double dC = C2-C1;
		
		double dh = 0;
		
		if(C1*C2>0) {
			if(Math.abs(h2-h1)<=180) {
				dh = h2-h1;
			}else if(h2-h1>180) {
				dh = h2-h1-360;
			}else if(h2-h1<180){
				dh = h2-h1+360;
			}else {
				System.out.println("CIDE ERROR");
			}
		}
		
		
		double dH = 2*Math.sqrt(C1*C2)*Math.sin(dh*Math.PI/360);
		
		double Lavg = (Lab1[0]+Lab2[0])/2;
		double Cavg = (C1+C2)/2;
		
		double hbar = h1+h2;
		
		if(C1*C2>0) {
			if(Math.abs(h1-h2)<=180) {
				hbar = (h2+h1)/2;
			}else if(Math.abs(h1-h2)>180 && h1+h2<360) {
				dh = (h1+h2+360)/2;
			}else if(Math.abs(h1-h2)>180 && h1+h2>=360){
				dh = (h1+h2-360)/2;
			}else {
				System.out.println("CIDE ERROR");
			}
		}
		
		double T = 1 - 0.17*Math.cos(hbar*Math.PI/180 - 30*Math.PI/180) + 0.24*Math.cos(2*hbar*Math.PI/180) + 0.32*Math.cos(3*hbar*Math.PI/180 + 6*Math.PI/180) - 0.2*Math.cos(4*hbar*Math.PI/180 - 63*Math.PI/180);
		
		
		double dtheta = 30*Math.exp(-Math.pow((hbar - 275)/25, 2));
		double Rc = 2*Math.sqrt(Math.pow(C, 7)/(Math.pow(C, 7)+Math.pow(25, 7)));
		
		double SL = 1 + (0.0015*(Lavg-50)*(Lavg-50))/(Math.sqrt(20+(Lavg-50)*(Lavg-50)));
		double SC = 1 + 0.045*Cavg;
		double SH = 1 + 0.015*Cavg*T;
		double RT = -Math.sin(2*dtheta)*Rc;
		
		double result = Math.sqrt(Math.pow(dL/(KL*SL), 2)+Math.pow(dC/(KC*SC), 2)+Math.pow(dH/(KH*SH), 2)+RT*(dC/(KC*SC))*(dH/(KH*SH)));
		
		return result;
	}
	
	public double[] rgbToLab(int R, int G, int B) {
		//https://stackoverflow.com/questions/4593469/java-how-to-convert-rgb-color-to-cie-lab

	    double r, g, b, X, Y, Z, xr, yr, zr;

	    // D65/2°
	    double Xr = 95.047;  
	    double Yr = 100.0;
	    double Zr = 108.883;


	    // --------- RGB to XYZ ---------//

	    r = R/255.0;
	    g = G/255.0;
	    b = B/255.0;

	    if (r > 0.04045)
	        r = Math.pow((r+0.055)/1.055,2.4);
	    else
	        r = r/12.92;

	    if (g > 0.04045)
	        g = Math.pow((g+0.055)/1.055,2.4);
	    else
	        g = g/12.92;

	    if (b > 0.04045)
	        b = Math.pow((b+0.055)/1.055,2.4);
	    else
	        b = b/12.92 ;

	    r*=100;
	    g*=100;
	    b*=100;

	    X =  0.4124*r + 0.3576*g + 0.1805*b;
	    Y =  0.2126*r + 0.7152*g + 0.0722*b;
	    Z =  0.0193*r + 0.1192*g + 0.9505*b;


	    // --------- XYZ to Lab --------- //

	    xr = X/Xr;
	    yr = Y/Yr;
	    zr = Z/Zr;

	    if ( xr > 0.008856 )
	        xr =  (float) Math.pow(xr, 1/3.);
	    else
	        xr = (float) ((7.787 * xr) + 16 / 116.0);

	    if ( yr > 0.008856 )
	        yr =  (float) Math.pow(yr, 1/3.);
	    else
	        yr = (float) ((7.787 * yr) + 16 / 116.0);

	    if ( zr > 0.008856 )
	        zr =  (float) Math.pow(zr, 1/3.);
	    else
	        zr = (float) ((7.787 * zr) + 16 / 116.0);


	    double[] lab = new double[3];

	    lab[0] = (116*yr)-16;
	    lab[1] = 500*(xr-yr); 
	    lab[2] = 200*(yr-zr); 

	    return lab;

	} 
}
