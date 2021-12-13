package com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.npc.dominion.Ashley;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseEffectsOnly;
import com.lilithsthrone.game.dialogue.responses.ResponseTrade;
import com.lilithsthrone.game.dialogue.romance.RomanceUtil;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.character.race.Race;

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
			return "Kumiko";
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

	private static Response goBack() {
		if(reviewMode) return new Response("Back", "go back to reviewing the wedding", MARRIAGE_PLANING_REVIEW);
		
		return new Response("Quit", "decide against planning a wedding", EXTERIOR) {
			@Override
			public void effects() {
				RomanceUtil.resetMarriagePlanner();
				reviewMode = false;
			}
		};
	}
	
	static boolean reviewMode = false;
	static boolean partnerLimit = false;	//Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ashleyLimit);
	
	static ArrayList<GameCharacter> pickedPartners() {return RomanceUtil.pickedPartners();}
	static HashMap<String, String> pickedOutfits() {return RomanceUtil.pickedOutfits();}
	static ArrayList<String> pickedDecisions() {return RomanceUtil.pickedDecisions();}
	// Location Colours Flowers Banners Ceremony Honeymoon

	public static final DialogueNode MARRIAGE_PLANING_START = new DialogueNode("Dream Lover", "-", true, false) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_START");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			
			if(index==0) {				// Back
				return goBack();

			} else if(index==1) {		// Next
				if(pickedPartners().size()==0) {													// no partners selected
					return new Response("Next", "you need to select at least one partner before you can proceed", null);
					
				} else if(RomanceUtil.checkCompatibility(pickedPartners(), true, false)==false) {	// not enough mutual passion
					return new Response("Next", "some of your partners aren't passionate enough about each other, they won't want to get married", null);
					
				} else if(RomanceUtil.checkCompatibility(pickedPartners(), false, true)==false) {	// one of the chosen have too many partners
					return new Response("Next", "at least one of your partners is already married to "+Util.intToString(RomanceUtil.MAX_POLYCULE - 1)+" others and can't get married", null);

				} else if(pickedPartners().size()>RomanceUtil.MAX_POLYCULE) {						// over the limit
					if(!partnerLimit) return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_TOO_MANY) {
						@Override
						public void effects() {Main.game.getDialogueFlags().setFlag(DialogueFlagValue.ashleyLimit, true);}
					};
					return new Response("Next", "Ashley has already told you that you can't be married to more than "+Util.intToString(RomanceUtil.MAX_POLYCULE)+" partners, you'll need to deselect some", null);	// FIXME, polycule existing partners
				
				} else {																		// under the limit or no limit
					return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_LOCATION);
				}

			} else if(index==2) {		// Select all
				return new ResponseEffectsOnly("[style.colourGood(Select all)]", "select all possible partners") {
					@Override
					public void effects() {
						pickedPartners().clear();
						pickedPartners().addAll(Main.game.getPlayer().getFiances());
						Main.game.updateResponses();
					}
				};
				
			} else if(index==3) {		// Deselect all
				return new ResponseEffectsOnly("[style.colourBad(Select none)]", "unselect all possible partners") {
					@Override
					public void effects() {
						pickedPartners().clear();
						Main.game.updateResponses();
						pickedOutfits().clear();
					}
				};
			}
			
			for(int i = 0; i < Main.game.getPlayer().getFiances().size(); i++) {		// Pick individually
				if(index == i + 4) {
					GameCharacter npc = Main.game.getPlayer().getFiances().get(i);
					
					if(pickedPartners().contains(npc)) {									// list contains character
						return new ResponseEffectsOnly(npc.getName(), "Remove "+npc.getName()) {

							@Override
							public void effects() {
								pickedPartners().remove(npc);
								Main.game.updateResponses();
								pickedOutfits().remove(npc.getId());
							}

							@Override
							public Colour getHighlightColour() {return PresetColour.GENERIC_MINOR_GOOD;}
						};
						
					} else {															// list does not contain character
						return new ResponseEffectsOnly(npc.getName(), "Add "+npc.getName()) {
							@Override
							public void effects() {
								pickedPartners().add(npc);
								Main.game.updateResponses();
							}
						};
						
					}
				}
			}
			
			if(index==8) {
				return new Response("Skip", "Change where your honeymoon will be", MARRIAGE_PLANING_REVIEW);
				
			}
			
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
			for(GameCharacter npc : pickedPartners()) {
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
			ArrayList<String> locations = new ArrayList<String>(Arrays.asList(
					"Church			|	|	75000",
					"Town hall		|	|	75000",
					"Oaken glade	|	|	125000"));
			
			if(index==0) {
				return goBack();

			} else if(index==1) {
				if(pickedDecisions().get(0)=="") return new Response("Next", "you can't move on until you pick a location", null);
				return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_DRESSING);
			}
			
			for (int i = 0; i < locations.size(); i++) {
				if(index==i+2 && locations.get(i)!=null) {
					final String C = locations.get(i);
					final String C0 = C.split("\\|")[0].trim();
					
					return new ResponseEffectsOnly(C0, "pick this location for the wedding venue") {
						@Override
							public void effects() {pickedDecisions().set(0, C);}
						@Override
						public Colour getHighlightColour() {
							if(pickedDecisions().get(0).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
							return PresetColour.TEXT;
						}
					};
					
				}
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
		public String getContent() {
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_DRESSING");
		}
		
		@Override
		public String getResponseTabTitle(int index) {
			if(index == 0) return "[style.colourTfPartial(You)]";
			
			for(int i = 0; i < pickedPartners().size(); i++) {
				if(index==i+1) return UtilText.parse(pickedPartners().get(i), "[npc.Name]");	
			} 
			
			if(index == 1 + pickedPartners().size()) return "[style.colourTfPartial(the Officiant)]";
			return null;
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> outfits = new ArrayList<String>(Arrays.asList(
					"Dress 1	|	E	|	2000",
					"Dress 2	|	E	|	2000",
					"Dress 3	|	E	|	2000",
					null, null,
					"Suit 1		|	E	|	2000",
					"Suit 2		|	E	|	2000",
					"Suit 3		|	E	|	2000",
					null, null,
					"Toga 1		|	E	|	2000",
					"Witch 1	|	E	|	2000",
					"Witch 2	|	E	|	2000",
					null,
					"Toga 2		|	E	|	2000",
					"Military 1	|	E	|	2000",
					"Military 2	|	E	|	2000",
					null, null,
					"Military 3	|	E	|	2000",
					"Kimono 1	|	E	|	2000",
					"Kimono 2	|	E	|	2000"));
			
			if(index==0 || index==29) {
				return goBack();
				
			} else if(index==1 || index==15) {
				if (pickedOutfits().isEmpty() || pickedOutfits().size()!=2+pickedPartners().size()) {
					return new Response("Next", "you can't move on until you've decided everyone's outfits!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_AESTHETICS);
				
			} else if(index==6) {
				return new Response("Change betrothed", "description", MARRIAGE_PLANING_START);
				
			}

			for (int i = 0; i < outfits.size(); i++) {
				if(index==i+2 && outfits.get(i)!=null) {
					final String C = outfits.get(i);
					final String C0 = C.split("\\|")[0].trim();
					
					return new ResponseEffectsOnly(C0, "select this outfit for " + this.getResponseTabTitle(responseTab)) {
						
						String id = responseTab==0
								? Main.game.getPlayer().getId()
								: responseTab==pickedPartners().size()+1
										? "NOT_SET"
										: pickedPartners().get(responseTab-1).getId();
						
						@Override
						public void effects() {pickedOutfits().put(id, C);}
						@Override
						public Colour getHighlightColour() {
							if(pickedOutfits().containsKey(id) && pickedOutfits().get(id).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
							return PresetColour.TEXT;
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
					"Purple		|	|	1000",
					"Gold		|	|	1000",
					"Red		|	|	1000"));
			ArrayList<String> flowers = new ArrayList<String>(Arrays.asList(
					"Lilies				|	|	2000",
					"Roses				|	|	2000",
					"Dripping Liliths	|	|	5000"));
			ArrayList<String> banners = new ArrayList<String>(Arrays.asList(
					"Plain		|	|	2000",
					"Jazzy		|	|	2000",
					"Lewd		|	|	5000"));
			
			if(index==0) {
				return goBack();
				
			} else if(index==1) {
				if (pickedDecisions().get(1)=="" || pickedDecisions().get(2)=="" || pickedDecisions().get(3)=="") {
					return new Response("Next", "you can't move on until you've decided on the colours, banners, and flowers for the ceremony!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_CEREMONY);
			}
			
			if(responseTab==0) {			// colour selection
				for (int i = 0; i < colours.size(); i++) {
					if(index==i+2 && colours.get(i)!=null) {
						final String C = colours.get(i);
						final String C0 = C.split("\\|")[0].trim();
						
						return new ResponseEffectsOnly(C0, "select this type of colour for decorations") {
							@Override
							public void effects() {pickedDecisions().set(1, C);	Main.game.updateResponses();}
							@Override
							public Colour getHighlightColour() {
								if(pickedDecisions().get(1).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
								return PresetColour.TEXT;
							}
						};
					}
				}
				
			} else if(responseTab==1) {			// flower selection
				for (int i = 0; i < flowers.size(); i++) {
					if(index==i+2 && flowers.get(i)!=null) {
						final String C = flowers.get(i);
						final String C0 = C.split("\\|")[0].trim();
						
						return new ResponseEffectsOnly(C0, "select this type of flowers for decorations") {
							@Override
							public void effects() {pickedDecisions().set(2, C);	Main.game.updateResponses();}
							@Override
							public Colour getHighlightColour() {
								if(pickedDecisions().get(2).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
								return PresetColour.TEXT;
							}
						};
					}
				}
				
			} else if(responseTab==2) {			// banner selection
				for (int i = 0; i < banners.size(); i++) {
					if(index==i+2 && banners.get(i)!=null) {
						final String C = banners.get(i);
						final String C0 = C.split("\\|")[0].trim();
						
						return new ResponseEffectsOnly(C0, "select this type of banner for decorations") {
							@Override
							public void effects() {pickedDecisions().set(3, C);	Main.game.updateResponses();}
							@Override
							public Colour getHighlightColour() {
								if(pickedDecisions().get(3).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
								return PresetColour.TEXT;
							}
						};

					}
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
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_CEREMONY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			ArrayList<String> ceremony = new ArrayList<String>(Arrays.asList(
					"Peaceful		|	walk to aisle, words exchanged, kiss, off to honeymoon					|	75000",
					"Exuberant		|	walk to aisle, words exchanged, kiss, party, off to honeymoon			|	100000",
					"Lewd			|	walk to aisle, words exchanged, fuck on altar, off to honeymoon			|	80000",
					null, null,
					"Casual			|	words exchanged, kiss, off to honeymoon									|	50000",
					"Debaucherous	|	walk to aisle, words exchanged, fuck on altar, orgy, off to honeymoon	|	150000",
					"Royal			|	guard of honour, walk to aisle, words exchanged, kiss, city parade, fuck in carriage, honeymoon	|	1500000"));	// change to noble? royal has story issues re lilith
			
			if(index==0) {
				return goBack();
				
			} else if(index==1) {
				if (pickedDecisions().get(4) == "") {
					return new Response("Next", "you can't move on until you've decided on a ceremony style!", null);
				}	return new Response("Next", "move on to the next stage of planning your wedding", MARRIAGE_PLANING_RING);
				
			}
			
			for (int i = 0; i < ceremony.size(); i++) {
				if(index==i+2 && ceremony.get(i)!=null) {
					final String C = ceremony.get(i);
					final String C0 = C.split("\\|")[0].trim();
					final String C1 = C.split("\\|")[1].trim();
					final String C2 = C.split("\\|")[2].trim();

					if(index==9 && Main.game.getPlayer().getTrueRace()!=Race.DEMON && !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_2_D_MEETING_A_LILIN)) {
						return new ResponseEffectsOnly(C0, "you cannot select this type of ceremony due to not being part of the royal family") {
							@Override
							public Colour getHighlightColour() {return PresetColour.GENERIC_TERRIBLE;}
						};
					}
					
					return new ResponseEffectsOnly(C0, "select this type of ceremony:<br>" + C1 + "<br> [style.moneyFormat("+C2+", span)]") {
						@Override
						public void effects() {pickedDecisions().set(4, C);}
						@Override
						public Colour getHighlightColour() {
							if(pickedDecisions().get(4).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
							return PresetColour.TEXT;
						}
					};
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
				return goBack();

			} else if(index==1) {
				return new Response("plain ol' ring", "give your partners each a plain ol' ring", MARRIAGE_PLANING_HONEYMOON);
				
			} else if(index==2) {
				return new Response("self supply", "get the rings yourself", MARRIAGE_PLANING_HONEYMOON);
				
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
			ArrayList<String> locations = new ArrayList<String>(Arrays.asList(
				"Manor House hotel	|	spend your honeymoon at the Manor House hotel, an upmarket resort on the outskirts of dominion	|	125000",
				"Venue | Description | 30"));
			
			if(index==0) {
				return goBack();

			} else if(index==1) {
				if(pickedDecisions().get(5)=="") return new Response("Next", "you can't move on until you pick a location", null);
				return new Response("Next", "move on to a final review of your wedding", MARRIAGE_PLANING_REVIEW);
			}
			
			for (int i = 0; i < locations.size(); i++) {
				if(index==i+2 && locations.get(i)!=null) {
					final String C = locations.get(i);
					final String C0 = C.split("\\|")[0].trim();
					
					return new ResponseEffectsOnly(C0, "pick this location for your honeymoon") {
						@Override
						public void effects() {pickedDecisions().set(5, C);}
						@Override
						public Colour getHighlightColour() {
							if(pickedDecisions().get(5).equals(C)) return PresetColour.GENERIC_MINOR_GOOD;
							return PresetColour.TEXT;
						}
					};
				}
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
			reviewMode = true;
			RomanceUtil.setProposalTimes();
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
						Main.game.getTextEndStringBuilder().append("well thanks for wasting my time!");
						RomanceUtil.resetMarriagePlanner();
						reviewMode = false;
					}
				};
				
			} else if(index==1) {
				return new Response("Finish and Pay", "Everything looks to be in order, review the prices and then pay", MARRIAGE_PLANING_PAY);
				
			} else if(index==2) {
				return new Response("Partners", "Change your partners", MARRIAGE_PLANING_START);
				
			} else if(index==3) {
				return new Response("Location", "Change the location for the ceremony", MARRIAGE_PLANING_LOCATION);
				
			} else if(index==4) {
				return new Response("Outfits", "Change the chosen outfits", MARRIAGE_PLANING_DRESSING);
				
			} else if(index==5) {
				return new Response("Aesthetics", "Change the visual aesthetics of the ceremony", MARRIAGE_PLANING_AESTHETICS);
				
			} else if(index==6) {
				return new Response("Ring", "Change the ring for your partners", MARRIAGE_PLANING_RING);
				
			} else if(index==7) {
				return new Response("Ceremony", "Change the type of ceremony", MARRIAGE_PLANING_CEREMONY);
				
			} else if(index==8) {
				return new Response("Honeymoon", "Change where your honeymoon will be", MARRIAGE_PLANING_HONEYMOON);
				
			}
			return null;
		}
	};
	
	public static final DialogueNode MARRIAGE_PLANING_PAY = new DialogueNode("Dream Lover", "-", true, true) {
		
		@Override
		public String getAuthor() {
			return "Amarok";
		}

		@Override
		public String getContent() {
			RomanceUtil.setProposalTimes();
			LocalDateTime prepTime = Main.game.getRomanceUtil().prepTime;
			LocalDateTime proposalTime = Main.game.getRomanceUtil().proposalTime;
			
			UtilText.addSpecialParsingString(prepTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + prepTime.getDayOfMonth(), true);
			UtilText.addSpecialParsingString(proposalTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + proposalTime.getDayOfMonth(), false);
			UtilText.addSpecialParsingString(RomanceUtil.costBreakdown(), false);
			UtilText.addSpecialParsingString(String.valueOf(RomanceUtil.totalCost()), false);
			
			return UtilText.parseFromXMLFile("romance/AshleyMarriagePlanner", "MARRIAGE_PLANING_PAY");
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			return MARRIAGE_PLANING_REVIEW.getResponse(responseTab, index);
		}
	};	
}
