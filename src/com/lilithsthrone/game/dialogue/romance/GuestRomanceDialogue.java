package com.lilithsthrone.game.dialogue.romance;

import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class GuestRomanceDialogue {
	private static NPC occupant;

	private static NPC occupant() {return occupant;}

	public static void initDialogue(NPC targetedOccupant) {
		occupant = targetedOccupant;
	}
	
// Dating
	public static final DialogueNode GUEST_START_DATING = new DialogueNode("Ask out", "you really like [npc.name], and want to make [npc.him] your [npc.boyfriend].", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/GuestRomance", "GUEST_START_DATING");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public static final DialogueNode GUEST_OFFER_DATE = new DialogueNode("Go on a date", "take your friend off for a date", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/GuestRomance", "GUEST_OFFER_DATE");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			if(index==0) {
				return new Response("Back", "Decide against taking [npc.name] on a date, being lead on like this is sure to disappoint [npc.her]", null) {
					@Override
					public void effects() {Main.game.getActiveNPC().incrementAffection(Main.game.getPlayer(), -5);}
				};

			} else if(index==1) {
				return new Response("Plan", "Instead of going on a date right now, plan one out", GUEST_DATE_PLANNER);

			}
			return null;
		}
	};

	public static final DialogueNode GUEST_DATE_PLANNER = new DialogueNode("Date planner", "take your friend off for a date", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/GuestRomance", "GUEST_DATE_PLANNER");
		}
		
		@Override
		public String getResponseTabTitle(int index) {
			if(index == 0) return "Spontaneous";
			else if(index == 1) return "Planned";
			return null;
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			if(index==0) return new Response("Back", "go back to picking a date to go on", null);
			
			if(responseTab==0) {
				// Spontaneous dates
				if(Main.game.isDayTime()) {		//	Main.game.getHourOfDay()
					// day dates
				} else {
					// night dates
				}
				
			} else if(responseTab==1) {
				// planned dates
				if(Main.game.isDayTime()) {
					// day dates
				} else {
					// night dates
				}
			
			}
			
			return null;
		}
	};


// Breakup
// Marriage
// Divorce
}
