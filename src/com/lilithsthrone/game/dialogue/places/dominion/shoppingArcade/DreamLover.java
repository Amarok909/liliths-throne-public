package com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade;

import java.time.LocalDateTime;
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
	public static int maxPartners = 3;			// max size of a polycule/marriage, excluding PC. if 0, no max limit
	public static ArrayList<String> pickedOutfits = new ArrayList<String>();
	public static String pickedCeremony = null;
	public static String pickedColour = null;
	public static String pickedFlowers = null;
	public static String pickedBanners = null;
	public static String pickedHoneymoon = null;
	public static LocalDateTime prepTime;
	public static LocalDateTime proposalTime;
	public static boolean partnerLimit = false;		// does the pc know about the limit on partners?

	private static void resetMarriagePlanner() {
		pickedPartners.clear();
		reviewMode = false;
		pickedOutfits.clear();
		pickedCeremony = null;
		pickedColour = null;
		pickedFlowers = null;
		pickedBanners = null;
		pickedHoneymoon = null;
	}

	private boolean checkRomance(List<GameCharacter> npc) {
		if(!npc.contains(Main.game.getPlayer())) npc.Add(Main.game.getPlayer());	// pc needs to be compared as well, if not in list, add

		for(int i = 0; i < npc.size(); i++) {
			for(int j = 0; j < npc.size(); j++) {
				if(npc.get(i)==npc.get(j)) continue;		// if comparing the same character, skip

				if(npc.get(i).getPassion(npc.get(j)) < 80) return false;	// one npc does not like another enough to get married, so can't happen
			}
		}

		return true;	// everyone has at least 80 passion towards everyone else
	}
	
	private static Response goBack() {
		if(reviewMode) return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
		
		return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
			@Override
			public void effects() {resetMarriagePlanner();}
		};
	}

	public static final DialogueNode MARRIAGE_PLANING_START = new DialogueNode("Dream Lover", "-", true, false) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		public void applyPreParsingEffects() {
			partnerLimit = Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit);
		}

		@Override
		public String getContent() {
			ArrayList<String> names = new ArrayList<String>();
			for(GameCharacter npc : pickedPartners) {
				names.add(npc.getName());
			}
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_START");
		}
		
	/*	@Override
		public String getResponseTabTitle(int index) {		// FIXME remove this test object
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
		}	*/

		@Override
		public Response getResponse(int responseTab, int index) {
			
			if(index==0) {				// Back
				goBack();

			} else if(index==1) {		// Next
				if(pickedPartners.size()==0) {
					return new Response("Next", "you need to select at least one partner before you can proceed", null);
					
				} else if(checkRomance(pickedPartners)==false) {	// not enough mutual passion
					return new Response("Next", "some of your partners aren't passionate enough about each other, they won't want to get married", null);

				// over the limit
				} else if(maxPartners > 0 && pickedPartners.size() + Main.game.getPlayer().getSpouces().size() >= 1 + maxPartners) {
					if(!partnerLimit) return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_TOO_MANY) {
						@Override
						public void effects() {Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyLimit, true);}
					};
					return new Response("Next", "Ashley has already told you that you can't be married to more than three partners, you'll need to deselect some", null);	
				
				} else {				// under the limit or no limit
					return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_LOCATION);

				}

			} else if(index==2) {		// Select all
				return new ResponseEffectsOnly("[style.colourGood(Select all)]", "select all possible partners") {
					@Override
					public void effects() {
						for(GameCharacter C : Main.game.getPlayer().getFiances()) {
							if(!pickedPartners.contains(C)) pickedPartners.add(C);
						}
					//	pickedPartners.addAll(Main.game.getPlayer().getFiances())
						Main.game.updateResponses();
					}
				};
				
			} else if(index==3) {		// Deselect all
				return new ResponseEffectsOnly("[style.colourBad(Select none)]", "unselect all possible partners") {
					@Override
					public void effects() {
						pickedPartners.clear();
						Main.game.updateResponses();
					}
				};
			}
			
			for(int i = 0; i < Main.game.getPlayer().getFiances().size(); i++) {		// Pick individually
				if(index == i + 4) {
					GameCharacter C = Main.game.getPlayer().getFiances().get(i);
					if(pickedPartners.contains(C)) {									// list contains character
						return new ResponseEffectsOnly(C.getName(), "Remove "+C.getName()) {

							@Override
							public void effects() {
								pickedPartners.remove(C);
								Main.game.updateResponses();
							}

							@Override
							public Colour getHighlightColour() {return PresetColour.GENERIC_MINOR_GOOD;}
						};
						
					} else {															// list does not contain character
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

			UtilText.addSpecialParsingString(Util.intToString(names.size()), true);
			UtilText.addSpecialParsingString(Util.stringsToStringList(names, false), false);
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_TOO_MANY");
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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_LOCATION");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==0) {
				goBack();

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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_DRESSING");
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
				goBack();
				
			} else if(index==1 || index==15) {
				if (pickedOutfits.stream().limit(2 + pickedPartners.size()).anyMatch(e -> e==null)) {
					return new Response("Next", "you can't move on until you've decided everyone's outfits!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_CEREMONY);
				
			}
			
			else if(index==6) {
				return new Response("Change betrothed", "description", MARRIAGE_PLANING_START);
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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_AESTHETICS");
		}
		
		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> colours = new ArrayList<String>(Arrays.asList(
					"Purple", "Gold", "Red"));
			ArrayList<String> flowers = new ArrayList<String>(Arrays.asList(
					"Lilies", "Roses", "Dripping Liliths"));
			ArrayList<String> banners = new ArrayList<String>(Arrays.asList(
					"Plain", "Jazzy", "Lewd"));
			
			if(index==0) {
				goBack();
				
			} else if(index==1) {
				if (pickedColour==null || pickedFlowers==null || pickedBanners==null) {
					return new Response("Next", "you can't move on until you've decided on the colour pallet, banners, and flowers for the ceremony!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_CEREMONY);
				
			}
			
			if(responseTab==0) {			// colour selection
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
				
			} else if(responseTab==1) {			// flower selection
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
				
			} else if(responseTab==2) {			// banner selection
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
	
	public static final DialogueNode MARRIAGE_PLANING_RING = new DialogueNode("Dream Lover", "-", true, true) {

		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_RING");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==0) {
				goBack();

			} else if(index==1) {
				return new Response("plain ol' ring", "give your partners each a plain ol' ring", MARRIAGE_PLANING_HONEYMOON);
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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_CEREMONY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> ceremony = new ArrayList<String>(Arrays.asList(
					"Peaceful", "Exuberant", "Lewd", null, null,
					"Royal", "Casual", "Debacherous"));
			ArrayList<String> description = new ArrayList<String>(Arrays.asList(
					"walk to aisle, words exchanged, kiss, off to honeymoon",
					"walk to aisle, words exchanged, kiss, party, off to honeymoon",
					"walk to aisle, words exchanged, fuck on altar, off to honeymoon", null, null,
					"Royal", "Casual",
					"walk to aisle, words exchanged, fuck on altar, orgy, off to honeymoon"));
			
			if(index==0) {
				goBack();
				
			} else if(index==1) {
				if (pickedCeremony == null) {
					return new Response("Next", "you can't move on until you've decided on a ceremony style!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_RING);
				
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
	
	public static final DialogueNode MARRIAGE_PLANING_HONEYMOON = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_HONEYMOON");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if(index==0) {
				goBack();

			} else if(index==1) {
				return new Response("Manor House hotel", "spend your honeymoon at the Manor House hotel, an upmarket resort on the outskirts of dominion", MARRIAGE_PLANING_REVIEW);
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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_REVIEW");
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
			UtilText.addSpecialParsingString(prepTime.getMonthName + " " + prepTime.getDayOfMonth, true);
			UtilText.addSpecialParsingString(proposalTime.getMonthName + " " + proposalTime.getDayOfMonth, false);
			UtilText.addSpecialParsingString("20000", false);
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_PAY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			// TODO Auto-generated method stub
			return MARRIAGE_PLANING_REVIEW.getResponse(responseTab, index);
		}
	};	
}
