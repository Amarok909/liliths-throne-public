package com.lilithsthrone.game.dialogue.romance;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.1.0
 * @version 0.3.7.3
 * @author Amarok
 */
public class RomanceUtil implements XMLSaving {

//************ Variables ************//
	public static final float MARRIAGE_PASSION = 80f;	// minimum amount of passion needed to get married
	public static final int MAX_POLYCULE = 3;			// max size of a polycule/marriage, excluding PC. if 0, no max limit
	
	public static Map<Long, List<String>> dateLog;
//	main.game.getMinutesPassed();
	
	public RomanceUtil() {
		this.dateLog = new HashMap<>();
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
	public ArrayList<String> pickedDecisions = new ArrayList<String>();
	{
		for(int i = 0; i < 6; i++) {
			this.pickedDecisions.add("");
		}
	}

	public LocalDateTime prepTime;
	public LocalDateTime proposalTime;
	
	// methods
	/**
	 * a list of all the npcs that are to be wed
	 */
	public static ArrayList<GameCharacter> pickedPartners() {return getMain().pickedPartners;}
	
	public static HashMap<String, String> pickedOutfits() {return getMain().pickedOutfits;}
	
	/**
	 * an array list containing info on the picked Location, Ceremony, Colours, Flowers, Banners, Honeymoon
	 */
	public static ArrayList<String> pickedDecisions() {return getMain().pickedDecisions;}
	
	public static boolean partnerLimit() {return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit);}

	public static boolean priorManorHouse() {return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.priorManorHouse);}
	
	public static void resetMarriagePlanner() {
		getMain().pickedPartners.clear();
		getMain().pickedOutfits.clear();
		getMain().pickedDecisions.clear();
		
		getMain().prepTime = null;
		getMain().proposalTime = null;
		
		for(int i = 0; i < 6; i++) {
			getMain().pickedDecisions.add("");
		}
	}
	
	public static void setProposalTimes() {
		LocalDateTime date = Main.game.getDateNow();
		getMain().prepTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 9, 0, 0, 0).plusDays(1);
		getMain().proposalTime = getMain().prepTime.plusDays(7);
	}

	public static boolean canPropose() {
		LocalDateTime now = Main.game.getDateNow();
		LocalDateTime prepTime = getMain().prepTime;
		LocalDateTime proposalTime = getMain().proposalTime;
		return !(prepTime==null || proposalTime==null) && now.isAfter(prepTime) && now.isBefore(proposalTime);
	}

//************ Other Utils ************//
	
	public static boolean checkCompatibility(ArrayList<GameCharacter> input, Boolean passions, Boolean marriage) {
		ArrayList<GameCharacter> npc = new ArrayList<>();
		npc.addAll(input);
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
	
	public static boolean checkCompatibility(Boolean passions, Boolean marriage, GameCharacter... npc) {
		return checkCompatibility(Util.newArrayListOfValues(npc), passions, marriage);
	}

	public static int totalCost() {
		int cost = 0;

		for(Entry<String, String> E : getMain().pickedOutfits.entrySet()) {
			cost += Integer.valueOf(E.getValue().split("\\|")[2].trim());
		}
		
		for(String S : getMain().pickedDecisions) {
			cost += Integer.valueOf(S.split("\\|")[2].trim());
		}
		
		return cost;
	}

	public static String costBreakdown() {
		StringBuilder sb = new StringBuilder("<p>");
		
		// dresses
		for(Entry<String, String> E : pickedOutfits().entrySet()) {
			String id = E.getKey();
			String dress = E.getValue().split("\\|")[0].trim();
			int costs = Integer.valueOf(E.getValue().split("\\|")[2].trim());
			
			if(id.equals("NOT_SET")) {
				sb.append("Officiant's ");
				
			} else if(id.equals(Main.game.getPlayer().getId())) {
				sb.append("Your ");
				
			} else {
				try {
					GameCharacter npc = Main.game.getNPCById(id);
					sb.append(UtilText.parse(npc, "[npc.NamePos] "));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			sb.append(dress + ": [style.moneyFormat("+costs+", span)]</br>");
		}
		
		sb.append("</p><p>");
		
		// all else
		ArrayList<String> titles = new ArrayList<>(Arrays.asList("Venue", "Colour Pallet", "Floristry", "Banners", "Ceremony", "Honeymoon"));
		for(int i = 0; i < pickedDecisions().size(); i++) {
			
			String T = pickedDecisions().get(i).split("\\|")[0].trim();
			int C = Integer.valueOf(pickedDecisions().get(i).split("\\|")[2].trim());
			
			sb.append(titles.get(i) + " (" +T+ "): [style.moneyFormat("+C+", span)] </br>");
		}
		
		sb.append("</p>");
		
		return UtilText.parse(sb.toString());
	}
	

}
