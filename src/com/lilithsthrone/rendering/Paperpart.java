package com.lilithsthrone.rendering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.race.AbstractSubspecies;
import com.lilithsthrone.main.Main;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class Paperpart extends Paperdoll {	//Paperpart manages individual instances of body part artwork

	Pmage compimage;
	BodyParts part;
	
	Paperpart parent;
	ArrayList<Paperpart> children;
	
	ArrayList<Integer> parentCoords = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> childrenCoords = new ArrayList<ArrayList<Integer>>();
	double rotation;	// relative to parent

	ArrayList<Integer> rootCoordsABS = new ArrayList<Integer>();
	double rotationABS;	// relative to root

	int renderZ;
	int treeLvl = 0;
	
	File xmlurl;
	
	public Paperpart(
		String name,
		String filetype,
		GameCharacter npc,
		AbstractSubspecies supspecies,
		BodyParts part) {
		
		this.xmlurl = new File("res/images/primitives/" + npc.getSubspecies() + "/" + npc.getSubspecies() + ".xml");
		
		this.parentCoords = compimage.origin;
		Paperdoll.colecto.add(this);

		if(this.isRoot()) {
			this.treeLvl = 0;
		} else {
			this.treeLvl = this.parent.treeLvl + 1;
		}
	}
	
	public Paperpart(GameCharacter npc, int sd) {
		this(npc.getName(), "jpg", npc.getSubspecies(), BodyParts.HEAD);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean isLeaf() {
		return children.size() == 0;
	}
	
	public String readData() {
		try {
			Document doc = Main.getDocBuilder().parse(xmlurl);
			
			// Cast magic:
			doc.getDocumentElement().normalize();
			
			Element metadataElement = (Element) doc.getElementsByTagName("metadata").item(0);
			// run metadata funtions
			
			Element bodyElement = (Element) doc.getElementsByTagName("body").item(0);
			
			Element gameElement = (Element) bodyElement.getElementsByTagName(part.getName()).item(0);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		//finds xml sheet and reads its data
		//look at savefile read and write for tips
		// Game.java.504, 652, 715
	}

	public ArrayList<Integer> traceCoords() {
		PaperPart ref = this;
		ArrayList<Paperpart> refList = new ArrayList<Paperpart>();

		do {
			reflList.add(ref);
			ref = ref.parent;
		} while(!ref.isRoot());

		int relX, relY, relRot;
		for(i = refList.size() - 1; i >= 0; i--) {
			if(!refList.get(i).isLeaf) {
				Paperpart P = refList.get(i);
				Paperpart C = refList.get(i-1);
			}
		}
	}

}
