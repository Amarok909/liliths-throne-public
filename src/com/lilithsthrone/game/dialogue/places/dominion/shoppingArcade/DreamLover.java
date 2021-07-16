package com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade;

import java.util.ArrayList;
import java.util.Arrays;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.npc.dominion.Ashley;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.responses.ResponseTrade;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;

/**
 * @since 0.1.99
 * @version 0.4
 * @author Kumiko, Innoxia, Amarok
 */
public class DreamLover {

	static boolean attitudeFixed() {
		return Main.game.getDialogueFlags().values.contains(DialogueFlagValue.ashleyAttitude);
	}

	public static final DialogueNode EXTERIOR = new DialogueNode("Dream Lover (Exterior)", "-", false) {

		@Override
		public String getAuthor() {
			return "Kumiko";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "EXTERIOR");
		}

		@Override
		public String getResponseTabTitle(int index) {
			return ShoppingArcadeDialogue.getCoreResponseTab(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if(responseTab==0) {
				if (index == 1) {
					if(!Main.game.isExtendedWorkTime()) {
						return new Response("Enter", "Step inside 'Dream Lover'.", EXTERIOR_CLOSED);
					}
					return new Response("Enter", "Step inside 'Dream Lover'.", ENTRY);
				}
			}
			return ShoppingArcadeDialogue.getFastTravelResponses(responseTab, index);
		}
	};

	public static final DialogueNode EXTERIOR_CLOSED = new DialogueNode("Dream Lover (Exterior)", "-", false, true) {

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "EXTERIOR_CLOSED");
		}

		@Override
		public String getResponseTabTitle(int index) {
			return ShoppingArcadeDialogue.getCoreResponseTab(index);
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if(responseTab==0) {
				if (index == 1) {
					return new Response("Enter", "'Dream Lover' is currently closed, so you'll have to back at another time if you wanted to do any shopping here.", null);
				}
			}
			return ShoppingArcadeDialogue.getFastTravelResponses(responseTab, index);
		}
	};
	
	public static final DialogueNode ENTRY = new DialogueNode("Dream Lover", "-", true) {


		@Override
		public String getAuthor() {
			return "Kumiko, Amarok";
		}
		
		@Override
		public String getContent() {
			UtilText.nodeContentSB.setLength(0);
			if(Main.game.getDialogueFlags().values.contains(DialogueFlagValue.ashleyIntroduced)) {
				UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "ENTRY_REPEAT"));

				if(!Main.game.getDialogueFlags().values.contains(DialogueFlagValue.ashleyAttitude)) {
					UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "ENTRY_REPEAT_ATTITUDE"));
				}
				
				return UtilText.nodeContentSB.toString();
				
			} else {
				return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "ENTRY");
			}
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if(!Main.game.getDialogueFlags().values.contains(DialogueFlagValue.ashleyIntroduced)) {
				if (index == 1) {
					return new Response("Explore", "Explore the shelves.", EXPLORE_SHELVES) {
						@Override
						public void effects() {
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyIntroduced, true);
						}
					};
					
				} else {
					return null;
				}
				
			} else {
				
				if(index == 1) {
					return new ResponseTrade("Trade", "Wander around the shop and see what items there are for sale...", Main.game.getNpc(Ashley.class));
					
				} else if(index==2 && !attitudeFixed()) {
					return new Response("Confront Ashley", "What's with this person's attitude? Walk up to the counter and confront them about it.", CONFRONT_ASHLEY) {
						@Override
						public void effects() {
							if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleySexToysDiscovered)) {
								Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "SEX_TOY_DISCOVERY"));
							}
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleySexToysDiscovered, true);
							Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyAttitude, true);
						}
					};
					
				} else if((Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE_MARRIAGE, Quest.MARRIAGE_START) || true)		// temporary override during testing
					&& ((index==2 && attitudeFixed()) || (index==3 && !attitudeFixed()))) {
					if(Main.game.getPlayer().getSpouces().size() >= 3) {
						return new Response("Wedding Planner", "You're already married to three partners, and legally can't marry any more, so there is nothing for Ashley to do", null);
						
					} else if(Main.game.getPlayer().getFiances().size() == 0) {
						return new Response("Wedding Planner", "You're not considering marring any of your current partners, so there is nothing for Ashley to do", null);
			
					} else {
						return new Response("Wedding Planner", "You're planning to get married, and you've heard from Lilaya and Rose that Ashley is the best wedding planner in Dominion.", MARRIAGE_PLANING_START);
					}
					
				} else if(index == 0) {
					return new Response("Leave", "Head back out to the Shopping Arcade.", EXTERIOR) {
						@Override
						public void effects() {
							Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "EXIT"));
						}
					};
	
				} else {
					return null;
				}
			}
		}
	};
	
	public static final DialogueNode EXPLORE_SHELVES = new DialogueNode("Dream Lover", "-", true, true) {

		@Override
		public String getAuthor() {
			return "Kumiko";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "EXPLORE_SHELVES");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==1) {
				return new Response("Confront Ashley", "What's with this person's attitude? Walk up to the counter and confront them about it.", CONFRONT_ASHLEY) {
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "SEX_TOY_DISCOVERY"));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyAttitude, true);
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleySexToysDiscovered, true);
					}
				};
				
			} else if(index==2) {
				return new Response("Keep quiet", "It's probably best to just ignore this person's strange attitude...", IGNORE_ASHLEY) {
					@Override
					public void effects() {
						Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "SEX_TOY_DISCOVERY"));
						Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleySexToysDiscovered, true);
					}
				};
				
			}  else {
				return null;
			}
		}
	};
	
	public static final DialogueNode CONFRONT_ASHLEY = new DialogueNode("Dream Lover", "-", true, true) {

		@Override
		public String getAuthor() {
			return "Kumiko";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "CONFRONT_ASHLEY");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return ENTRY.getResponse(responseTab, index);
		}
	};
	
	public static final DialogueNode IGNORE_ASHLEY = new DialogueNode("Dream Lover", "-", true, true) {

		@Override
		public String getAuthor() {
			return "Kumiko";
		}
		
		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "IGNORE_ASHLEY");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			return ENTRY.getResponse(responseTab, index);
		}
	};

	public static ArrayList<GameCharacter> pickedPartners = new ArrayList<GameCharacter>();
	public static boolean reviewMode = false;
	public static int maxPartners = 3;
	public static ArrayList<String> pickedOutfits = new ArrayList<String>();
	public static String pickedCeremony = null;
	public static String pickedColour = null;
	public static String pickedFlowers = null;
	public static String pickedBanners = null;
	public static String pickedHoneymoon = null;

	public static void resetMarriagePlanner() {
		pickedPartners.clear();
		reviewMode = false;
		pickedOutfits.clear();
		pickedCeremony = null;
		pickedColour = null;
		pickedFlowers = null;
		pickedBanners = null;
		pickedHoneymoon = null;
	}

	public static final DialogueNode MARRIAGE_PLANING_START = new DialogueNode("Dream Lover", "-", true, false) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			ArrayList<String> names = new ArrayList<String>();
			for(GameCharacter npc : pickedPartners) {
				names.add(npc.getName());
			}
			return "hey Ashley, I need your help, im planning to get married, can you help me"
					+ "Ashley picks up at this, but seems to be eyeing you suspiciously, why use me? there's a bunch of others if you just need a certificate"
					+ "no, i want a proper ceremony, so I can show them just how much I love them, plus, my aunt's maid reckoned that you were the best at traditional one-day marrigages"
					+ "aw jeez, ok lets do this, I'll make sure you have the best, most lovely ceremony possible"
					+ "so can you tell me a bit more about your partner"
					+ ""
					+ ". Partners: " + Util.stringsToStringList(names, false);
		}
		
		@Override
		public String getResponseTabTitle(int index) {
			if(index == 0) {
				return "[style.colourTfPartial("+(pickedPartners.size()<1 || pickedPartners.get(0)==null?"Minimal":pickedPartners.get(0).getName())+")]";
			} else if(index == 1) {
				return "[style.colourTfPartial("+(pickedPartners.size()<2 || pickedPartners.get(1)==null?"Partial":pickedPartners.get(1).getName())+")]";
			} else if(index == 2) {
				return "[style.colourTfMinor("+(pickedPartners.size()<3 || pickedPartners.get(2)==null?"Minor":pickedPartners.get(2).getName())+")]";
			} else if(index == 3) {
				return "[style.colourTfLesser("+(pickedPartners.size()<4 || pickedPartners.get(3)==null?"Lesser":pickedPartners.get(3).getName())+")]";
			} else if(index == 4) {
				return "[style.colourTfGreater("+(pickedPartners.size()<5 || pickedPartners.get(4)==null?"Greater":pickedPartners.get(4).getName())+")]";
			}
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			
			if(index==0) {
			// Back
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};

			} else if(index==1) {
			// Next
				if(pickedPartners.size()==0) {
					return new Response("Next", "you need to select at least one partner before you can procede", null);
					
				} else if(pickedPartners.size() + Main.game.getPlayer().getSpouces().size() >= 4 && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit)) {		//TODO turn these into variables 
					return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_TOO_MANY) {
						@Override
						public void effects() {Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyLimit, true);}
					};
					
				} else if(pickedPartners.size() + Main.game.getPlayer().getSpouces().size() >= 4 && Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit)) {
					return new Response("Next", "ashley has already told you that you can't be married to more than three partners, you'll need to deselect some", null);	
				
				} else {
					return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_LOCATION);

				}

			} else if(index==2) {
			// Select all
				return new ResponseEffectsOnly("[style.colourGood(Select all)]", "select all possible partners") {
					@Override
					public void effects() {
						for(GameCharacter C : Main.game.getPlayer().getFiances()) {
							if(!pickedPartners.contains(C)) pickedPartners.add(C);
						}
						Main.game.updateResponses();
					}
				};
				
			} else if(index==3) {
			// Deselect all
				return new ResponseEffectsOnly("[style.colourBad(Select none)]", "deselect all possible partners") {
					@Override
					public void effects() {
						pickedPartners.clear();
						Main.game.updateResponses();
					}
				};
			}
			
			for(int i = 0; i < Main.game.getPlayer().getFiances().size(); i++) {
			// Pick individually
				if(index == i + 4) {
					GameCharacter C = Main.game.getPlayer().getFiances().get(i);
					if(pickedPartners.contains(C)) {
						return new ResponseEffectsOnly(C.getName(), "Remove "+C.getName()) {
							@Override
							public void effects() {
								pickedPartners.remove(C);
								Main.game.updateResponses();
							}
							@Override
							public Colour getHighlightColour() {return PresetColour.GENERIC_MINOR_GOOD;}
						};
						
					} else {
						return new ResponseEffectsOnly(C.getName(), "Add "+C.getName()) {
							@Override
							public void effects() {
								pickedPartners.add(C);
								Main.game.updateResponses();
							}
						};
					}
				}
			}
			//	[#pc.createRelationship(brax)] [#pc.incrementPassion(brax, 80, "", true)]
			//	[#pc.createRelationship(ralph)] [#pc.incrementPassion(ralph, 80, "", true)]
			//	[#pc.createRelationship(rose)] [#pc.incrementPassion(rose, 80, "", true)]
			//	[#pc.createRelationship(nyan)] [#pc.incrementPassion(nyan, 80, "", true)]
			return null;
		}
		
	};
	
	public static final DialogueNode MARRIAGE_PLANING_TOO_MANY = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			ArrayList<String> names = new ArrayList<String>();
			for(GameCharacter npc : pickedPartners) {
				names.add(npc.getName());
			}
			return "whoa sorry bud, how many people did you want to marry again?"
					+ "4, " + Util.stringsToStringList(names, false)
					+ "yeah sorry not happening bud"
					+ "you look at her quizzically, you got a reason, or are you just being moody"
					+ "I'm not being moody, I just think you should be dedicating yourself to one person, and it's not just me. State law says there can only be a maximum of four people in a marrige, ie you and three others."
					+ "muttering under breath, at least these horn dogs have some limits"
					+ "knowing that you'll only be able to marry three of your partners, you think who you really want the most";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return MARRIAGE_PLANING_START.getResponse(responseTab, index);
		}
	};
	
	public static final DialogueNode MARRIAGE_PLANING_LOCATION = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			return "eurg really, you're marrying two people?"
					+ "is that going to be a problem?"
					+ "it won't effect the ceremony, its just that i''m losing what little respect I had for you"
					+ "she says, writing down notes on your spouses-to-be"
					+ ""
					+ "Anyway, next step is to pick a location"
					+ "ashley then passes you a brochure"
					+ "so there's a bunch of options, but these two are the ones i personally recommend, ashley says, pointing at 'Dominion Town Hall' and 'the oaken glade'"
					+ "the town hall is good for small and simple ceremonies, the glade is fairly prestigious, but I have a few contacts that can get you in there though it'll cost"
					+ ""
					+ "I am however, legally obliged, she says stressing the words, to mention that the Cult of lilith offerers the services of their curches for you ceremony and has <i>excelent</i> deals"
					+ "after finishing her obligatory spiel, ashley then makes a gagging motion, though it's a bit hard to tell under their cloak"
					+ ""
					+ "these a few others in  the brochure, letting you have a read of it"
					+ "If you have a place yourself, we can do it there"
					+ "so what are you thinking?";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==0) {
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};

			} else if(index==1) {
				return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_DRESSING);
			}
			return null;
		}
	};
	
	public static final DialogueNode MARRIAGE_PLANING_DRESSING = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public void applyPreParsingEffects() {
			if(pickedOutfits.size()<=2) {		// if at least three outfits (the minimum needed) aren't picked, assumes it's initating for the first time
				for(int i = 0; i < 2 + pickedPartners.size(); i++) {
					pickedOutfits.add(null);
				}
			}
		}
		
		@Override
		public String getContent() {
			return "okey, now we'll need to pick out what you, your partners, and  the officiant are wearing"
					+ "Now, I am able tto offer you a large selection of dresses in both traditional and modern styles."
					+ "I even have a variety on suits, in case, you find a tuxedo too plain";
		}
		
		@Override
		public String getResponseTabTitle(int index) {
			if(index == 0) {
				return "[style.colourTfPartial(Player)]";
				
			} for(int i = 0; i < pickedPartners.size(); i++) {
				if(index==i+1) {
					return UtilText.parse(pickedPartners.get(i), "[npc.Name]");
				}
				
			} if(index == 1 + pickedPartners.size()) {
				return "[style.colourTfPartial(Officiant)]";
				
			} 

			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> outfits = new ArrayList<String>(Arrays.asList(
					"Dress 1", "Dress 2", "Dress 3", null, null,
					"Suit 1", "Suit 2", "Suit 3", null, null,
					"Toga 1", "Witch 1", "Witch 2", null,
					"Toga 2", "Military 1", "Military 2", null, null,
					"Military 3", "Kimono 1", "Kimono 2"));
			
			if(index==0 || index==29) {
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};
				
			} else if(index==1 || index==15) {
				if (pickedOutfits.stream().limit(2 + pickedPartners.size()).anyMatch(e -> e==null)) {
					return new Response("Next", "you can't move on until you've decided everyone's outfits!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_CEREMONY);
				
			}
			
			else if(index==6) {
				return new Response("Change betrohed", "description", MARRIAGE_PLANING_START);
			}
			
			
			for (int i = 0; i < outfits.size(); i++) {
				if(index==i+2 && outfits.get(i)!=null) {
					final String C = outfits.get(i);
					
					return new ResponseEffectsOnly(C, "select this outfit for " + this.getResponseTabTitle(responseTab)) {
						@Override
						public void effects() {
							while (pickedOutfits.size() < responseTab + 1) {	// if suddenly more partners, creates blanks to be overwritten
								pickedOutfits.add(null);
							}
							pickedOutfits.set(responseTab, C);
						}
						
						@Override
						public Colour getHighlightColour() {
							if(pickedOutfits.get(responseTab)==C) {
								return PresetColour.GENERIC_MINOR_GOOD;
							}	return PresetColour.TEXT;
						}
					};
				}
			}

			return null;
		}
	};

	public static final DialogueNode MARRIAGE_PLANING_CEREMONY = new DialogueNode("Dream Lover", "-", true, true) {
		
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
			ArrayList<String> ceremony = new ArrayList<String>(Arrays.asList(
					"Peaceful", "Exerbrient", "Lewd", null, null,
					"Royal", "Casual", "Debacherous"));
			ArrayList<String> description = new ArrayList<String>(Arrays.asList(
					"walk to aisle, words exchanged, kiss, off to honeymoon",
					"walk to aisle, words exchanged, kiss, party, off to honeymoon",
					"walk to aisle, words exchanged, fuck on altar, off to honeymoon", null, null,
					"Royal", "Casual",
					"walk to aisle, words exchanged, fuck on altar, orgy, off to honeymoon"));
			
			if(index==0) {
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};
				
			} else if(index==1) {
				if (pickedCeremony == null) {
					return new Response("Next", "you can't move on until you've decided on a ceremony style!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_AESTHETICS);
				
			}
			
			for (int i = 0; i < ceremony.size(); i++) {
				if(index==i+2 && ceremony.get(i)!=null) {
					final String C = ceremony.get(i);
					
					return new ResponseEffectsOnly(C, "select this type of ceremony<br>" + description.get(i)) {
						@Override
						public void effects() {pickedCeremony = C;}
						@Override
						public Colour getHighlightColour() {
							if(pickedCeremony==C) {
								return PresetColour.GENERIC_MINOR_GOOD;
							}	return PresetColour.TEXT;
						}
					};
				}
			}

			return null;
		}
		
	};
	
	public static final DialogueNode MARRIAGE_PLANING_AESTHETICS = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}
		
		@Override
		public String getResponseTabTitle(int index) {
			if(index == 0) {
				return "Colours";	
			} else if(index == 1) {
				return "Flowers";
			} else if(index == 2) {
				return "Banners";
			}
			return null;
		}

		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> colours = new ArrayList<String>(Arrays.asList(
					"Purple", "Gold", "Red"));
			ArrayList<String> flowers = new ArrayList<String>(Arrays.asList(
					"Lillies", "Roses", "Driping Liliths"));
			ArrayList<String> banners = new ArrayList<String>(Arrays.asList(
					"Plain", "Jazzy", "Lewd"));
			
			if(index==0) {
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};
				
			} else if(index==1) {
				if (pickedColour==null || pickedFlowers==null || pickedBanners==null) {
					return new Response("Next", "you can't move on until you've decided everyone's outfits!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_HONEYMOON);
				
			}
			
			if(responseTab==0) {
				for (int i = 0; i < colours.size(); i++) {
					if(index==i+2 && colours.get(i)!=null) {
						final String C = colours.get(i);

						return new ResponseEffectsOnly(C, "select this type of colour for decorations") {
							@Override
							public void effects() {pickedColour = C;}
							@Override
							public Colour getHighlightColour() {
								if(pickedColour==C) {
									return PresetColour.GENERIC_MINOR_GOOD;
								}	return PresetColour.TEXT;
							}
						};
					}
				}
				
			} else if(responseTab==1) {
				for (int i = 0; i < flowers.size(); i++) {
					if(index==i+2 && flowers.get(i)!=null) {
						final String C = flowers.get(i);

						return new ResponseEffectsOnly(C, "select this type of flowers for decorations") {
							@Override
							public void effects() {pickedFlowers = C;}
							@Override
							public Colour getHighlightColour() {
								if(pickedFlowers==C) {
									return PresetColour.GENERIC_MINOR_GOOD;
								}	return PresetColour.TEXT;
							}
						};
					}
				}
				
			} else if(responseTab==2) {
				for (int i = 0; i < banners.size(); i++) {
					if(index==i+2 && banners.get(i)!=null) {
						final String C = banners.get(i);
						
						return new ResponseEffectsOnly(C, "select this type of banner for decorations") {
							@Override
							public void effects() {pickedBanners = C;}
							@Override
							public Colour getHighlightColour() {
								if(pickedBanners==C) {
									return PresetColour.GENERIC_MINOR_GOOD;
								}	return PresetColour.TEXT;
							}
						};
					}
				}
				
			}
			
			return null;
		}
		
	};
	
	public static final DialogueNode MARRIAGE_PLANING_HONEYMOON = new DialogueNode("Dream Lover", "-", true, true) {
		
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
			if(index==0) {
				if(reviewMode) {
					return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
				}	return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
					public void effects() {resetMarriagePlanner();}
				};

			} else if(index==1) {
				return new Response("Manor house hotel", "spend your honeymoon at the Manor house hotel, an upmarket resort on the outskirts of dominion", MARRIAGE_PLANING_REVIEW);
			}
			return null;
		}
		
	};
	
	public static final DialogueNode MARRIAGE_PLANING_REVIEW = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public void applyPreParsingEffects() {
			DreamLover.reviewMode = true;
		}

		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==0) {
				return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
					@Override
						public void effects() {
						//	Main.game.getTextEndStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/shoppingArcade/dreamLover", "SEX_TOY_DISCOVERY"));
							Main.game.getTextEndStringBuilder().append("well thanks for wasting my time!");
							resetMarriagePlanner();
						}
				};
			} else if(index==1) {
				return new Response("Finish and Pay", "Everything looks to be in order, review the prices and then pay", MARRIAGE_PLANING_PAY);
			} else if(index==2) {
				return new Response("Partners", "Change your partners", MARRIAGE_PLANING_START);
			} else if(index==3) {
				return new Response("Location", "Change the location for the ceremony", MARRIAGE_PLANING_LOCATION);
			}
			return null;
		//	return MARRIAGE_PLANING_START.getResponse(responseTab, index);
		}
	};
	
	public static final DialogueNode MARRIAGE_PLANING_PAY = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			// TODO Auto-generated method stub
			return "ok so that's everything sorted. now since you're going for a traditional wedding, i'll need two days to organise everything."
					+ "then after that, you only have a week to propose to your fiance."
					+ "after a week, my contractors will consider this a no-show and will cancel their preparations"
					+ "If you actually want to marry them, which you should have done that week, then you'll need to come back here so we can organise another wedding"
					+ "if you want to change anything regarding your wedding, come talk to me and I'll get it done, though it will be costly";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return MARRIAGE_PLANING_REVIEW.getResponse(responseTab, index);
		}
	};	
}
