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
	public Pmage(BufferedImage image, ArrayList<Integer> origin) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.origin = origin;
		
		// corner coords are relative to origin. eg, if O(2,2), then TL(-2,-2), TR(8,-2), BL(-2,8), BR(8,8)
		// REMEMBER, images are positive right and down
		ArrayList<Integer> TL = new ArrayList<Integer>(Arrays.asList(origin.get(0), 		origin.get(1)			));
		ArrayList<Integer> TR = new ArrayList<Integer>(Arrays.asList(origin.get(0)+width,	origin.get(1)			));
		ArrayList<Integer> BL = new ArrayList<Integer>(Arrays.asList(origin.get(0), 		origin.get(1)+height	));
		ArrayList<Integer> BR = new ArrayList<Integer>(Arrays.asList(origin.get(0)+width, 	origin.get(1)+height	));
		this.corners = new ArrayList<ArrayList<Integer>>(Arrays.asList(TL, TR, BL, BR));
	}
	
	public Pmage(BufferedImage image) {
		this(image, new ArrayList<Integer>(Arrays.asList(0,0)));
	}
	
	
	// Image methods
	public BufferedImage getPmageSource() {
		return image;
	}
	
	
	// Name methods
	public String getFullName() {return name + "." + format;}
	
	public String getName() {return name;}
	
	public String getFormat() {return format;}
	
	
	// Test methods
	public Pmage function1(Pmage input) {
		return input;
	}
	
	public Pmage function2 () {
		return this;
	}
	
	public void bulkExport(Pmage... inputs) {
		for(Pmage p : inputs) {
			Paperdoll.exportImage(Paperdoll.unviersalExport, p.name, p.format, p.image);
		}
	}

}
