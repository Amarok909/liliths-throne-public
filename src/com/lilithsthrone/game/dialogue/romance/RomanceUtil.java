package com.lilithsthrone.game.dialogue.romance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.occupantManagement.OccupancyUtil;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.1.0
 * @version 0.3.7.3
 * @author Amarok
 */
public class RomanceUtil implements XMLSaving {

//************ Constants ************//
	public static final float MARRIAGE_PASSION = 80f;	// minimum amount of passion needed to get married
	public static final int MAX_POLYCULE = 3;			// max size of a polycule/marriage, excluding PC. if 0, no max limit
	
	public RomanceUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static RomanceUtil getMain() {
		return Main.game.getRomanceUtil();
	}

//************ Import Export ************//
	
	@Override
	public Element saveAsXML(Element parentElement, Document doc) {
		// TODO Auto-generated method stub
		return null;
	}

	public static RomanceUtil loadFromXML(Element parentElement, Document doc) {
		try {
			return null;
		} catch (Exception ex) {
			System.err.println("Warning: RomanceUtil failed to import!");
			return null;
		}

	}
	
//************ Wedding Planner ************//
	
	// variables
	public ArrayList<GameCharacter> pickedPartners = new ArrayList<GameCharacter>();
	public HashMap<String, String> pickedOutfits = new HashMap<String, String>();
	public ArrayList<String> pickedDecisions = new ArrayList<String>(7);

	public LocalDateTime prepTime;
	public LocalDateTime proposalTime;
	
	// methods
	/**
	 * a list of all the npcs that are 
	 */
	public static ArrayList<GameCharacter> pickedPartners() {return Main.game.getRomanceUtil().pickedPartners;}
	
	public static HashMap<String, String> pickedOutfits() {return Main.game.getRomanceUtil().pickedOutfits;}
	
	/**
	 * an array list containing info on the picked Location, Ceremony, Colours, Flowers, Banners, Honeymoon
	 */
	public static ArrayList<String> pickedDecisions() {return Main.game.getRomanceUtil().pickedDecisions;}
	
	public static boolean partnerLimit() {return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit);}

	public static boolean priorManorHouse() {return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.priorManorHouse);}
	
	public static void resetMarriagePlanner() {
		Main.game.getRomanceUtil().pickedPartners.clear();
		Main.game.getRomanceUtil().pickedOutfits.clear();
		Main.game.getRomanceUtil().pickedDecisions.clear();
		Main.game.getRomanceUtil().prepTime = null;
		Main.game.getRomanceUtil().proposalTime = null;
	}
	
	public static void setProposalTimes() {
		LocalDateTime date = Main.game.getDateNow();
		Main.game.getRomanceUtil().prepTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0, 0, 0).plusDays(1);
		Main.game.getRomanceUtil().proposalTime = Main.game.getRomanceUtil().prepTime.plusDays(7);
	}

	//************ Other Utils ************//
	
	public static boolean checkCompatibility(ArrayList<GameCharacter> npc, Boolean passions, Boolean marriage) {
		GameCharacter pc = Main.game.getPlayer();
		if(!npc.contains(pc)) npc.add(pc);	// pc needs to be compared as well, if not in list, add

		for(int i = 0; i < npc.size(); i++) {
			for(int j = 0; j < npc.size(); j++) {
				if(npc.get(i).equals(npc.get(j))) continue;		// if comparing the same character, skip
				
				if(passions && npc.get(i).getPassion(npc.get(j)) < MARRIAGE_PASSION) return false;	// one npc does not like another enough to get married, so can't happen
				
				if(marriage && !npc.get(i).equals(pc) && 0 < RomanceUtil.MAX_POLYCULE 
						&& RomanceUtil.MAX_POLYCULE < npc.get(i).getSpouces().size()) return false;	// any npc but player has the max amount of partners or more
			}
		}

		return true;	// everyone has at least 80 passion towards everyone else and/or is not at the polycule limit
	}
	
	

}
