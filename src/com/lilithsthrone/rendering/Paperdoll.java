package com.lilithsthrone.rendering;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

import java.awt.Graphics2D;

//	https://www.tutorialspoint.com/javafx/javafx_images.htm
//	https://community.oracle.com/tech/developers/discussion/2450090/save-the-image-painted-by-javafx-to-the-disk
//	https://community.oracle.com/tech/developers/discussion/2456310/using-vector-graphics-with-fxml-and-javafx
//	https://stackoverflow.com/questions/40447825/how-do-i-go-from-an-array-of-pixel-values-to-an-image-file-in-java
//	https://codereview.stackexchange.com/questions/244123/fastest-way-to-create-random-pixel-image
//	https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
//	https://codereview.stackexchange.com/questions/146603/image-processing-rotation?rq=1

//	https://stackoverflow.com/questions/5905868/how-to-rotate-jpeg-images-based-on-the-orientation-metadata

//	https://www.tutorialspoint.com/javafx/javafx_overview.htm

//	https://www.flaticon.com/

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.lilithsthrone.main.Main;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

@SuppressWarnings("unused")

public class Paperdoll {
	
//	List<Integer> coords = Arrays.asList(1, 2, 3);
//	String name;

//	public Paperdoll() {
//		// TODO Auto-generated constructor stub
//	}
	
	//**** Image Management ****//
	//**** Paper-doll ****//
	
	//**** Development ****//
	public static BufferedImage getImage(File input) {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error: "+e+", file"+input+"does not exist");
		}
		return null;
	}
	
	public static BufferedImage getImage (String path) {
		return getImage(new File(path));
	}
	
	
	public static File getRandomImageFromFolder(File input) {
		if(input.exists()) {
			FilenameFilter textFilter = new FilenameFilter() {
				public boolean accept(File input, String name) {
					return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg");
				}
			};
			
		//	List<File> direct = new List<>();
			File[] imageList = input.listFiles(textFilter);
			if(imageList!=null) {
				int rnd = new Random().nextInt(imageList.length);
			//  return getImage(imageList[rnd]);
				return imageList[rnd];
			}
		//	for(File subFile : dir.listFiles(textFilter)) {
				
		//	};
			
			return null;
		}	return null;
	}
	
	public static String FiletoString(File image) {
		if(image.exists() && image.isFile()) {
			return "Happy "+ image.getPath();
		} else {
			return "Sad";
		}
		//return image.getAbsolutePath();
		
	}
	
	public static String FtS(String path) {
		return FiletoString(new File(path));
	}
	
	public static void exportImage(String location, String name, String format, BufferedImage img) {
		try {
			int saveNumber = 0;
		//	String saveLocation = "res/images/primitives/test_species/Autogen.png";
			String saveLocation = location + "/" + name + "." + format;
			while(new File(saveLocation).exists()) {
				saveNumber++;
				saveLocation = "res/images/primitives/test_species/Autogen"+saveNumber+".png";
			}	ImageIO.write(img, format, new File(saveLocation));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: " + e + ", image " + location + "/" + name + "." + format + "failed to save to disk");
		}
	}
	
	public static void Expo() {		//This is confirmed to work
		System.err.println("Err");
	//  System.out.println("Err");
		System.err.println(System.getProperty("user.dir"));
	}
	
	public static boolean Extwo() {
		File dir = new File("res/images/primitives/test_species/test_species.xml");
		
		if(dir.exists()) {
			return true;
		}
			return false;
		
	}
	
	public static void TestExport () {
		System.err.println("Exop: ");
	// System.out.println("Exop: ");
		System.err.println(System.getProperty("user.dir"));
		
	//   File dir = new File("res/images/primitives/test_species/Autogen.png");
	//   dir.mkdir();
		
		int xLength = 2000;
		int yLength = 3500;
		BufferedImage img = new BufferedImage(xLength, yLength, BufferedImage.TYPE_INT_ARGB); 
		
		for (int x = 0; x < xLength; x++) { 
				for (int y = 0; y < yLength; y++) { 
				//	int a = (int)(Math.random()*256); //generating 
					int a, r, b, g;
					int boundary = (int)(xLength*0.45);
					double radius = Math.sqrt(Math.pow((x - xLength/2),2) + Math.pow((y - yLength/2),2));
					double gradient = (Math.cos((Math.PI/2)*(radius/boundary)));
					if(radius <= boundary) {	//0,0 is upper left
						a = 255;
						r = (int)(gradient*(Math.random()*256));
						g = (int)((3*gradient/4+0.25)*140);
						b = (int)((gradient/2+0.5)*240);
					} else if((y<=yLength/2+xLength/2)
					&& (3*Math.sin((Math.PI/2)*(x/60))
						+ 2*Math.cos((Math.PI/2)*(x/30)) + 1 >= 0)
					&& (2*Math.cos((Math.PI/2)*(y/45))
						+ 3*Math.sin((Math.PI/2)*(y/30)) + 2 >= 0)) {
						a = 0;
						r = 255;
						g = 255;
						b = 255;
					} else {
						if(x<=128 || x>=xLength-128 || (y>=x+yLength*7/16 && y<=x+yLength*3/4)) {a = 0;	}
						else {a = (int)(y*255/yLength);	}
						r = (int)(Math.random()*256); //values
						g = (int)(Math.random()*256); //less than
						b = (int)(Math.random()*256); //256
					}

					int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel

					img.setRGB(x, y, p); 
				} 
			} 
		
		try {
			int saveNumber = 0;
			String saveLocation = "res/images/primitives/test_species/Autogen.png";
			while(new File(saveLocation).exists()) {
				saveNumber++;
				saveLocation = "res/images/primitives/test_species/Autogen"+saveNumber+".png";
			}
			File dir = new File(saveLocation);
			ImageIO.write(img, "png", dir);
			System.err.print(dir.getAbsolutePath());
			
		//    ImageIO.write(img, "png", new File(System.getProperty("user.dir")+"/"+dir));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: " + e);
			System.out.println("Error: " + e);
		}
		
	//	FileOutputStream out = new FileOutputStream();
		
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
	public static BufferedImage addOval(BufferedImage base) {
		// Constructs a BufferedImage of one of the predefined image types.
    //    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 
        // Create a graphics which can be used to draw into the buffered image
     //   Graphics2D g2d = bufferedImage.createGraphics();
		Graphics2D g2d = base.createGraphics();
	//	base.gra
		
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
        g2d.drawString("Java Code Geeks", 50, 120);
        g2d.fill3DRect((int)(0.7*X), (int)(0.95*Y), 700, 250, false);
 
        // Disposes of this graphics context and releases any system resources that it is using. 
        g2d.dispose();
 
     //   g2d.drawImage(base, null, 0, 0);
        
        // Save as PNG
     //   File file = new File("myimage.png");
       // ImageIO.write(bufferedImage, "png", file);
 
        // Save as JPEG
      //  file = new File("myimage.jpg");
      //  ImageIO.write(bufferedImage, "jpg", file);
        
        return base;
	}
	
	public void save(BufferedImage dPanel)
	{
	    BufferedImage bImg = new BufferedImage(dPanel.getWidth(), dPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D cg = bImg.createGraphics();
	//    ((BufferedImage) dPanel).paintAll(cg);
	    try {
	            if (ImageIO.write(bImg, "png", new File("./output_image.png")))
	            {
	                System.out.println("-- saved");
	            }
	    } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	    }
	}
	
//	protected Paperdoll(List<Integer> coords, String name) {
//		this.coords = coords;
//		this.name = name;
//	};
	
}
