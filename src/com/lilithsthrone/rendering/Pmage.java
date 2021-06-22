package com.lilithsthrone.rendering;

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
	 * name, format
	 * origin coordinates (relative to top left corner)
	 * corner coordinates
	 * rotation
	 */

	BufferedImage image;
	int width;
	int height;
	String name;
	String format;
	String folder = "res/images/simulcrum";
	
	ArrayList<Integer> origin = new ArrayList<Integer>();	// relative to image top left corner, which is the true origin of every image, will be location of parent node in Paperbody
	static ArrayList<Integer> truezero = new ArrayList<Integer>(Arrays.asList(0,0));
	ArrayList<ArrayList<Integer>> corners = new ArrayList<ArrayList<Integer>>();		//TL, TR, BL, BR
	
	double rotation;
//	double rotationActual;
	
// Constructors
	public Pmage(BufferedImage image, ArrayList<Integer> origin, String name, String format) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		this.name = name;
		this.format = format;
		
		this.origin = origin;
		this.corners = findCorners(this.width, this.height, this.origin);
	//	System.err.println("A1origin: " + origin.get(0) + ", " + origin.get(1));
	}
	
	public Pmage(String path, ArrayList<Integer> origin, String name, String format) {
		this(Paperdoll.getImage(path), origin, name, format);
	}
	
	public Pmage(BufferedImage i, ArrayList<Integer> o) {
		this(i, o, "Autogen", "png");
	}
	
	public Pmage(BufferedImage i) {
		this(i, truezero);
	}
	
	
// Image methods
	public BufferedImage getImage() {return image;}
	
	public void setImage(BufferedImage i) {
		this.image = i;
	}
	
	
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
	
	public void logData() {
		System.err.println("width: " + width + "px");
		System.err.println("height: " + height + "px");
		System.err.println("name: " + name);
		System.err.println("format: " + format);
		System.err.println("origin: " + origin.get(0) + ", " + origin.get(1));
		for(int i = 0; i < this.corners.size(); i++) {
			System.err.println("corner "+i+": " + this.corners.get(i).get(0) + ", " + this.corners.get(i).get(1));
		}
	}
	
	
// Origin methods
	public ArrayList<Integer> getOrigin() {
		return this.origin;
	}
	
	public void setOrigin(ArrayList<Integer> o) {
		this.origin = o;
		this.corners = findCorners(this.width, this.height, this.origin, this.rotation);	//value actually needs to be updated, it's not a void idiot
	//	System.err.println("Set1origin: " + o.get(0) + ", " + o.get(1));
	//	System.err.println("Set2origin: " + this.origin.get(0) + ", " + this.origin.get(1));
	}
	
	public void setOrigin(int X, int Y) {
		setOrigin(new ArrayList<>(Arrays.asList(X, Y)));
	}
	
	public void setOffsetOrigin(int X, int Y) {
		X += this.origin.get(0);
		Y += this.origin.get(1);
		setOrigin(X, Y);
	}
	
// Math methods
	private ArrayList<ArrayList<Integer>> findCorners(int width, int height, ArrayList<Integer> o, double rotation) {
		// corner coords are relative to origin. eg, if O(4,2), then TL(-4,-2), TR(6,-2), BR(6,8), BL(-4,8) on a 10x10 image
		// REMEMBER, images are positive right and down
	//	origin = origin==null? truezero : origin;
	//	System.err.println("Forigin: " + this.origin.get(0) + ", " + this.origin.get(1));
	//	System.err.println("Oorigin: " + o.get(0) + ", " + o.get(1));
		
		int X = o.get(0);
		int Y = o.get(1);
		ArrayList<Integer> TL = new ArrayList<Integer>(Arrays.asList(-X, -Y));
		ArrayList<Integer> TR = new ArrayList<Integer>(Arrays.asList(width-X, -Y));
		ArrayList<Integer> BR = new ArrayList<Integer>(Arrays.asList(width-X, height-Y));
		ArrayList<Integer> BL = new ArrayList<Integer>(Arrays.asList(-X, height-Y));
		
		ArrayList<ArrayList<Integer>> corners = new ArrayList<ArrayList<Integer>>(Arrays.asList(TL, TR, BR, BL));
		
		if(rotation==0) {
			return corners;	
		} else {
			rotation = Math.toRadians(rotation);	//anti clockwise is positive, opposite graphics.rotate(), same as general math
			
			for(int i = 0; i < corners.size(); i++) {
				int px = corners.get(i).get(0);
				int py = corners.get(i).get(1);
				int ox = o.get(0);
				int oy = o.get(1);
				
				double ppx = Math.cos(rotation) * (px-ox) - Math.sin(rotation) * (py-oy) + ox;
				double ppy = Math.sin(rotation) * (px-ox) + Math.cos(rotation) * (py-oy) + oy;
				
				X = (int) Math.round(ppx); 
				Y = (int) Math.round(ppy); 

				corners.set(i, new ArrayList<Integer>(Arrays.asList(X, Y)));
			}
			return corners;
		}
	}
	
	private ArrayList<ArrayList<Integer>> findCorners(int width, int height, ArrayList<Integer> o) {
		return findCorners(width, height, o, 0);
	}
	
	@SuppressWarnings("unused")
	private Pmage rotate(Pmage input, double rotation) {
		BufferedImage img = input.getImage();
		img = Paperdoll.rotateImage(img, rotation);
		this.image = img;
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.corners = findCorners(width, height, origin);
		return this;
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
			Paperdoll.exportImage(Paperdoll.universalExport, p.name, p.format, p.image);
		}
	}
	
	public void bulkExport() {
		bulkExport(this, this, this);
	}
	
	public Pmage collapse() {
		return this;
	}

}
