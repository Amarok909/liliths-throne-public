package com.lilithsthrone.rendering;

import java.io.*;
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

//	https://www.tutorialspoint.com/javafx/javafx_images.htm
//	https://community.oracle.com/tech/developers/discussion/2450090/save-the-image-painted-by-javafx-to-the-disk
//	https://community.oracle.com/tech/developers/discussion/2456310/using-vector-graphics-with-fxml-and-javafx
//	https://stackoverflow.com/questions/40447825/how-do-i-go-from-an-array-of-pixel-values-to-an-image-file-in-java
//	https://codereview.stackexchange.com/questions/244123/fastest-way-to-create-random-pixel-image
//	https://stackoverflow.com/questions/16433915/how-to-copy-file-from-one-location-to-another-location
//	https://codereview.stackexchange.com/questions/146603/image-processing-rotation?rq=1

//	https://www.tutorialspoint.com/javafx/javafx_overview.htm

//	https://www.flaticon.com/

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.lilithsthrone.main.Main;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

@SuppressWarnings("unused")

public class Paperdoll {

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
	
	public static File getRandomImageFromFolder(File input) {
		try {
			if(input.exists() && input.isDirectory()) {
				File[] imageList = input.listFiles((path, filename) -> filename.endsWith(".jpg"));
				int rnd = new Random().nextInt(imageList.length);
			//	return getImage(imageList[rnd]);
				File selected = imageList[rnd];
				return selected;
			}
				return null;
		} catch(Exception e) {
			
		}
		return null;
	}
	
	public static String FiletoString(File image) {
		if(image.exists()) {
			return "Happy";
		}	return "Sad";
		//return image.getAbsolutePath();
		
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
		
		int xLenght = 4000;
		int yLenght = 7000;
		BufferedImage img = new BufferedImage(xLenght, yLenght, BufferedImage.TYPE_INT_ARGB); 
		
		for (int y = 0; y < yLenght; y++) 
			{ 
				for (int x = 0; x < xLenght; x++) 
				{ 
				//	int a = (int)(Math.random()*256); //generating 
					int a, r, b, g;
					int boundary = 200;
					double radius = Math.sqrt(Math.pow((x - xLenght/2),2) + Math.pow((y - yLenght/2),2));
					double gradient = (Math.cos((Math.PI/2)*(radius/boundary)));
					if(radius <= boundary) {	//0,0 is upper left
						a = 255;
						r = (int)(gradient*(Math.random()*256));
						g = (int)((3*gradient/4+0.25)*140);
						b = (int)((gradient/2+0.5)*240);
					} else {
						if(x<=128 || x>=xLenght-128 || (y>=x+yLenght*7/16 && y<=x+yLenght*3/4)) {a = 0;	}
						else {a = (int)(y*255/yLenght);	}
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
			ImageIO.write(img, "png", new File(saveLocation));
			
		//    ImageIO.write(img, "png", new File(System.getProperty("user.dir")+"/"+dir));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: " + e);
			System.out.println("Error: " + e);
		}
		
	//	FileOutputStream out = new FileOutputStream();
		
	}

}
