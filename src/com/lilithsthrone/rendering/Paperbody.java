package com.lilithsthrone.rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.BodyPartType;
import com.lilithsthrone.game.character.npc.NPC;

public class Paperbody {

	ArrayList<BodyPartType> grisly = new ArrayList<BodyPartType>(Arrays.asList(
			BodyPartType.FACE,
			BodyPartType.SKIN,
			BodyPartType.ARM,
			BodyPartType.LEG));
	
	ArrayList<Paperpart> mooo = new ArrayList<Paperpart>();
	HashMap<BodyPartType, Paperpart> species = new HashMap<BodyPartType, Paperpart>();
	
	public Paperbody() {
	//	this.species;
	}
	
	/*
	 * Creates a BodyPartType:Paperpart map, linking each Podyparttype with a matching Paperpart
	 * the Paperpart cointains into on its parent and children nodes, a Pmage (with origin set by Paperpart looking in folders)
	 * https://stackoverflow.com/questions/40230249/java-create-multiple-hashmaps-and-fill-them-using-a-for-loop-is-there-a-bett
	 * https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java?rq=1
	 */
	public HashMap<BodyPartType, Paperpart> generate (NPC npc) {		
		for(BodyPartType part : grisly) {
			this.species.put(part, new Paperpart(null, null, npc.getSubspecies(), part));
		}
		return species;
	}

}
