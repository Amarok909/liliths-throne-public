package com.lilithsthrone.rendering;

import java.io.*;

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

//	https://www.tutorialspoint.com/javafx/javafx_overview.htm

//	https://www.flaticon.com/

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 

@SuppressWarnings("unused")

public class Paperdoll {

//	public Paperdoll() {
//		// TODO Auto-generated constructor stub
//	}
	
	public static void Expo() {		//This is confirmed to work
	    System.err.println("Err");
	//  System.out.println("Err");
	    System.out.println(System.getProperty("user.dir"));
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
	    
	    File dir = new File("res/images/primitives/myImage.png");
	    dir.mkdir();
	    
		int xLenght = 1600;
		int yLenght = 4000;
		BufferedImage img = new BufferedImage(xLenght, yLenght, BufferedImage.TYPE_INT_ARGB); 
	//	BufferedImage bi = new BufferedImage(xLenght, yLenght, BufferedImage.TYPE_INT_BGR);

	/*	for(int x = 0; x < xLenght; x++) {
		    for(int y = 0; y < yLenght; y++) {
		//      int rgbPixel = (int)img[x][y]<<16 | (int)img[x][y] << 8 | (int)img[x][y];
		    	bi.setRGB(x, y, 7);	//bi.setRGB(x, y, rgb);
		    }
		}	*/
		
		 for (int y = 0; y < yLenght; y++) 
	        { 
	            for (int x = 0; x < xLenght; x++) 
	            { 
	                int a = (int)(Math.random()*256); //generating 
	                int r = (int)(Math.random()*256); //values 
	                int g = (int)(Math.random()*256); //less than 
	                int b = (int)(Math.random()*256); //256 

	                int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel 

	                img.setRGB(x, y, p); 
	            } 
	        } 
		
		try {
		    // javax.imageio.ImageIO:
		    ImageIO.write(img, "png", dir);
		//    ImageIO.write(img, "png", new File(System.getProperty("user.dir")+"/"+dir));
		} catch (IOException e) {
		    e.printStackTrace();
		    System.err.println("Error: " + e);
		    System.out.println("Error: " + e);
		}
		
	//	FileOutputStream out = new FileOutputStream();
		
	}

}
