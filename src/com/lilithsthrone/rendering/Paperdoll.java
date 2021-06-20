package com.lilithsthrone.rendering;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import com.lilithsthrone.rendering.Pmage;
import com.lilithsthrone.utils.Util;

import javafx.application.Application; 

import javafx.scene.Group;  
import javafx.scene.Scene; 

import javafx.scene.image.Image; 
import javafx.scene.image.ImageView; 
import javafx.scene.image.PixelReader; 
import javafx.scene.image.PixelWriter; 
import javafx.scene.image.WritableImage;
 
import javafx.scene.paint.Color; 
import javafx.stage.Stage;

import java.awt.Font;
import java.awt.Graphics2D;

//	https://www.tutorialspoint.com/javafx/javafx_images.htm
//	https://community.oracle.com/tech/developers/discussion/2450090/save-the-image-painted-by-javafx-to-the-disk
//	https://community.oracle.com/tech/developers/discussion/2456310/using-vector-graphics-with-fxml-and-javafx
//	https://stackoverflow.com/questions/40447825/how-do-i-go-from-an-array-of-pixel-values-to-an-image-file-in-java
//	https://codereview.stackexchange.com/questions/244123/fastest-way-to-create-random-pixel-image
//	https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
//	https://codereview.stackexchange.com/questions/146603/image-processing-rotation?rq=1
//	https://www.dreamincode.net/forums/topic/166263-converting-graphics2d-to-bufferedimage-how/

//	https://stackoverflow.com/questions/5905868/how-to-rotate-jpeg-images-based-on-the-orientation-metadata

//	https://www.tutorialspoint.com/javafx/javafx_overview.htm

//	https://www.flaticon.com/

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.lilithsthrone.main.Main;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

@SuppressWarnings("unused")

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class Paperdoll {
	/* Paperdoll is the universal managager for all classes and methods related to the paperdoll system
	 * It contains the code for retriving, modifying, and exporting images
	 */
	
	static String lastExported;
	static String unviersalExport = "res/images/simulcrum";
	
	
//**** Image Input ****//
	public static BufferedImage getImage(File input) {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			System.err.println("Error: "+e+", file"+input+"does not exist");
		}	return null;
	}

	public static BufferedImage getImage (String path) {
		return getImage(new File(path));
	}

//**** Image Output ****//
	public static void exportImage(String location, String name, String format, BufferedImage img) {
		try {
			int saveNumber = 0;
			String saveLocation = location + "/" + name + "." + format;
			while(new File(saveLocation).exists()) {
				saveNumber++;
				saveLocation = location + "/" + name + saveNumber + "." + format;
			}
			ImageIO.write(img, format, new File(saveLocation));
			Paperdoll.lastExported = saveLocation;
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: " + e + ", image " + location + "/" + name + "." + format + "failed to save to disk");
		}
	}
	
	public static String lastExported() {
		if(lastExported!=null) {
			return lastExported;
		} else {
			File input = new File("res/images/simulcrum");
			if(input.exists()) {
				FilenameFilter textFilter = new FilenameFilter() {
					public boolean accept(File input, String name) {
						return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
					}
				};
				File[] imageList = input.listFiles(textFilter);
				if(imageList!=null) {
					File oldestFile = imageList[0];
					for(int i=0; i<imageList.length; i++) {
						if(imageList[i].lastModified()>oldestFile.lastModified()) oldestFile=imageList[i];
					}	return oldestFile.getPath();
				}
			}	return null;
		}
	}

//**** Image File Management ****//
	public static File getRandomFileFromFolder(File input) {
		if(input.exists()) {
			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File input, String name) {
					return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
				}
			};
			File[] imageList = input.listFiles(textFilter);
			if(imageList!=null) {
				int rnd = new Random().nextInt(imageList.length);
				//  return getImage(imageList[rnd]);
				return imageList[rnd];
			}
		}	return null;
	}
	
	public static String FiletoString(File image) {
		if(image.exists() && image.isFile()) {
			return image.getPath();
		}	return null;
	}
	
	public static String FtS(String path) {
		return FiletoString(new File(path));
	}
	
	
//**** Image Data Manipulation ****//
	public static BufferedImage scaleDown(BufferedImage input) {
		return CachedImage.scaleDown(input, 300, 445);
	}
	
	
//**** Paper-doll ****//

//**** Development ****//
	public static boolean Extwo() {
		File dir = new File("res/images/primitives/test_species/test_species.xml");
		if(dir.exists()) {
			return true;
		}	return false;
	}
	
	public static BufferedImage TestExport () {
		int xLength = 2000*0+300*8;
		int yLength = 3500*0+445*8;
		BufferedImage img = new BufferedImage(xLength, yLength, BufferedImage.TYPE_INT_ARGB); 
		
		for (int x = 0; x < xLength; x++) { 
				for (int y = 0; y < yLength; y++) { 
				//	int a = (int)(Math.random()*256); //generating 
					int a, r, b, g;
					double i = Math.floor(x/1)*1;
					double j = Math.floor(y/1)*1;
					int boundary = (int)(xLength*0.45);
					double radius = Math.sqrt(Math.pow((i - xLength/2),2) + Math.pow((j - yLength/2),2));
					double gradient = (Math.cos((Math.PI/2)*(radius/boundary)));
					
					img.setRGB(x, y, ((100<<24) | (30<<16) | (50<<8) | 150));

					if(radius <= boundary) {	//0,0 is upper left
						a = 255;
						r = (int)(gradient*(Math.random()*256));
						g = (int)((3*gradient/4+0.25)*140);
						b = (int)((gradient/2+0.5)*240);
					} else if((y<=yLength/2+xLength/2)
					&& (3*Math.sin((Math.PI/2)*(i/60))
						+ 2*Math.cos((Math.PI/2)*(i/30)) + 1 >= 0)
					&& (2*Math.cos((Math.PI/2)*(j/45))
						+ 3*Math.sin((Math.PI/2)*(j/30)) + 2 >= 0)) {
						a = 0;
						r = 255;
						g = 255;
						b = 255;
					} else {
						if(x<=128 || x>=xLength-128 || (j>=i+yLength*7/16 && j<=i+yLength*3/4)) {a = 0;	}
						else {a = (int)(y*255/yLength);	}
						r = (int)(Math.random()*256); //values
						g = (int)(Math.random()*256); //less than
						b = (int)(Math.random()*256); //256
					}
					int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel
					img.setRGB(x, y, p); 
				} 
			}
		Pmage dan = new Pmage(img, new ArrayList<>(Arrays.asList(0, 0)));
		Pmage bob = new Pmage(img);
		BufferedImage pete = dan.function2().function1(bob).function2().getImage();
		exportImage("res/images/simulcrum", "Autogen", "png", pete);
		return pete;
	}

	public static BufferedImage TestTessellate (BufferedImage base, Integer X, Integer Y) {
		int baseXLength = base.getWidth();
		int baseYLength = base.getHeight();
		int baseType = base.getType();
		
		int xLength = X*baseXLength;
		int yLength = Y*baseYLength;
		BufferedImage img = new BufferedImage(xLength, yLength, baseType); 
		
		for (int x = 0; x < xLength; x++) { 
			for (int y = 0; y < yLength; y++) {
				int i = Math.floorMod(x, baseXLength);
				int j = Math.floorMod(y, baseYLength);
				int p = base.getRGB(i, j);
				img.setRGB(x, y, p);
			}
		}
		System.err.println(baseXLength+" "+baseYLength);
		System.err.println(xLength+" "+yLength);
		return img;
	}
	
	public static BufferedImage addRibbon(BufferedImage base, int height2) {
		int baseXLength = base.getWidth();
		int baseYLength = base.getHeight();
		int baseType = base.getType();
		int height = Math.min(height2, baseYLength/2);
		
	//	BufferedImage img = new BufferedImage(baseXLength, baseYLength, baseType); 
		int max = baseYLength/2+height;
		int min = baseYLength/2-height;
		
		for (int x = 0; x < baseXLength; x++) { 
			double cos = (Math.floorMod(Math.abs(x), 750))/750;//Math.cos(x/300);
			cos = cos*height;
			for (int y = 0; y < baseYLength; y++) {
				if(x - y <= 700) {
			//	if((y<=cos+max) && (y>=-cos+min)) {
					int p = (200<<24) | (250<<16) | (5<<8) | 5; //pixel
					base.setRGB(x, y, p);
				
				}	continue;
			}
		}
		return base;
	}
	
//	https://examples.javacodegeeks.com/desktop-java/imageio/create-image-file-from-graphics-object/
//	https://www.baeldung.com/java-add-text-to-image
	public static BufferedImage addOval(BufferedImage base) {
		Graphics2D g2d = base.createGraphics();

		int X = base.getWidth();
		int Y = base.getHeight();
 
        // fill all the image with white
        g2d.setColor(java.awt.Color.RED);
        g2d.fillRect(0, 0, 300, 300);
 
        // create a circle with black
        g2d.setColor(java.awt.Color.BLACK);
        g2d.fillOval(0, 0, 600, 200);
 
        // create a string with yellow
        g2d.setColor(java.awt.Color.YELLOW);
		Font font = new Font("Monospaced", Font.BOLD, 75);
		g2d.setFont(font);
        g2d.drawString("Java Code Geeks", 50, 120);
        g2d.fill3DRect((int)(0.7*X), (int)(0.95*Y), 900, 450, false);
 
        // Disposes of this graphics context and releases any system resources that it is using. 
        g2d.dispose();
		return base;
	}
	
	public static BufferedImage addSquare(BufferedImage base) {
		int baseXLength = base.getWidth();
		int baseYLength = base.getHeight();
		
		int X = (int) (Math.random()*baseXLength*0.5);
		int Y = (int) (Math.random()*baseYLength*0.5);
		int height = Math.min(2000, baseYLength/3);
		height = Math.min(height, baseXLength/3);
		
		for (int x = Math.max(X-height, 0); x < Math.min(X+height, baseXLength); x++) { 
			for (int y = Math.max(Y-height, 0); y < Math.min(Y+height, baseYLength); y++) {
				int p = (100<<24) | (30<<16) | (50<<8) | 150; //pixel
				base.setRGB(x, y, p);
			}
		}
		return base;	
	}
	
	//	http://jens-na.github.io/2013/11/06/java-how-to-concat-buffered-images/
	public static BufferedImage layerImagesbyMath(BufferedImage base, BufferedImage implant, boolean cropToBase) {
		int baseXLength = base.getWidth();
		int baseYLength = base.getHeight();
		int implantXLength = implant.getWidth();
		int implantYLength = implant.getHeight();
		
		int[] TLOrigin = {300, 500};	//TopLeft origin corner for implant, [X,Y]. currently, both must be positive
		int x1, x2, y1, y2, W, H;
		x1 = TLOrigin[0];
		y1 = TLOrigin[1];
		x2 = cropToBase?Math.min(x1 + implantXLength, baseXLength):x1 + implantXLength;	//if cropping, x2 is limited to base's width, otherwise it will go as far as the implant needs it to
		y2 = cropToBase?Math.min(y1 + implantYLength, baseYLength):y1 + implantYLength;
		W = Math.max(x2, baseXLength);	// ensures that whether being cropped or not, the output image will include all the image it needs
		H = Math.max(x2, baseYLength);
		
		BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB); 
		
		//copies the base image
		for(int x = 0; x < baseXLength; x++) {
			for(int y = 0; y < baseYLength; y++) {
				int p = base.getRGB(x, y);
				img.setRGB(x, y, p);
			}
		}
		
		//applies the implant image
		for (int x = x1; x < TLOrigin[0]+implantXLength; x++) { 
			for (int y = y1; y < TLOrigin[1]+implantYLength; y++) {
				int i = x - TLOrigin[0];
				int j = y - TLOrigin[0];
				int p = implant.getRGB(i, j);
				img.setRGB(x, y, p);
			}
		}
		return img;
	}
	
	public static BufferedImage layerImagesbyGraphics(BufferedImage base, BufferedImage implant) {
		
	/*	https://stackoverflow.com/questions/7028780/how-to-add-20-pixels-of-white-at-the-top-of-an-existing-image-file
		https://stackoverflow.com/questions/7894275/cropping-an-image-in-java
	 	Use GraphicsConfiguration.createCompatibleImage(int width, int height) to create a BufferedImage of the same width, but with a height that's +20.
		Use BufferedImage.createGraphics() to obtain the Graphics2D object of this image.
		Use Graphics.setColor(Color c) and Graphics.fillRect(int x, int y, int width, int height) to draw the white top
		Use Graphics.drawImage(Image img, int x, int y, ImageObserver observer) to draw the original image to the specified coordinates of the new image.
	*/

		Graphics2D g = base.createGraphics();
		g.setColor(new java.awt.Color(220, 180, 240, 145));
		g.fillRect(0, 0, 450, 200);
		g.drawImage(implant, 450, 200, null);
		g.drawImage(base, base.getWidth()/2, base.getHeight()/2, null);
		BufferedImage ex = Paperdoll.getImage("res/images/characters/Amber/jam/clothed1.png");
		g.drawImage(ex, 0, 400, null);
		g.drawImage(ex, 0, 400+1*ex.getHeight(), null);
		g.drawImage(ex, 0, 400+2*ex.getHeight(), null);
		g.dispose();

		return base;
	}

	public static BufferedImage rotateImage(BufferedImage input, double rotation) {
		int w = input.getWidth();
		int h = input.getHeight();
		rotation = Math.toRadians(rotation);
		int hPrime = (int) (w * Math.abs(Math.sin(rotation)) + h * Math.abs(Math.cos(rotation)));
		int wPrime = (int) (h * Math.abs(Math.sin(rotation)) + w * Math.abs(Math.cos(rotation)));
		
		BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = rotatedImage.createGraphics();
		g.setColor(new java.awt.Color(20, 180, 240, 145));
		//	g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
		
		g.fillRect(0, 0, 450, hPrime);
		g.fillRect(wPrime-450, 0, 450, hPrime);
		g.fillRect(0, 0, wPrime, 450);
		//	g.fillRect(0, hPrime-450, wPrime, 450);
		
		g.translate(wPrime/2, hPrime/2);
		g.rotate(rotation);
		g.translate(-w/2, -h/2);
		g.drawImage(input, 0, 0, null);
		g.dispose();  // release used resources before g is garbage-collected
		
		Graphics2D g2 = rotatedImage.createGraphics();		// declaring a new Graphics2d reseets any translate and rotate effects on the canvas
		g2.setColor(new java.awt.Color(20, 180, 240, 145));
		g2.fillRect(450, hPrime-450, wPrime-900, 450);	//bottom bar, anti tranform hopeful
		g2.dispose();
		
		return rotatedImage;
	}
	
	public static void ExperimentMethod() {
//		Paperdoll.TestExport();
		
		BufferedImage baseimg = Paperdoll.getImage(Paperdoll.getRandomFileFromFolder(new File("res/images/simulcrum")));
		BufferedImage neonimg = Paperdoll.TestTessellate(baseimg, 3, 2);
	//	BufferedImage neonimg = Paperdoll.addRibbon(baseimg, 2000);
		neonimg = Paperdoll.addOval(baseimg);
		neonimg = Paperdoll.TestTessellate(neonimg, 3, 2);
		neonimg = Paperdoll.addSquare(neonimg);
	//	Paperdoll.exportImage("res/images/simulcrum", "Autogen", "png", neonimg);
	//	baseimg = Paperdoll.rotateImage(Paperdoll.TestExport(), (Util.random.nextInt(13)-6)*15);
	//	Paperdoll.exportImage("res/images/simulcrum", "Autogen", "png", baseimg);
		
		neonimg = Paperdoll.rotateImage(neonimg, (Util.random.nextInt(13)-6)*15);
	//	Paperdoll.exportImage("res/images/simulcrum", "Autogen", "png", neonimg);
	//	Paperdoll.exportImage("res/images/simulcrum", "Autogen", "png", Paperdoll.scaleDown(neonimg));
		
	//	BufferedImage smol = Paperdoll.scaleDown(neonimg);
		BufferedImage smol = layerImagesbyGraphics(neonimg, baseimg);
		Paperdoll.exportImage("res/images/simulcrum", "Autogen", "png", smol);
	//	Pmage expo = new Pmage(smol).setFullName("Autogenous", "png");		//not quite working
	//	expo.bulkExport();
		
		// TODO Auto-generated method stub
		
	}
	
//	protected Paperdoll(List<Integer> coords, String name) {
//		this.coords = coords;
//		this.name = name;
//	};
	
}
