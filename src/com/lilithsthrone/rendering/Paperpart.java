package com.lilithsthrone.rendering;

import java.util.ArrayList;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.types.BodyPartType;
import com.lilithsthrone.game.character.race.AbstractSubspecies;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class Paperpart extends Paperdoll {	//Paperpart manages individual instances of body part artwork

	Pmage compimage;
	BodyPartType part;
	ArrayList<Integer> Parent = new ArrayList<Integer>();
	ArrayList<ArrayList<Integer>> Children = new ArrayList<ArrayList<Integer>>();
	int renderZ;

	public Paperpart(
		String name,
		String filetype,
		AbstractSubspecies supspecies,
		BodyPartType part) {
		// TODO Auto-generated constructor stub
	}
	
	public Paperpart(GameCharacter npc, int sd) {
		this(npc.getName(), "jg", npc.getSubspecies(), BodyPartType.ARM);
	}
	
	public String readData() {
		return null;
		//finds xml sheet and reads its data
		//look at savefile read and write for tips
		// Game.java.504, 652, 715
	}

}
