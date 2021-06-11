package com.lilithsthrone.game.dialogue.companions;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;

/**
 * @since 0.3.21
 * @version 0.3.21
 * @author Amarok
 */
public class RomanceDialogue {
// Dating
	//Guest
	//Offspring
	//Submissive clubbers
	public static final DialogueNode CONTACT_OFFER_DATE = new DialogueNode("Go on a date", "take your firend off for a date", true, true) {

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

	//Dominant clubbers
	/*
	Interject in dates to pay, otherwise they will
	*/
	
// Breakup
// Marriage
// Divorce
}
