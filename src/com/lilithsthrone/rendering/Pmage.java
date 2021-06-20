package com.lilithsthrone.rendering;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class Pmage /*extends Paperdoll*/ {
	/* pmage is a expansion of BufferedImages
	 * is stores the following
	 * image
	 * width, height
	 * origin cordinates (relative to top left corner)
	 * corner coordinates
	 * ??rotation
	 * ??scale
	 */

	BufferedImage image;
	int width;
	int height;
	String name;
	String format;
	
	ArrayList<Integer> origin = new ArrayList<Integer>();	// relative to image top left corner, which is the true origin of every image
	ArrayList<ArrayList<Integer>> corners = new ArrayList<ArrayList<Integer>>();		//TL, TR, BL, BR
	
	double rotation;
	double scale;
	double xscale;
	double yscale;
	
// Constructors
	public Pmage(BufferedImage image, ArrayList<Integer> origin, String name, String format) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.origin = origin;
		this.corners = findCorners(width, height, origin);
		
		this.name = name;
		this.format = format;
	}
	
	public Pmage(BufferedImage image, ArrayList<Integer> origin) {
		this(image, new ArrayList<Integer>(Arrays.asList(0,0)), "Autogen", "png");
	}
	
	public Pmage(BufferedImage image) {
		this(image, new ArrayList<Integer>(Arrays.asList(0,0)));
	}
	
	
// Image methods
	public BufferedImage getImage() {return image;}
	
	
// Name methods
	public String getFullName() {return name + "." + format;}
	
	public String getName() {return name;}
	
	public String getFormat() {return format;}
	
	public void setName(String name) {this.name = name;}
	
	public void setFormat(String format) {this.format = format;}
	
	public Pmage setFullName(String name, String format) {
		this.name = name;
		this.format = format;
		return this;
	}
	
	
// Math methods
	private ArrayList<ArrayList<Integer>> findCorners(int width, int height, ArrayList<Integer> origin, double rotation) {
		// corner coords are relative to origin. eg, if O(4,2), then TL(-4,-2), TR(6,-2), BR(6,8), BL(-4,8) on a 10x10 image
		// REMEMBER, images are positive right and down
		int X = origin.get(0);
		int Y = origin.get(1);
		ArrayList<Integer> TL = new ArrayList<Integer>(Arrays.asList(-width, -height));
		ArrayList<Integer> TR = new ArrayList<Integer>(Arrays.asList(X-width,-height));
		ArrayList<Integer> BR = new ArrayList<Integer>(Arrays.asList(X-width,Y-height));
		ArrayList<Integer> BL = new ArrayList<Integer>(Arrays.asList(-width, Y-height));
		
		ArrayList<ArrayList<Integer>> corners = new ArrayList<ArrayList<Integer>>(Arrays.asList(TL, TR, BR, BL));
		
		if(rotation==0) {
			return corners;	
		} else {
			rotation = Math.toRadians(rotation);	//anti clockwise is positive, same as graphics.rotate()
			
			for(int i = 0; i < 4; i++) {
				int px = corners.get(i).get(0);
				int py = corners.get(i).get(1);
				int ox = origin.get(0);
				int oy = origin.get(1);
				
				double ppx = Math.cos(rotation) * (px-ox) - Math.sin(rotation) * (py-oy) + ox;
				double ppy = Math.sin(rotation) * (px-ox) + Math.cos(rotation) * (py-oy) + oy;
				
				X = (int) Math.round(ppx); 
				Y = (int) Math.round(ppy); 

				corners.set(i, new ArrayList<Integer>(Arrays.asList(X, Y)));
			}
			return corners;
		}
	}
	
	@SuppressWarnings("unused")
	private Pmage rotate(Pmage input, double rotation) {
		/* Some note before we start
		 * Any rotation will create a 'shadow' like this: https://stackoverflow.com/a/44087430
		 * the complexity of this can be subverted by rotating at the image's center, and then moving the origen respectivly
		 * this should achive the same effect as rotating around the origin
		 */
		int w = input.width;
	    int h = input.height;
	    rotation = Math.toRadians(rotation);
	    
	    int hPrime = (int) (w * Math.abs(Math.sin(rotation)) + h * Math.abs(Math.cos(rotation)));
	    int wPrime = (int) (h * Math.abs(Math.sin(rotation)) + w * Math.abs(Math.cos(rotation)));

	    BufferedImage rotatedImage = new BufferedImage(wPrime, hPrime, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = rotatedImage.createGraphics();
	    g.setColor(java.awt.Color.LIGHT_GRAY);
	    g.fillRect(0, 0, wPrime, hPrime);  // fill entire area
	    g.translate(wPrime/2, hPrime/2);
	    g.rotate(rotation);
	    g.translate(-w/2, -h/2);
	    g.drawImage(input.image, 0, 0, null);
	    g.dispose();  // release used resources before g is garbage-collected
	    //return rotatedImage;
	    
	    Pmage img =  new Pmage(rotatedImage, new ArrayList<Integer>(Arrays.asList(0,0)));
	    img.setRotation(rotation);
	    return img;
	
	}
	
	private ArrayList<ArrayList<Integer>> findCorners(int width, int height, ArrayList<Integer> origin) {
		return findCorners(width, height, origin, 0);
	}
	
	private double pointDistance(ArrayList<Integer> A, ArrayList<Integer> B) {
		double 	dist =	Math.pow(B.get(0) - A.get(0), 2);
				dist += Math.pow(B.get(1) - A.get(1), 2);
				dist =	Math.sqrt(dist);
		return 	dist;
	}
	
	@SuppressWarnings("unused")
	private ArrayList<Double> cornerDistance(ArrayList<Integer> Origin, ArrayList<ArrayList<Integer>> Corner) {
		ArrayList<Double> Distances = new ArrayList<Double>();
		for(ArrayList<Integer> C : Corner) {
			Distances.add(pointDistance(Origin, C));
		}	return Distances;
	}
	
	
// Rotation methods
	public double getRotation() {return this.rotation;}
	
	public void setRotation(double r) {this.rotation = r;}
	
	
// Test methods
	public Pmage function1(Pmage input) {
		return input;
	}
	
	public Pmage function2 () {
		return this;
	}
	
	public void bulkExport(Pmage... inputs) {
		for(int i = 0; i < inputs.length; i++) {
			Pmage p = inputs[i];
			Paperdoll.exportImage(Paperdoll.unviersalExport, p.name, p.format, p.image);
		}
	}
	
	public void bulkExport() {
		bulkExport(this, this, this);
	}

}
