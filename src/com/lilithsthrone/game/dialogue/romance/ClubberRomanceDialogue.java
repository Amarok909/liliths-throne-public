package com.lilithsthrone.game.dialogue.romance;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.dialogue.places.dominion.nightlife.NightlifeDistrict;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class ClubberRomanceDialogue {
// Dating
	//Submissive clubbers (you dom)
	public static final DialogueNode CLUBBER_START_DATING = new DialogueNode("Ask out", "you really like [npc.name], and want to make [npc.him] your [npc.boyfriend].", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/ClubberRomance", "CLUBBER_START_DATING");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			if (index == 1) {
				return new Response("Stay", "Decide to stay at the club for now.", NightlifeDistrict.WATERING_HOLE_MAIN);
			} else if(index == 2) {
				return new Response("Your place", "Decide to have [npc.name] spend the night at your place.", NightlifeDistrict.WATERING_HOLE_MAIN);
			} else if(index == 3) {
				return new Response("[npc.His] place", "Decide to spend the night at [npc.his] place.", NightlifeDistrict.WATERING_HOLE_MAIN);
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CLUBBER_OFFER_DATE = new DialogueNode("Go on a date", "take your firend off for a date", true, true) {

		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return null;
		}
	};


	//Dominant clubbers (you sub)
	public static final DialogueNode CLUBBER_DOM_START_DATING = new DialogueNode("Ask out", "you really like [npc.name], and want to be [npc.his] [pc.boyfriend].", true, true) {

		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return UtilText.parseFromXMLFile("romance/ClubberRomance", "CLUBBER_DOM_START_DATING");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	/*
	Interject in dates to pay, otherwise they will
	*/

	public static final DialogueNode CLUBBER_REQUEST_DATE = new DialogueNode("", "", true, true) {

		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
// Breakup
// Marriage
// Divorce
}
