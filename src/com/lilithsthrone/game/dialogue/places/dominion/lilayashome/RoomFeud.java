package com.lilithsthrone.game.dialogue.places.dominion.lilayashome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lilithsthrone.game.character.attributes.AffectionLevel;
import com.lilithsthrone.game.character.attributes.AffectionLevelBasic;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.attributes.ObedienceLevelBasic;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.NPCFlagValue;
import com.lilithsthrone.game.character.npc.dominion.Scarlett;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.companions.OccupantDialogue;
import com.lilithsthrone.game.dialogue.companions.SlaveDialogue;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJob;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJobSetting;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;

public class RoomFeud {
	
	static List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
	static List<NPC> greetings = charactersPresent.stream().filter(npc -> npc.hasSlaveJobSetting(SlaveJob.BEDROOM, SlaveJobSetting.BEDROOM_GREETING)).collect(Collectors.toList());
	static List<NPC> greetingsNice = greetings.stream().filter(npc -> npc.getObedienceBasic()==ObedienceLevelBasic.OBEDIENT || npc.getAffectionLevelBasic(Main.game.getPlayer())==AffectionLevelBasic.LIKE).collect(Collectors.toList());
	static List<NPC> greetingsRude = greetings.stream().filter(npc -> npc.getObedienceBasic()!=ObedienceLevelBasic.OBEDIENT && npc.getAffectionLevelBasic(Main.game.getPlayer())!=AffectionLevelBasic.LIKE).collect(Collectors.toList());
	
	private static int commandTool = 0;
	private static int commandVibe = 0;
	
	private static int isDiscordantStart() {
		float sumRelations = 0;
		float minRelations = 100;
		
		for(NPC nice : greetingsNice) {
			for(NPC rude : greetingsRude) {
				sumRelations += rude.getAffection(nice);
				sumRelations += nice.getAffection(rude);
				minRelations = Math.min(minRelations, rude.getAffection(nice));
				minRelations = Math.min(minRelations, nice.getAffection(rude));
			}
		}
	
		sumRelations = sumRelations/(2 * greetingsNice.size() * greetingsRude.size());
		
		if(sumRelations < AffectionLevel.ZERO_NEUTRAL.getMinimumValue()) {
			return 0;
		} else if(minRelations < AffectionLevel.NEGATIVE_ONE_ANNOYED.getMedianValue() && Util.random.nextInt(5)<3) {
			return 0;
		}
		return 1;
	}
	
	private static float sumRelations() {
		float sumRelations = 0;
		float minRelations = 100;
		
		for(NPC nice : greetingsNice) {
			for(NPC rude : greetingsRude) {
				sumRelations += rude.getAffection(nice);
				sumRelations += nice.getAffection(rude);
				minRelations = Math.min(minRelations, rude.getAffection(nice));
				minRelations = Math.min(minRelations, nice.getAffection(rude));
			}
		}
	
		sumRelations = sumRelations/(2 * greetingsNice.size() * greetingsRude.size());
		return sumRelations;
	}
	
	public static final DialogueNode FEUD_START = new DialogueNode("Attempt resolution", "Try and resolve the standoff between your slaves", true, true) {

		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public void applyPreParsingEffects() {
			for(NPC npc : greetings) {
					npc.NPCFlagValues.add(NPCFlagValue.flagSlaveResolved);
				}
			RoomPlayer.applySleep(5);
		}
		
		@Override
		public String getContent() {
			Main.game.getTextStartStringBuilder().append(Util.intToString(Math.round(sumRelations())));
			Main.game.getTextStartStringBuilder().append(sumRelations());
			
			StringBuilder sb = new StringBuilder();
			if(isDiscordantStart()==0) {			//Bad Relations
				sb.append("<p>"
						+ "Deciding that now is the best time to try an fix this stouch between your slaves,"
						+ " and having some idea of what you want to say to them, you open your mouth, ready to lecture them—"
						+ " but are cut off as RUDE suddenly speaks up.<br/>"
						
						+ "Oh what, going to keep ignoring us are you, fine by me bitches, all you do is kiss up to <i>Master</i> pc's ass."
						+ " yep, you two are both certainly his pet bitches through and through, RUDE2 says smugly before shaking her head, no self respect at all, these slaves"
						+ " 'Oh master, i've been such a good boy, pet me, pet me I'm such a gooood little slave' lampoons RUDE3, mockingly impersonating NICE.<br/>"
						
						+ "The only dogs here are you barking mad cunts, you think you can just act like that to Pc and us, and actually get away with it!?"
						+ " Exactly, you realise how unbearable you lot are? your attitude to everything is so damn annoying to put up with."
						+ " 'Well, what can you do, it's not RUDE's fault that she's a brain-dead cow. Heck, she can't even tell food apart from scrap' smirks NICE3, 'right RUDE'"
						+ "</p>");
			} else if(isDiscordantStart()==1) {		//Mixed Relations
				sb.append("<p>"
						+ "Deciding that now is the best time to try fix this stouch between your slaves,"
						+ " and having some idea of what you want to say to them, you clear your throat. Gaining their attention, you prepare to lecture them."
						+ " Look, I realize there are some personal issues between you, but if this feud isn't resolved then—<br/>"
						
						+ "Oh? <i>issues?</i> Like how NICE is a insufferable biych who keeps kissing up to your ass, RUDE snaps, interrupting you, 'guess that's why you have slave'"
						+ " yep, all that ass kissing must have rotted all of your brains, or maybe you were just always full of shit, adds RUDE2, smirking as NICE and NICE2's faces start twitching"
						+ " well, it would explain a lot, RUDE3 chimes in, a look of bitter contept on her face.<br/>"
						
						+ "NICE's head snaps towards them, The fuck you say skank!? you leave Pc out of this you deluded whore because the only rotten ones here are you two"
						+ " Exactly, you realise how unbearable you lot are? your whinging drives <i>everyone</i> in this mansion to insanity"
						+ " nah, the only insane one here is RUDE, it's what happens when you end up as cum-for-brain slut like her"
						+ "</p>");
			}
			
			sb.append("<p>"
					+ "standing back you try and shout overthem, in an attempt to shut them up.<br/>"
					
					+ "tempers flaring and snarky comments giving way to outright verbal abuse, the two groups move from their sides of the room to get in each other's faces."
					+ " size nor sex matters, the battle lings have been drawn and neithr side is going to back down now"
					+ "and although NPC is outnumbered, that dosen't stop her from treating the others.<br/>"
					+ ""
					+ "as they start barking in each other's faces, you realize you need to do something fast before someone throws a punch, or they get loud enough that liliya storms up here.<br/>"
					+ ""
					+ "realizing that you have lost control of the situation, and that it'll be impossible to get an edge in otherwise.<br/>"
					+ ""
					+ "desperate to regain control of the situation, you quickly look around your room for anything you could use to silence your slaves."
					+ " with a flash of realisation, you decide to..."
					+ "</p>");
			return sb.toString();
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==2) {
				
				if(Main.game.getPlayer().getAttributeValue(Attribute.MAJOR_ARCANE)<50) {
					return new Response("Arcane Overpower", "As you have not achieved <b>arcane prowess</b>,"
						+ " you don't have enough arcane power to make everyone horny", null);
				}
				
				return new Response("Arcane Overpower", "Flare up your powerful arcane aura to make everyone too horny to fight", FEUD_IN_CHARGE) {
					@Override public void effects() {commandTool = 1;}
				};
				
			} else if(index==1) {
				
				return new Response("Newspaper", "Roll up the newspaper that rose delivers to you daily, and use it to whap everyone's heads", FEUD_IN_CHARGE) {
					@Override public void effects() {commandTool = 2;}
				};
				
			} else if(index==3) {
				
				return new Response("Weapon", "Threaten to use your weaponry upon your slaves if they don't start behaving right now", FEUD_IN_CHARGE) {
					@Override public void effects() {commandTool = 3;}
				};
			}
			return null;
		}
	};
	
	
	public static final DialogueNode FEUD_IN_CHARGE = new DialogueNode("Show who's in charge", "Get your slaves to shut up, so you can fix things", false, true) {

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
	
//	public static final DialogueNode FEUD_ADMONISH = new DialogueNode("Admonish their behaviour", "Tell them off for how they're acting", true, true) {};
	
	
//	public static final DialogueNode FEUD_CONVINCE = new DialogueNode("Shut down and convince", "Convince your renegade slaves that they are better off under your care", true, true) {};
	
	
//	public static final DialogueNode FEUD_SMARMY = new DialogueNode("Shut down pompous slaves", "Your loyal slaves seem to have gotten big heads, deflate thm so it doesn't cause issues", true, true) {};
	
	
//	public static final DialogueNode FEUD_BONDING = new DialogueNode("Bonding exercise", "Now that you have gotten everyone's emotions and obedience towards you under control, do something together", true, true) {};
	
	
	
	
	
	
	
	public static final DialogueNode FEUD_ERUPTION = new DialogueNode("Argument", "The argument erupts", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		public void effects22() {
			List<NPC> charactersPresent = LilayaHomeGeneric.getSlavesAndOccupantsPresent();
			for(NPC npc : charactersPresent) {
				npc.NPCFlagValues.add(NPCFlagValue.flagSlaveResolved);
			}
			int sleepTimeInMinutes = 30;
			RoomPlayer.applySleep(sleepTimeInMinutes);
			
			Main.game.getTextEndStringBuilder().append("<p style='text-align:center'><i>You leave your clothes outside of your bathroom so that they can be cleaned while you wash yourself...</i></p>");
			Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().applyWash(true, false, StatusEffect.CLEANED_SHOWER, 240+30));
		}
		
		@Override
		public String getContent() {
			effects22();
			effects22();
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/lab", "LILAYA_EXPLAINS_ESSENCES_2");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 4) {
				return new Response("Listen", "Listen as Lilaya shows you how to use your stored essences in order to enchant items.", Lab.LILAYA_EXPLAINS_ESSENCES_3) {
					@Override
					public void effects() {
						effects22();
					}
				};

			} else if(index == 3) {
				return new Response("Resolve situation", "Try and resolve the feud among your slaves.</br><i>Work in progress</i>", RoomPlayer.AUNT_HOME_PLAYERS_ROOM_SLEEP) {

					NPC character = Main.game.getNpc(Scarlett.class);
					
					@Override
					public Colour getHighlightColour() {
						return character.getFemininity().getColour();
					}
					@Override
					public void effects() {
						if(character.isSlave()) {
							SlaveDialogue.initDialogue(character, false);
						} else {
							OccupantDialogue.initDialogue(character, false, false);
						}
					}
				
				};
						
			} else if(index <= 8 && index > 0) {
				return new Response("Gattai", "Lorem Ispum Dolor", RoomPlayer.AUNT_HOME_PLAYERS_ROOM_SLEEP) {
				//	PlayerCharacter character = Main.game.getPlayer();
				//	@Override
					public Colour getHighlightColour() {
				//		return character.getFemininity().getColour();
						return Main.game.getPlayer().getSlavesOwnedAsCharacters().get(Util.random.nextInt(Main.game.getPlayer().getSlavesOwned().size())).getFemininity().getColour();
					}
		
				};
				
			} else if(index <= 18 && index > 0) {
				return new Response("GURREN LAGGAN", "Reach Heaven Through Violence", RoomPlayer.AUNT_HOME_PLAYERS_ROOM_SLEEP) {
					@Override
					public void effects() {
						effects22();
					}
				//	PlayerCharacter character = Main.game.getPlayer();
				//	@Override
					public Colour getHighlightColour() {
				//		return character.getFemininity().getColour();
						return Main.game.getPlayer().getSlavesOwnedAsCharacters().get(Util.random.nextInt(Main.game.getPlayer().getSlavesOwned().size())).getFemininity().getColour();
					}
		
				};
				
			} else if(index <= 1000 && index > 0){
				return new Response("Null: "+index, "ERRORERRORERRORERRORERROR", null);	//null;
			} else {
				return null;
			}
		};
		

	};
	
}
