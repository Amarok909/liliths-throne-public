package com.lilithsthrone.game.dialogue.romance;

import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;

/**
 * @since 0.4
 * @version 0.4
 * @author Amarok
 */
public class OffspringRomanceDialogue {
// Dating
	public static final DialogueNode OFFSPRING_START_DATING = new DialogueNode("Ask out", "you really like [npc.name], and want to make [npc.him] your [npc.boyfriend].", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/OffspringRomance", "OFFSPRING_START_DATING");
		}
		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	public static final DialogueNode OFFSPRING_OFFER_DATE = new DialogueNode("Go on a date", "take your [pc.relation(npc)] off for a date", true, true) {
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/OffspringRomance", "OFFSPRING_OFFER_DATE");
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
